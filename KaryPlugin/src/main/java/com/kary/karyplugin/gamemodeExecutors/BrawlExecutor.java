package com.kary.karyplugin.gamemodeExecutors;

import com.kary.karyplugin.KaryPlugin;
import com.kary.karyplugin.pojo.Record;
import com.kary.karyplugin.service.RecordService;
import com.kary.karyplugin.utils.GameModeUtil;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;

import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author:123
 */
/*TODO 加入助攻计时线程
 *  playerKill加入助攻记录
 * 加入输指令匹配机制
 * 加入<段位,正在匹配玩家列表>的Map
 * */
public class BrawlExecutor implements Listener {
    private Integer gameMode= GameModeUtil.BRAWL_MODE;
    private Map<Player, Record> players=new ConcurrentHashMap();
    private RecordService recordService;
    private Map<Player,Integer> playersMatchingGamemode;
    //用于启动比赛的线程池
    private ExecutorService matches= Executors.newScheduledThreadPool(16);

    public BrawlExecutor(RecordService recordService, Map<Player, Integer> playersMatchingGamemode) {
        this.recordService = recordService;
        this.playersMatchingGamemode = playersMatchingGamemode;
    }

    @EventHandler(priority = EventPriority.MONITOR)
    public void playerKill(PlayerDeathEvent event){
        Player deadPlayer=event.getEntity();
        Player killer=deadPlayer.getKiller();
        //更新死亡者的记录
        Record deadRecord=players.get(deadPlayer);
        deadRecord.addOneDeath();
        players.put(deadPlayer,deadRecord);
        //更新杀人者的记录
        Record killerRecord=players.get(killer);
        killerRecord.addOneKill();
        players.put(killer,killerRecord);

    }

    //当匹配完时启动一个新的比赛线程
    class BrawlMatch implements Callable<Void> {
        private Map<Player, Record> players;//Concurrent
        private int second=0;
        private int gameLimitTime=300;

        public BrawlMatch(Map<Player, Record> players) {
            this.players = players;
        }

        @Override
        public Void call() throws Exception {
            //游戏限时
            while(second<gameLimitTime){
                second+=1;
                Thread.sleep(1000);
            }
            //利用ConcurrentHashMap来同步操作
            for (Map.Entry<Player, Record> entry : players.entrySet()) {
                Player player= entry.getKey();
                Record record=entry.getValue();
                KaryPlugin.updateDatabase(
                        300000L,
                        player.getName(),
                        record.getKill(),
                        record.getDeath(),
                        record.getScoreGain(),
                        record.getAssist(),
                        gameMode);
            }

            return null;
        }
    }



}
