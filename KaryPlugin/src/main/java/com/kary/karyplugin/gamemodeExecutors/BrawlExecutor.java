package com.kary.karyplugin.gamemodeExecutors;

import com.kary.karyplugin.KaryPlugin;
import com.kary.karyplugin.pojo.Record;
import com.kary.karyplugin.service.RecordService;
import com.kary.karyplugin.utils.GameModeUtil;
import com.kary.karyplugin.utils.LevelUtil;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentSkipListSet;

/**
 * @author:123
 */
public class BrawlExecutor implements Listener, CommandExecutor {
    private Integer gameMode= GameModeUtil.BRAWL_MODE;
    private KaryPlugin plugin;
    private Map<Player, Record> players=new ConcurrentHashMap<>();
    private RecordService recordService;
    private Map<Player,Integer> playersMatchingGamemode;
    private Map<Integer,List<Player>> matchingPlayers=new ConcurrentHashMap<>();
    private Player mvpPlayer;
    private static final int MAX_MATCH_NUM=3;
    //TODO 一场比赛6个人
    private static final int KILL_ONE_ADD =10;
    //将死者,助攻者列表
    private Map<Player, ConcurrentSkipListSet<PlayerAndTime>> assistMap=new ConcurrentHashMap<>();
    class PlayerAndTime implements Comparable<PlayerAndTime>{
        Player player;
        long time;

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            PlayerAndTime that = (PlayerAndTime) o;
            return Objects.equals(player, that.player);
        }

        @Override
        public int hashCode() {
            return Objects.hash(player);
        }

        public PlayerAndTime(Player player, long time) {
            this.player = player;
            this.time = time;
        }

        public synchronized void setTime(long time) {
            this.time = time;
        }

        @Override
        public int compareTo(PlayerAndTime o) {
            if(time-o.time>0){
                return 1;
            }else if(time-o.time<0){
                return -1;
            }else {
                return Integer.compare(player.hashCode(), o.player.hashCode());
            }
        }
    }

    public BrawlExecutor(RecordService recordService, Map<Player, Integer> playersMatchingGamemode,KaryPlugin plugin) {
        this.recordService = recordService;
        this.playersMatchingGamemode = playersMatchingGamemode;
        this.plugin=plugin;
        matchingPlayers.put(LevelUtil.COPPER, Collections.synchronizedList(new ArrayList<>()));
        matchingPlayers.put(LevelUtil.SILVER, Collections.synchronizedList(new ArrayList<>()));
        matchingPlayers.put(LevelUtil.GOLD, Collections.synchronizedList(new ArrayList<>()));
        matchingPlayers.put(LevelUtil.PLATINUM, Collections.synchronizedList(new ArrayList<>()));
        matchingPlayers.put(LevelUtil.DIAMOND, Collections.synchronizedList(new ArrayList<>()));
        matchingPlayers.put(LevelUtil.MASTER, Collections.synchronizedList(new ArrayList<>()));
        matchingPlayers.put(LevelUtil.KING, Collections.synchronizedList(new ArrayList<>()));
    }

    @EventHandler(priority = EventPriority.MONITOR)
    public void addAssist(EntityDamageByEntityEvent event){
        Entity damagerEntity= event.getDamager();
        Entity damageeEntity=event.getEntity();
        //如果两者都是玩家
        if(damageeEntity instanceof Player && damagerEntity instanceof Player){
            Player damagee= (Player) damageeEntity;
            Player damager= (Player) damagerEntity;
            double damage=event.getDamage();//本次伤害
            //danagerList是所有伤害过damagee的玩家列表
            assistMap.computeIfPresent(damagee,(key, danagerList)->{
                danagerList.add(new PlayerAndTime(damager,System.currentTimeMillis()));
                return danagerList;
            });
            //给输出者加伤害
            players.computeIfPresent(damager,(key, damagerRecord)->{
                damagerRecord.addTakeDamage(damage);
                return damagerRecord;
            });
            //给受伤者加承伤
            players.computeIfPresent(damagee,(key, damageeRecord)->{
                damageeRecord.addTakenDamage(damage);
                return damageeRecord;
            });
        }
    }

    @EventHandler(priority = EventPriority.MONITOR)
    public void playerKill(PlayerDeathEvent event){
        Player deadPlayer=event.getEntity();
        Player killer=deadPlayer.getKiller();
        //更新死亡者的记录
        players.computeIfPresent(deadPlayer,(key, deadRecord)->{
            deadRecord.addOneDeath();
            return deadRecord;
        });

        //更新杀人者的记录
        players.computeIfPresent(killer,(key, killerRecord)->{
            killerRecord.addOneKill();
            //杀一人，加分
            killerRecord.addScore(KILL_ONE_ADD);
            return killerRecord;
        });

        //对于所有助攻者更新助攻记录
        assistMap.computeIfPresent(deadPlayer,(key, deadAssists)->{
            for (Iterator<PlayerAndTime> iterator = deadAssists.iterator(); iterator.hasNext();) {
                PlayerAndTime assistAndTime = iterator.next();
                Player assist=assistAndTime.player;
                players.computeIfPresent(assist,(key2, assistRecord)->{
                    if(!assist.equals(killer)) {
                        assistRecord.addOneAssist();
                    }
                    return assistRecord;
                });
                iterator.remove(); // 移除已经计算过的助攻者
            }
            return deadAssists;
        });
    }

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] args) {
        boolean result = false;
        if (commandSender instanceof Player) {
            Integer gamemode=playersMatchingGamemode.get(commandSender);
            int level= LevelUtil.getLevel(recordService.getScoreTotal(commandSender.getName(),gameMode));
            if(gamemode==null){
                playersMatchingGamemode.put((Player) commandSender, GameModeUtil.BRAWL_MODE);
                //每一段位的总匹配人数
                List<Player> matchingPlayer=matchingPlayers.get(level);
                matchingPlayer.add((Player) commandSender);
                ((Player) commandSender).sendRawMessage("您正在匹配大乱斗，等待其他玩家加入游戏……");
                if(matchingPlayer.size()==MAX_MATCH_NUM){//达到最大待匹配人数
                    StringBuilder message=new StringBuilder();
                    for (Player player:matchingPlayer) {
                        //对于在场每一位玩家，都有一个攻击者列表
                        assistMap.put(player,new ConcurrentSkipListSet<>());
                        players.put(player,new Record());
                        message.append(player.getName());
                        message.append(",");
                    }
                    for (Player player : matchingPlayer) {
                        player.sendRawMessage("和您在同一局的对手为"+ message+"对局开始");
                    }

                    this.mvpPlayer=matchingPlayer.get(0);
                    // 安排任务在主线程中每20个游戏刻执行一次（1秒 = 20游戏刻）
                    int delayInTicks = 0; // 延迟0个游戏刻
                    int periodInTicks = 20; // 每20个游戏刻执行一次
                    //准备完毕，开始比赛线程
                    Bukkit.getScheduler().runTaskTimer(plugin,new BrawlMatch(players), delayInTicks, periodInTicks);
                    Bukkit.getScheduler().runTaskTimer(plugin,new AssistTimer(assistMap), delayInTicks, periodInTicks);
                    matchingPlayer.clear();
                }
            }else{
                if(gamemode==GameModeUtil.BRAWL_MODE){
                    ((Player) commandSender).sendRawMessage("您已在大乱斗匹配中，无需再加入匹配");
                }else{
                    ((Player) commandSender).sendRawMessage("您正在匹配其他游戏，请先退出");
                }
            }
        }
        return result;
    }

    //当匹配完时启动一个新的比赛线程
    class BrawlMatch implements Runnable {
        Map<Player, Record> players;//Concurrent
        int second=0;
        int gameLimitTime=30;//1分钟
        //TODO 改为5分钟

        public BrawlMatch(Map<Player, Record> players) {
            this.players = players;
        }

        @Override
        public void run() {
            //游戏限时
            if(second < gameLimitTime) {
                second += 1;
            }else{
                Set<Map.Entry<Player, Record>> entrySet=players.entrySet();
                double maxKDA=Double.MIN_VALUE;
                for (Map.Entry<Player, Record> entry : entrySet){
                    Player player= entry.getKey();
                    Record record= entry.getValue();
                    int k= record.getKill();
                    int d= record.getDeath();
                    int a= record.getAssist();
                    double kda= (k * 1.0 + a*0.7) /((d!=0)?d:1);
                    if(kda>maxKDA){
                        maxKDA=kda;
                        mvpPlayer=player;
                    }
                }
                //利用ConcurrentHashMap来同步操作
                for (Map.Entry<Player, Record> entry : entrySet) {
                    Player player= entry.getKey();
                    Record record=entry.getValue();
                    KaryPlugin.updateDatabase(
                            300000L,
                            player.getName(),
                            record.getKill(),
                            record.getDeath(),
                            record.getScoreGain(),
                            record.getAssist(),
                            record.getTakeDamage(),
                            record.getTakenDamage(),
                            mvpPlayer.getName(),
                            gameMode
                    );
                }
                players.clear();

            }
        }
    }
    class AssistTimer implements Runnable {
        Map<Player, ConcurrentSkipListSet<PlayerAndTime>> assistMap;
        int second=0;
        int assistExistLimitTime=30;//只计算30秒内的助攻

        public AssistTimer(Map<Player, ConcurrentSkipListSet<PlayerAndTime>> assistMap) {
            this.assistMap = assistMap;
        }

        @Override
        public void run(){
            //计算10秒
            if (second < assistExistLimitTime) {
                second += 1;
            }else{
                for (Map.Entry<Player, ConcurrentSkipListSet<PlayerAndTime>> playerSetEntry : assistMap.entrySet()) {
                    ConcurrentSkipListSet<PlayerAndTime> assistSet=playerSetEntry.getValue();
                    assistSet.pollFirst();
                }
                second=0;
            }
        }
    }
}
