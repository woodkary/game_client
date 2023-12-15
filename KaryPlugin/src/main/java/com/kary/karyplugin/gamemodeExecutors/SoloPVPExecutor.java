package com.kary.karyplugin.gamemodeExecutors;

import com.kary.karyplugin.KaryPlugin;
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

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

//gamemode==1才符合
public class SoloPVPExecutor extends BaseExecutor {
    Map<Player,Player> playersInSoloPVP=new ConcurrentHashMap<>();
    Map<Player,Integer> playersMatchingGamemode;
    //Object[]数组对应分数int和开始时间long
    Map<Player,Object[]> playersScoreGainAndMatchStartTime=new ConcurrentHashMap<>();
    Map<Integer,Player> matchingPlayers=new ConcurrentHashMap<>();
    RecordService recordService;
    Integer gameMode=GameModeUtil.SOLOPVP_MODE;


    public SoloPVPExecutor(Map<Player,Integer> playersMatchingGamemode,RecordService recordService) {
        this.playersMatchingGamemode=playersMatchingGamemode;
        this.recordService=recordService;
    }
    @EventHandler(priority = EventPriority.MONITOR)
    public void playerQuit(PlayerQuitEvent playerQuitEvent){
        Player loser=playerQuitEvent.getPlayer();
        int level= LevelUtil.getLevel(recordService.getScoreTotal(loser.getName(),gameMode));
        //如果退出的这个人正在匹配，则把他踢出匹配等待
        //不管这个段位是否有人正在匹配，都把这个位置设为null
        matchingPlayers.compute(level,(key,p)-> null);
        if(playersInSoloPVP.isEmpty()){
            return;
        }
        //有人退出游戏，则另一人胜利
        Player winner=playersInSoloPVP.remove(loser);
        if(winner==null){
            return;
        }

        Integer maxGameId=recordService.getMaxGameId();
        if(maxGameId==null){
            maxGameId=0;
        }
        maxGameId+=1;
        playersInSoloPVP.remove(winner);
        Object[] winnerArray=playersScoreGainAndMatchStartTime.remove(winner);
        Object[] loserArray=playersScoreGainAndMatchStartTime.remove(loser);
        long duration=System.currentTimeMillis() -(Long)winnerArray[1];
        if(Integer.valueOf(5).equals(winnerArray[0])){
            recordService.addNewGame(gameMode,maxGameId,duration,winner.getName());
            KaryPlugin.updateDatabase(maxGameId,
                    duration,
                    winner.getName(),
                    1,1,
                    (Integer) winnerArray[0],
                    0,
                    (Double) winnerArray[2],
                    (Double) winnerArray[3],
                    winner.getName(),
                    gameMode
            );
            KaryPlugin.updateDatabase(maxGameId,
                    duration,
                    loser.getName(),
                    1,1,
                    (Integer) loserArray[0]-8,
                    0,
                    (Double) loserArray[2],
                    (Double) loserArray[3],
                    winner.getName(),
                    gameMode
            );
        }else if(Integer.valueOf(15).equals(winnerArray[0])){
            recordService.addNewGame(gameMode,maxGameId,System.currentTimeMillis() -(Long)winnerArray[1],winner.getName());
            KaryPlugin.updateDatabase(maxGameId,
                    duration,
                    winner.getName(),
                    1,0,
                    (Integer) winnerArray[0],
                    0,
                    (Double) winnerArray[2],
                    (Double) winnerArray[3],
                    winner.getName(),
                    gameMode
            );
            KaryPlugin.updateDatabase(maxGameId,
                    duration,
                    loser.getName(),
                    0,1,
                    (Integer) loserArray[0]-8,
                    0,
                    (Double) loserArray[2],
                    (Double) loserArray[3],
                    winner.getName(),
                    gameMode
            );
        }else{
            recordService.addNewGame(gameMode,maxGameId,System.currentTimeMillis() -(Long)winnerArray[1],winner.getName());
            KaryPlugin.updateDatabase(maxGameId,
                    duration,
                    winner.getName(),
                    0,1,
                    (Integer) winnerArray[0],
                    0,
                    (Double) winnerArray[2],
                    (Double) winnerArray[3],
                    winner.getName(),
                    gameMode
            );
            KaryPlugin.updateDatabase(maxGameId,
                    duration,
                    loser.getName(),
                    1,0,
                    (Integer) loserArray[0]-8,
                    0,
                    (Double) loserArray[2],
                    (Double) loserArray[3],
                    winner.getName(),
                    gameMode
            );
        }

        Bukkit.getServer().broadcastMessage("玩家"+loser.getName()+"退出了游戏，"+winner.getName()+"获得了胜利");
    }
    @EventHandler(priority = EventPriority.HIGH)
    public void figureDamage(EntityDamageByEntityEvent event){
        Entity damager=event.getDamager();
        Entity damagee=event.getEntity();
        double damage=event.getDamage();
        if(damager instanceof Player&&damagee instanceof Player&&
           playersInSoloPVP.containsKey(damager)&&playersInSoloPVP.containsKey(damagee)){
            Object[] damagerArray=playersScoreGainAndMatchStartTime.get(damager);
            damagerArray[2]=(Double)damagerArray[2]+damage;
            Object[] damageeArray=playersScoreGainAndMatchStartTime.get(damagee);
            damageeArray[3]=(Double)damageeArray[3]+damage;
        }
    }

    @EventHandler(priority = EventPriority.HIGH)
    public void oneMatchOver(PlayerDeathEvent event){
        if(playersInSoloPVP.isEmpty()){
            return;
        }
        //有人被杀死了
        Player loser=event.getEntity();//失败者
        Player winner=playersInSoloPVP.get(loser);//胜利者
        if(winner==null){
            return;
        }
        Integer maxGameId=recordService.getMaxGameId();
        if(maxGameId==null){
            maxGameId=0;
        }
        maxGameId+=1;
        Object[] winnerScoreGainAndStartTime=playersScoreGainAndMatchStartTime.get(winner);
        winnerScoreGainAndStartTime[0]=(Integer)winnerScoreGainAndStartTime[0]+15;
        /*playersScoreGainAndMatchStartTime.put(winner,winnerScoreGainAndStartTime);*/

        Object[] loserScoreGainAndStartTime=playersScoreGainAndMatchStartTime.get(loser);
        loserScoreGainAndStartTime[0]=(Integer)loserScoreGainAndStartTime[0]-10;
        /*playersScoreGainAndMatchStartTime.put(loser,loserScoreGainAndStartTime);*/
        if(Integer.valueOf(30).equals(winnerScoreGainAndStartTime[0])){
            //2:0，游戏结束
            playersInSoloPVP.remove(winner);
            playersInSoloPVP.remove(loser);
            Object[] winnerArray=playersScoreGainAndMatchStartTime.remove(winner);
            Object[] loserArray=playersScoreGainAndMatchStartTime.remove(loser);
            long duration=System.currentTimeMillis() -(Long)winnerArray[1];
            recordService.addNewGame(gameMode,maxGameId,System.currentTimeMillis() -(Long)winnerArray[1],winner.getName());
            KaryPlugin.updateDatabase(maxGameId,
                    duration,
                    winner.getName(),
                    2,0,
                    (Integer) winnerArray[0],
                    0,
                    (Double) winnerArray[2],
                    (Double) winnerArray[3],
                    winner.getName(),
                    gameMode
            );
            KaryPlugin.updateDatabase(maxGameId,
                    duration,
                    loser.getName(),
                    0,2,
                    (Integer) loserArray[0],
                    0,
                    (Double) loserArray[2],
                    (Double) loserArray[3],
                    winner.getName(),
                    gameMode
            );
            Bukkit.getServer().broadcastMessage("比赛结束，胜利者"+winner.getName()+"比分为2:0");
            winner.sendRawMessage("请选择游戏模式");
            winner.setOp(true);
            winner.performCommand(CommandUtil.COMMAND_SOLO_PVP);
            winner.performCommand(CommandUtil.COMMAND_BRAWL);
            winner.setOp(false);
            loser.sendRawMessage("请选择游戏模式");
            loser.setOp(true);
            loser.performCommand(CommandUtil.COMMAND_SOLO_PVP);
            loser.performCommand(CommandUtil.COMMAND_BRAWL);
            loser.setOp(false);
        }
        if(Integer.valueOf(20).equals(winnerScoreGainAndStartTime[0])){
            //2:1结束
            playersInSoloPVP.remove(winner);
            playersInSoloPVP.remove(loser);
            Object[] winnerArray=playersScoreGainAndMatchStartTime.remove(winner);
            Object[] loserArray=playersScoreGainAndMatchStartTime.remove(loser);
            long duration=System.currentTimeMillis() -(Long)winnerArray[1];
            recordService.addNewGame(gameMode,maxGameId,System.currentTimeMillis() -(Long)winnerArray[1],winner.getName());
            KaryPlugin.updateDatabase(maxGameId,
                    duration,
                    winner.getName(),
                    2,1,
                    (Integer) winnerArray[0],
                    0,
                    (Double) winnerArray[2],
                    (Double) winnerArray[3],
                    winner.getName(),
                    gameMode
            );
            KaryPlugin.updateDatabase(maxGameId,
                    duration,
                    loser.getName(),
                    1,2,
                    (Integer) loserArray[0],
                    0,
                    (Double) loserArray[2],
                    (Double) loserArray[3],
                    winner.getName(),
                    gameMode
            );
            Bukkit.getServer().broadcastMessage("比赛结束，胜利者"+winner.getName()+"比分为2:1");
            winner.sendRawMessage("请选择游戏模式");
            winner.setOp(true);
            winner.performCommand(CommandUtil.COMMAND_SOLO_PVP);
            winner.performCommand(CommandUtil.COMMAND_BRAWL);
            winner.setOp(false);
            loser.sendRawMessage("请选择游戏模式");
            loser.setOp(true);
            loser.performCommand(CommandUtil.COMMAND_SOLO_PVP);
            loser.performCommand(CommandUtil.COMMAND_BRAWL);
            loser.setOp(false);
        }
        /*Bukkit.getServer().broadcastMessage("玩家"+winner.getName()+"击败了"+loser.getName());
        Bukkit.getServer().broadcastMessage(winner.getName()+"造成伤害"+winnerScoreGainAndStartTime[2]+",承伤"+winnerScoreGainAndStartTime[3]);
        Bukkit.getServer().broadcastMessage(loser.getName()+"造成伤害"+loserScoreGainAndStartTime[2]+",承伤"+loserScoreGainAndStartTime[3]);*/
    }

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] args) {
        boolean result = false;
        if (commandSender instanceof Player) {
            Integer gamemode=playersMatchingGamemode.get(commandSender);
            int level= LevelUtil.getLevel(recordService.getScoreTotal(commandSender.getName(),gameMode));
            if(gamemode==null){
                //如果此人还未进入匹配，给他加入匹配
                Player matchingPlayer=matchingPlayers.get(level);
                if(matchingPlayer==null){
                    //如果此时没有一人正在匹配，把此人设为正在匹配，并加入记录此时正在匹配人的集合
                    playersMatchingGamemode.put((Player) commandSender, GameModeUtil.SOLOPVP_MODE);
                    matchingPlayers.put(level,(Player) commandSender);
                    ((Player) commandSender).sendRawMessage("您正在匹配单人PVP，等待其他玩家加入游戏……");
                    commandSender.setOp(true);
                   ((Player) commandSender).performCommand(CommandUtil.COMMAND_QUIT_MATCHING);
                    commandSender.setOp(false);
                }else{
                    //将他的对手设为正在匹配的那人，将此人从时正在匹配人的集合移除，此时正在匹配的记录设为null
                    playersInSoloPVP.put((Player) commandSender,matchingPlayer);
                    playersInSoloPVP.put(matchingPlayer, (Player) commandSender);

                    //三局两胜比赛开始，初始双方都为0分,游戏开始时间均为当前时间，初始伤害、承伤均为0
                    long gameStart=System.currentTimeMillis();
                    Object[] commandSenderArray=new Object[4];
                    commandSenderArray[0]= 0;
                    commandSenderArray[1]= gameStart;
                    commandSenderArray[2]=0.0;
                    commandSenderArray[3]=0.0;
                    playersScoreGainAndMatchStartTime.put((Player) commandSender,commandSenderArray);

                    Object[] matchingPlayerArray=new Object[4];
                    matchingPlayerArray[0]= 0;
                    matchingPlayerArray[1]= gameStart;
                    matchingPlayerArray[2]=0.0;
                    matchingPlayerArray[3]=0.0;
                    playersScoreGainAndMatchStartTime.put(matchingPlayer,matchingPlayerArray);

                    playersMatchingGamemode.remove(matchingPlayer);
                    ((Player) commandSender).sendRawMessage("您已匹配对手"+matchingPlayer.getName()+",对局开始");
                    matchingPlayer.sendRawMessage("您的对手是"+commandSender.getName()+",对局开始");
                    matchingPlayers.remove(level);
                    result=true;
                }
            }else{
                if(gamemode==GameModeUtil.SOLOPVP_MODE){
                    ((Player) commandSender).sendRawMessage("您已在单人PVP匹配中，无需再加入匹配");
                }else{
                    ((Player) commandSender).sendRawMessage("您正在匹配其他游戏，请先退出");
                }
            }
        }
        return result;
    }

    @Override
    public void playerQuitMatching(Player player) {
        playersMatchingGamemode.remove(player);
        int level= LevelUtil.getLevel(recordService.getScoreTotal(player.getName(),gameMode));
        matchingPlayers.remove(level);
    }
}
