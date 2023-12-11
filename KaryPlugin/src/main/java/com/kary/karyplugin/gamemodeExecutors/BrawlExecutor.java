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

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

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
    private static final int MAX_MATCH_NUM=3;
    //TODO 一场比赛6个人
    private static final int KILL_ONE_ADD =10;
    //将死者,助攻者列表
    private Map<Player, List<Player>> assistMap=new ConcurrentHashMap<>();

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
        if(damageeEntity.isDead()){//如果似了，就不算助攻
            return;
        }
        //如果两者都是玩家
        if(damageeEntity instanceof Player && damagerEntity instanceof Player){
            Player damagee= (Player) damageeEntity;
            Player damager= (Player) damagerEntity;
            //danagerList是所有伤害过damagee的玩家列表
            assistMap.compute(damagee,(key,danagerList)->{
                if(danagerList==null){
                    danagerList=new ArrayList<>();
                    // 安排任务在主线程中每20个游戏刻执行一次（1秒 = 20游戏刻）
                    int delayInTicks = 0; // 延迟0个游戏刻
                    int periodInTicks = 20; // 每20个游戏刻执行一次
                    //在主线程池中开启助攻计时
                    Bukkit.getScheduler().runTaskTimer(plugin, new AssistTimer(damagee, assistMap),delayInTicks,periodInTicks);
                }
                danagerList.add(damager);
                return danagerList;
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
            for (Player assist : deadAssists) {
                players.computeIfPresent(assist,(key2, assistRecord)->{
                    assistRecord.addOneAssist();
                    return assistRecord;
                });
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
                    for (int i=0;i<MAX_MATCH_NUM;i++) {
                        Player player=matchingPlayer.get(i);
                        players.put(player,new Record());
                        message.append(player.getName());
                        message.append(",");
                    }
                    for (Player player : matchingPlayer) {
                        player.sendRawMessage("和您在同一局的对手为"+ message+"对局开始");
                    }
                    // 安排任务在主线程中每20个游戏刻执行一次（1秒 = 20游戏刻）
                    int delayInTicks = 0; // 延迟0个游戏刻
                    int periodInTicks = 20; // 每20个游戏刻执行一次
                    //准备完毕，开始比赛线程
                    Bukkit.getScheduler().runTaskTimer(plugin,new BrawlMatch(players), delayInTicks, periodInTicks);
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
    class BrawlMatch extends TimerTask {
        Map<Player, Record> players;//Concurrent
        int second=0;
        int gameLimitTime=60;//1分钟
        //TODO 改为5分钟

        public BrawlMatch(Map<Player, Record> players) {
            this.players = players;
        }

        @Override
        public void run() {
            //游戏限时
            if (second < gameLimitTime) {
                second += 1;
            }else{
                Set<Map.Entry<Player, Record>> entrySet=players.entrySet();
                double maxKDA=Double.MIN_VALUE;
                Player mvpPlayer=null;
                for (Map.Entry<Player, Record> entry : entrySet){
                    Player player= entry.getKey();
                    Record record= entry.getValue();
                    int k= record.getKill();
                    int d= record.getDeath();
                    int a= record.getAssist();
                    double kda= (k * 1.0 + a) /((d!=0)?d:1);
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
                            gameMode,
                            mvpPlayer.getName()
                    );
                }
                players.clear();
            }
        }
    }
    class AssistTimer extends TimerTask {
        Player damagee;
        Map<Player, List<Player>> assistMap;
        int second=0;
        int assistExistLimitTime=10;//只计算10秒内的助攻

        public AssistTimer(Player damagee, Map<Player, List<Player>> assistMap) {
            this.damagee = damagee;
            this.assistMap = assistMap;
        }

        @Override
        public void run(){
                //计算10秒
            if (second < assistExistLimitTime) {
                second += 1;
            }else{
                //在不影响其他线程访问damagerList的情况下，移除键值对
                //把damagee的值damagerList安全替换为null
                assistMap.compute(damagee,(key,damagerList)-> null);
            }
        }
    }
}
