package com.kary.karyplugin.gamemodeExecutors;

import com.kary.karyplugin.KaryPlugin;
import com.kary.karyplugin.pojo.Record;
import com.kary.karyplugin.service.RecordService;
import com.kary.karyplugin.utils.CommandUtil;
import com.kary.karyplugin.utils.GameModeUtil;
import com.kary.karyplugin.utils.LevelUtil;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentSkipListSet;
import java.util.concurrent.CopyOnWriteArraySet;

/**
 * @author:123
 */
public class BrawlExecutor extends BaseExecutor {
    Integer gameMode= GameModeUtil.BRAWL_MODE;
    KaryPlugin plugin;
    Map<Player, Record> players=new ConcurrentHashMap<>();
    RecordService recordService;
    Map<Player,Integer> playersMatchingGamemode;
    Map<Integer,Set<Player>> matchingPlayers=new ConcurrentHashMap<>();
    Map<Player,Long[]> playerDuration=new ConcurrentHashMap<>();
    Player mvpPlayer;
    static final int MAX_MATCH_NUM=3;
    //TODO 一场比赛6个人
    private static final int KILL_ONE_ADD =10;
    private static final int ASSIST_ONE_ADD =5;
    private static final int SLAIN_ONE_ADD =-4;
    //将死者,助攻者列表
    Map<Player, ConcurrentSkipListSet<PlayerAndTime>> assistMap=new ConcurrentHashMap<>();

    @Override
    public void playerQuitMatching(Player player) {
        playersMatchingGamemode.remove(player);
        int level= LevelUtil.getLevel(recordService.getScoreTotal(player.getName(),gameMode));
        matchingPlayers.computeIfPresent(level,(key, matchingPlayer)->{
            matchingPlayer.remove(player);
            return matchingPlayer;
        });
    }

    //此类用于记录助攻者和助攻时间，用player比较是否相等，以及用加入时间比较大小
    static class PlayerAndTime implements Comparable<PlayerAndTime>{
        Player player;
        long time;

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            PlayerAndTime that = (PlayerAndTime) o;
            return Objects.equals(player.getUniqueId(), that.player.getUniqueId())&&Objects.equals(player, that.player);
        }

        @Override
        public int hashCode() {
            return Objects.hash(player.getUniqueId(),player);
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
            if(hashCode()==o.hashCode()){
                return 0;
            }else if(this.equals(o)){
                return 0;
            }
            if(time-o.time>0){
                return 1;
            }else if(time-o.time<0){
                return -1;
            }else {
                return player.getUniqueId().compareTo(o.player.getUniqueId());
            }
        }
    }

    public BrawlExecutor(RecordService recordService, Map<Player, Integer> playersMatchingGamemode,KaryPlugin plugin) {
        this.recordService = recordService;
        this.playersMatchingGamemode = playersMatchingGamemode;
        this.plugin=plugin;
        //首先，所有段位都应该有个匹配集合
        matchingPlayers.put(LevelUtil.COPPER, new CopyOnWriteArraySet<>());
        matchingPlayers.put(LevelUtil.SILVER, new CopyOnWriteArraySet<>());
        matchingPlayers.put(LevelUtil.GOLD, new CopyOnWriteArraySet<>());
        matchingPlayers.put(LevelUtil.PLATINUM, new CopyOnWriteArraySet<>());
        matchingPlayers.put(LevelUtil.DIAMOND,new CopyOnWriteArraySet<>());
        matchingPlayers.put(LevelUtil.MASTER, new CopyOnWriteArraySet<>());
        matchingPlayers.put(LevelUtil.KING, new CopyOnWriteArraySet<>());
    }
    @EventHandler(priority = EventPriority.MONITOR)
    public void playerQuit(PlayerQuitEvent event){
        Player player = event.getPlayer();
        Long[] times=playerDuration.get(player);
        //说明此人未进入比赛，但可能在匹配
        if(times==null){
            int level= LevelUtil.getLevel(recordService.getScoreTotal(player.getName(),gameMode));
            //在退出玩家的段位列表，不管有无此玩家，都把他移除
            matchingPlayers.computeIfPresent(level,(key, matchingPlayer)->{
                matchingPlayer.remove(player);
                return matchingPlayer;
            });
            return;
        }
        times[1]=System.currentTimeMillis();
        Record record=players.get(player);
        //中途退出。排位分扣除15分
        record.setScoreGain(-15);

    }
    @EventHandler(priority = EventPriority.HIGH)
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

    @EventHandler(priority = EventPriority.HIGH)
    public void playerKill(PlayerDeathEvent event){
        Player deadPlayer=event.getEntity();
        Player killer=deadPlayer.getKiller();
        //更新死亡者的记录
        players.computeIfPresent(deadPlayer,(key, deadRecord)->{
            deadRecord.addOneDeath();
            deadRecord.addScore(SLAIN_ONE_ADD);//被杀，扣分
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
                        //助攻一人，加分
                        assistRecord.addScore(ASSIST_ONE_ADD);
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
                //对于一个level，操控一个matchingPlayer集合
                matchingPlayers.computeIfPresent(level,(key, matchingPlayer)->{
                    if(matchingPlayer.isEmpty()){
                        this.mvpPlayer= (Player) commandSender;
                    }
                    matchingPlayer.add((Player) commandSender);
                    ((Player) commandSender).sendRawMessage("您正在匹配大乱斗，等待其他玩家加入游戏……");
                    if(matchingPlayer.size()<MAX_MATCH_NUM) {//给正在匹配的人发送取消指令
                        commandSender.setOp(true);
                        ((Player) commandSender).performCommand(CommandUtil.COMMAND_QUIT_MATCHING);
                        commandSender.setOp(false);
                    }
                    if(matchingPlayer.size()==MAX_MATCH_NUM){//达到最大待匹配人数
                        StringBuilder message=new StringBuilder();
                        for (Player player:matchingPlayer) {
                            //对于在场每一位玩家，都有一个攻击者列表
                            assistMap.put(player, new ConcurrentSkipListSet<>());
                            players.put(player, new Record());
                            message.append(player.getName());
                            message.append(",");
                            Long[] times = new Long[2];
                            //times[0]为开始时间，times[1]为结束时间
                            times[0] = System.currentTimeMillis();
                            times[1] = Long.MAX_VALUE;
                            playerDuration.put(player, times);
                            playersMatchingGamemode.remove(player);
                            player.sendRawMessage("和您在同一局的对手为" + message + "对局开始");
                        }
                        // 安排任务在主线程中每20个游戏刻执行一次（1秒 = 20游戏刻）
                        int delayInTicks = 0; // 延迟0个游戏刻
                        int periodInTicks = 20; // 每20个游戏刻执行一次
                        //准备完毕，开始比赛线程
                        BukkitRunnable assistTimer= new AssistTimer(assistMap);//先开启助攻计时器
                        BukkitRunnable brawlMatch = new BrawlMatch(players,assistTimer);//再开启比赛计时器
                        brawlMatch.runTaskTimer(plugin, delayInTicks, periodInTicks);
                        assistTimer.runTaskTimer(plugin, delayInTicks, periodInTicks);
                        matchingPlayer.clear();
                    }
                    return matchingPlayer;
                });
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
    class BrawlMatch extends BukkitRunnable {
        Map<Player, Record> players;//Concurrent
        int second=0;
        int gameLimitTime=30;//5分钟
        Integer maxGameId;
        BukkitRunnable assistTimer;
        //TODO 改为5分钟

        public BrawlMatch(Map<Player, Record> players,BukkitRunnable assistTimer) {
            this.players = players;
            this.assistTimer=assistTimer;
            maxGameId=recordService.getMaxGameId();
            if(maxGameId==null){
                maxGameId=0;
            }
            maxGameId+=1;
        }

        @Override
        public void run() {
            //游戏限时
            if(second < gameLimitTime) {
                second += 1;
            }else{//比赛结束
                Set<Map.Entry<Player, Record>> entrySet=players.entrySet();
                final double[] maxKDA = {Double.MIN_VALUE};
                Map<Player,Long> playerDur=new HashMap<>();//临时记录每位玩家的游戏时长
                for (Map.Entry<Player, Record> entry : entrySet){
                    Player player= entry.getKey();
                    Record record= entry.getValue();
                    playerDuration.computeIfPresent(player,(key, times)->{
                        //playerRemove[0]为开始时间，playerRemove[1]为结束时间
                        //如果结束时间为Long.MAX_VALUE，说明玩家没有退出，游戏时长为5分钟
                                playerDur.put(player,(times[1]==Long.MAX_VALUE)?300000L:(times[1]-times[0]));
                                int k= record.getKill();
                                int d= record.getDeath();
                                int a= record.getAssist();
                                double kda= (k * 1.0 + a*0.7) /((d!=0)?d:1);
                                if(kda> maxKDA[0]){
                                    maxKDA[0] =kda;
                                    mvpPlayer=player;
                                }
                        return null;
                    });


                }
                Bukkit.getServer().broadcastMessage("比赛结束");
                recordService.addNewGame(gameMode,maxGameId,300000L,mvpPlayer.getName());
                //利用ConcurrentHashMap来同步操作
                for (Map.Entry<Player, Record> entry : entrySet) {
                    Player player= entry.getKey();
                    Record record=entry.getValue();
                    KaryPlugin.updateDatabase(
                            maxGameId,
                            playerDur.get(player),
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
                    player.sendRawMessage("请选择游戏模式");
                    player.setOp(true);
                    player.performCommand(CommandUtil.COMMAND_SOLO_PVP);
                    player.performCommand(CommandUtil.COMMAND_BRAWL);
                    player.setOp(false);
                }
                players.clear();
                //结束比赛线程以及助攻线程
                this.cancel();
                assistTimer.cancel();
            }
        }
    }
    //这是个计时器，每秒钟检查一次助攻列表，每十秒移除一个加入时间最早的助攻者
    static class AssistTimer extends BukkitRunnable {
        Map<Player, ConcurrentSkipListSet<PlayerAndTime>> assistMap;
        int second=0;
        int assistExistLimitTime=10;//只计算10秒内的助攻

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
