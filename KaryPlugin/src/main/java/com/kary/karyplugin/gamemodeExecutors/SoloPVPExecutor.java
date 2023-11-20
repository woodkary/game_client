package com.kary.karyplugin.gamemodeExecutors;

import com.kary.karyplugin.service.RecordService;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

//gamemode==1才符合
public class SoloPVPExecutor implements CommandExecutor, Listener {
    private Map<Player,Player> playersInSoloPVP=new ConcurrentHashMap<>();
    private Map<Player,Integer> playersMatchingGamemode;
    //Object[]数组对应分数int和开始时间long
    private Map<Player,Object[]> playersScoreGainAndMatchStartTime=new ConcurrentHashMap<>();
    private Player matchingPlayer;
    private RecordService recordService;
    private final Object lock=new Object();

    private synchronized void updateDatabase(Long duration,
                                             String usernamePlay1,
                                             String usernamePlay2,
                                             Integer killPlay1,
                                             Integer killPlay2,
                                             Integer deathPlay1,
                                             Integer deathPlay2,
                                             Integer scoreGainPlay1,
                                             Integer scoreGainPlay2){
        synchronized (lock){
            Integer scoreTotalPlay1=recordService.getScoreTotal(usernamePlay1);
            Integer scoreTotalPlay2=recordService.getScoreTotal(usernamePlay2);
            recordService.addScore(usernamePlay1,scoreGainPlay1);
            recordService.addScore(usernamePlay2,scoreGainPlay2);
            recordService.recordNewMatch(
                    duration,
                    usernamePlay1,
                    usernamePlay2,
                    killPlay1,killPlay2,
                    deathPlay1,
                    deathPlay2,
                    scoreGainPlay1,
                    scoreGainPlay2,
                    scoreTotalPlay1,
                    scoreTotalPlay2
            );
        }
    }

    public SoloPVPExecutor(Map<Player,Integer> playersMatchingGamemode,RecordService recordService) {
        this.playersMatchingGamemode=playersMatchingGamemode;
        this.recordService=recordService;
    }
    @EventHandler(priority = EventPriority.MONITOR)
    public void playerQuit(PlayerQuitEvent playerQuitEvent){
        if(playersInSoloPVP.size()==0){
            return;
        }
        //有人退出游戏，则另一人胜利
        //TODO 这是有一人中途退出的情况，为胜利方加分，中途退出的人多扣8分，记录这场比赛
        Player loser=playerQuitEvent.getPlayer();
        Player winner=playersInSoloPVP.remove(loser);
        playersInSoloPVP.remove(winner);

        Object[] winnerArray=playersScoreGainAndMatchStartTime.remove(winner);
        Object[] loserArray=playersScoreGainAndMatchStartTime.remove(loser);
        if(Integer.valueOf(5).equals(winnerArray[0])){
            updateDatabase(Long.valueOf(System.currentTimeMillis())-(Long)winnerArray[1],
                    winner.getName(),
                    loser.getName(),
                    1,1,1,1,
                    (Integer) winnerArray[0],
                    (Integer) loserArray[0]-8
            );
        }else if(Integer.valueOf(15).equals(winnerArray[0])){
            updateDatabase(Long.valueOf(System.currentTimeMillis())-(Long)winnerArray[1],
                    winner.getName(),
                    loser.getName(),
                    1,0,0,1,
                    (Integer) winnerArray[0],
                    (Integer) loserArray[0]-8
            );
        }else{
            updateDatabase(Long.valueOf(System.currentTimeMillis())-(Long)winnerArray[1],
                    winner.getName(),
                    loser.getName(),
                    0,1,1,0,
                    (Integer) winnerArray[0],
                    (Integer) loserArray[0]-8
            );
        }

        Bukkit.getServer().broadcastMessage("玩家"+loser.getName()+"退出了游戏，"+winner.getName()+"获得了胜利");
    }


    @EventHandler(priority = EventPriority.MONITOR)
    public void oneMatchOver(PlayerDeathEvent event){
        if(playersInSoloPVP.size()==0){
            return;
        }
        //有人被杀死了
        Player loser=event.getEntity();//失败者
        Player winner=loser.getKiller();//胜利者

        Object[] winnerScoreGainAndStartTime=playersScoreGainAndMatchStartTime.get(winner);
        winnerScoreGainAndStartTime[0]=(Integer)winnerScoreGainAndStartTime[0]+15;
        playersScoreGainAndMatchStartTime.put(winner,winnerScoreGainAndStartTime);

        Object[] loserScoreGainAndStartTime=playersScoreGainAndMatchStartTime.get(loser);
        loserScoreGainAndStartTime[0]=(Integer)loserScoreGainAndStartTime[0]-10;
        playersScoreGainAndMatchStartTime.put(loser,loserScoreGainAndStartTime);
        //TODO 为胜利方加分，记录这场比赛，目前暂定赢+15，输-10，更新数据库
        if(Integer.valueOf(30).equals(winnerScoreGainAndStartTime[0])){
            //2:0，游戏结束
            playersInSoloPVP.remove(winner);
            playersInSoloPVP.remove(loser);
            Object[] winnerArray=playersScoreGainAndMatchStartTime.remove(winner);
            Object[] loserArray=playersScoreGainAndMatchStartTime.remove(loser);
            updateDatabase(Long.valueOf(System.currentTimeMillis())-(Long)winnerArray[1],
                    winner.getName(),
                    loser.getName(),
                    2,0,0,2,
                    (Integer) winnerArray[0],
                    (Integer) loserArray[0]
            );
        }
        if(Integer.valueOf(20).equals(winnerScoreGainAndStartTime[0])){
            //2:1结束
            playersInSoloPVP.remove(winner);
            playersInSoloPVP.remove(loser);
        }   Object[] winnerArray=playersScoreGainAndMatchStartTime.remove(winner);
        Object[] loserArray=playersScoreGainAndMatchStartTime.remove(loser);
        updateDatabase(Long.valueOf(System.currentTimeMillis())-(Long)winnerArray[1],
                winner.getName(),
                loser.getName(),
                2,1,1,2,
                (Integer) winnerArray[0],
                (Integer) loserArray[0]
        );
        Bukkit.getServer().broadcastMessage("玩家"+winner.getName()+"击败了"+loser.getName());
    }

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] args) {
        boolean result = false;
        if ("soloPVP".equals(args[0]) && commandSender instanceof Player) {
            Integer gamemode=playersMatchingGamemode.get(commandSender);
            if(gamemode==null){
                //如果此人还未进入匹配，给他加入匹配
                if(matchingPlayer==null){
                    //如果此时没有一人正在匹配，把此人设为正在匹配，并加入记录此时正在匹配人的集合
                    playersMatchingGamemode.put((Player) commandSender,QuitMatchingExecutor.SOLOPVP_MODE);
                    matchingPlayer= (Player) commandSender;
                    ((Player) commandSender).sendRawMessage("您正在匹配单人PVP，等待其他玩家加入游戏……");
                }else{
                    //将他的对手设为正在匹配的那人，将此人从时正在匹配人的集合移除，此时正在匹配的记录设为null
                    playersInSoloPVP.put((Player) commandSender,matchingPlayer);
                    playersInSoloPVP.put(matchingPlayer, (Player) commandSender);

                    //三局两胜比赛开始，初始双方都为0分,游戏开始时间均为当前时间
                    long gameStart=System.currentTimeMillis();
                    Object[] commandSenderArray=new Object[2];
                    commandSenderArray[0]=Integer.valueOf(0);
                    commandSenderArray[1]=Long.valueOf(gameStart);
                    playersScoreGainAndMatchStartTime.put((Player) commandSender,commandSenderArray);

                    Object[] matchingPlayerArray=new Object[2];
                    matchingPlayerArray[0]=Integer.valueOf(0);
                    matchingPlayerArray[1]=Long.valueOf(gameStart);
                    playersScoreGainAndMatchStartTime.put(matchingPlayer,matchingPlayerArray);

                    playersMatchingGamemode.remove(matchingPlayer);
                    ((Player) commandSender).sendRawMessage("您已匹配对手"+matchingPlayer.getName()+",对局开始");
                    matchingPlayer.sendRawMessage("您的对手是"+commandSender.getName()+",对局开始");
                    matchingPlayer=null;
                    result=true;
                }
            }else{
                if(gamemode==1){
                    ((Player) commandSender).sendRawMessage("您已在单人PVP匹配中，无需再加入匹配");
                }else{
                    ((Player) commandSender).sendRawMessage("您正在匹配其他游戏，请先退出");
                }
            }
        }
        return result;
    }
}
