package com.kary.karyplugin.gamemodeExecutors;

import com.kary.karyplugin.mapProcessors.MatchingPlayersMapProcessor;
import com.kary.karyplugin.service.impl.RecordServiceImpl;
import com.kary.karyplugin.utils.LevelUtil;
import org.bukkit.command.Command;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.junit.Test;

import java.lang.reflect.Field;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import static com.kary.karyplugin.utils.GameModeUtil.BRAWL_MODE;
import static com.kary.karyplugin.utils.GameModeUtil.SOLOPVP_MODE;
import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.*;

/**
 * @author:123
 */
public class SoloPVPExecutorTest {
    private int maxGameid=2;
    //测试玩家在匹配中退出游戏
    @Test
    public void soloPVPQuitTest_NotInMatch() throws NoSuchFieldException, IllegalAccessException {
        Player loser = mock(Player.class);
        when(loser.getName()).thenReturn("kary");
        PlayerQuitEvent event = new PlayerQuitEvent(loser, "kary退出了游戏");

        Map<Player, Integer> playersMatchingGamemode = new ConcurrentHashMap<>();
        RecordServiceImpl recordService = mock(RecordServiceImpl.class);
        when(recordService.getMaxGameId()).thenReturn(maxGameid);
        maxGameid+=1;
        SoloPVPExecutor soloPVPExecutor = new SoloPVPExecutor(playersMatchingGamemode, recordService);

        //执行操作
        soloPVPExecutor.playerQuit(event);

        //获取matchingPlayers
        Field privateField = SoloPVPExecutor.class.getDeclaredField("matchingPlayers");
        privateField.setAccessible(true);
        Map<Integer, Player> matchingPlayers = (Map<Integer, Player>) privateField.get(soloPVPExecutor);
        Map<Integer, Player> matchingPlayersMock = spy(matchingPlayers);

        Integer gameMode = SOLOPVP_MODE;
        int level = LevelUtil.getLevel(recordService.getScoreTotal(loser.getName(), gameMode));
        //验证matchingPlayers是否做了移除操作
        MatchingPlayersMapProcessor processor = new MatchingPlayersMapProcessor(matchingPlayersMock);
        processor.removePlayerInThisLevel(level, loser);
        verify(matchingPlayersMock).compute(eq(level), any());

        //获取playersInSoloPVP
        privateField = SoloPVPExecutor.class.getDeclaredField("playersInSoloPVP");
        privateField.setAccessible(true);
        Map<Player, Player> playersInSoloPVP = (Map<Player, Player>) privateField.get(soloPVPExecutor);
        Map<Player, Player> playersInSoloPVPMock = spy(playersInSoloPVP);
        //验证playersInSoloPVP是否做了移除操作
        verify(playersInSoloPVPMock).remove(loser);

    }

    //测试玩家在比赛中退出游戏
    @Test
    public void soloPVPQuitTest_InMatch() throws NoSuchFieldException, IllegalAccessException {
        Player loser = mock(Player.class);
        when(loser.getName()).thenReturn("kary");
        PlayerQuitEvent event = new PlayerQuitEvent(loser, "kary退出了游戏");

        Map<Player, Integer> playersMatchingGamemode = new ConcurrentHashMap<>();
        RecordServiceImpl recordService = mock(RecordServiceImpl.class);
        when(recordService.getMaxGameId()).thenReturn(maxGameid);
        maxGameid+=1;
        SoloPVPExecutor soloPVPExecutor = new SoloPVPExecutor(playersMatchingGamemode, recordService);

        // 获取playersInSoloPVP并添加模拟玩家
        Field privateField = SoloPVPExecutor.class.getDeclaredField("playersInSoloPVP");
        privateField.setAccessible(true);
        Map<Player, Player> playersInSoloPVP = spy((Map<Player, Player>) privateField.get(soloPVPExecutor));
        Player winner = mock(Player.class);
        when(winner.getName()).thenReturn("kary1");
        playersInSoloPVP.put(winner, loser);
        playersInSoloPVP.put(loser, winner);
        privateField.set(soloPVPExecutor, playersInSoloPVP);

        //模拟放入记录数组
        privateField = SoloPVPExecutor.class.getDeclaredField("playersScoreGainAndMatchStartTime");
        privateField.setAccessible(true);
        Map<Player, Object[]> playersScoreGainAndMatchStartTime = spy((Map<Player, Object[]>) privateField.get(soloPVPExecutor));
        Object[] loserArray = new Object[4];
        loserArray[0] = 0;
        loserArray[1] = 114514L;
        loserArray[2] = 0.0;
        loserArray[3] = 0.0;
        playersScoreGainAndMatchStartTime.put(loser, loserArray);
        Object[] winnerArray = new Object[4];
        winnerArray[0] = 0;
        winnerArray[1] = 114514L;
        winnerArray[2] = 0.0;
        winnerArray[3] = 0.0;
        playersScoreGainAndMatchStartTime.put(winner, winnerArray);
        privateField.set(soloPVPExecutor, playersScoreGainAndMatchStartTime);


        //执行操作

        try {
            soloPVPExecutor.playerQuit(event);
        } catch (Exception e) {
        }
        // 验证playersInSoloPVP是否做了移除操作
        verify(playersInSoloPVP).remove(loser);
        verify(playersInSoloPVP).remove(winner);
        // 验证playersScoreGainAndMatchStartTime是否做了移除操作
        verify(playersScoreGainAndMatchStartTime).remove(loser);
        verify(playersScoreGainAndMatchStartTime).remove(winner);
    }
    @Test
    public void oneMatchOverTest_NotInMatch(){
        Player loser = mock(Player.class);
        when(loser.getName()).thenReturn("kary");
        PlayerDeathEvent event = new PlayerDeathEvent(loser,new ArrayList<>(),0, null);

        Map<Player, Integer> playersMatchingGamemode = new ConcurrentHashMap<>();
        RecordServiceImpl recordService = mock(RecordServiceImpl.class);
        when(recordService.getMaxGameId()).thenReturn(maxGameid);
        maxGameid+=1;
        SoloPVPExecutor soloPVPExecutor = new SoloPVPExecutor(playersMatchingGamemode, recordService);


        soloPVPExecutor.oneMatchOver(event);
        verify(recordService, never()).getMaxGameId();

    }
    @Test
    public void oneMatchOverTest_30() throws NoSuchFieldException, IllegalAccessException, ParseException {
        Player loser = mock(Player.class);
        when(loser.getName()).thenReturn("kary");
        Player winner = mock(Player.class);
        when(winner.getName()).thenReturn("kary1");
        PlayerDeathEvent event = new PlayerDeathEvent(loser,new ArrayList<>(),0, null);


        Map<Player, Integer> playersMatchingGamemode = new ConcurrentHashMap<>();
        RecordServiceImpl recordService = mock(RecordServiceImpl.class);
        when(recordService.getMaxGameId()).thenReturn(maxGameid);
        maxGameid+=1;
        SoloPVPExecutor soloPVPExecutor = new SoloPVPExecutor(playersMatchingGamemode, recordService);

        Field privateField = SoloPVPExecutor.class.getDeclaredField("playersScoreGainAndMatchStartTime");
        privateField.setAccessible(true);
        Map<Player,Object[]> playersScoreGainAndMatchStartTime = spy((Map<Player, Object[]>) privateField.get(soloPVPExecutor));
        Object[] loserArray = new Object[4];
        loserArray[0] = 0;
        loserArray[1] = System.currentTimeMillis();
        loserArray[2] = 0.0;
        loserArray[3] = 0.0;
        playersScoreGainAndMatchStartTime.put(loser, loserArray);
        Object[] winnerArray = new Object[4];
        winnerArray[0] = 0;
        winnerArray[1] = System.currentTimeMillis();
        winnerArray[2] = 0.0;
        winnerArray[3] = 0.0;
        playersScoreGainAndMatchStartTime.put(winner, winnerArray);
        privateField.set(soloPVPExecutor, playersScoreGainAndMatchStartTime);

        privateField = SoloPVPExecutor.class.getDeclaredField("playersInSoloPVP");
        privateField.setAccessible(true);
        Map<Player, Player> playersInSoloPVP = spy((Map<Player, Player>) privateField.get(soloPVPExecutor));
        playersInSoloPVP.put(winner, loser);
        playersInSoloPVP.put(loser, winner);
        privateField.set(soloPVPExecutor, playersInSoloPVP);


        //执行两次同一个玩家死亡，也就是2:0
        try{
            soloPVPExecutor.oneMatchOver(event);
        }catch (Exception e){}
        try{
            soloPVPExecutor.oneMatchOver(event);
        }catch (Exception e){}

        verify(recordService,times(2)).getMaxGameId();
        //Assert that the winner's score is 30 and the loser's score is -10
        assertEquals(30,winnerArray[0]);
        assertEquals (-20,loserArray[0]);
    }
    @Test
    public void oneMatchOverTest_20() throws NoSuchFieldException, IllegalAccessException, ParseException {
        Player loser = mock(Player.class);
        when(loser.getName()).thenReturn("kary");
        Player winner = mock(Player.class);
        when(winner.getName()).thenReturn("kary1");
        PlayerDeathEvent event = new PlayerDeathEvent(loser,new ArrayList<>(),0, null);


        Map<Player, Integer> playersMatchingGamemode = new ConcurrentHashMap<>();
        RecordServiceImpl recordService = mock(RecordServiceImpl.class);
        when(recordService.getMaxGameId()).thenReturn(maxGameid);
        maxGameid+=1;
        SoloPVPExecutor soloPVPExecutor = new SoloPVPExecutor(playersMatchingGamemode, recordService);

        Field privateField = SoloPVPExecutor.class.getDeclaredField("playersScoreGainAndMatchStartTime");
        privateField.setAccessible(true);
        Map<Player,Object[]> playersScoreGainAndMatchStartTime = spy((Map<Player, Object[]>) privateField.get(soloPVPExecutor));
        Object[] loserArray = new Object[4];
        loserArray[0] = 0;
        loserArray[1] = System.currentTimeMillis();
        loserArray[2] = 0.0;
        loserArray[3] = 0.0;
        playersScoreGainAndMatchStartTime.put(loser, loserArray);
        Object[] winnerArray = new Object[4];
        winnerArray[0] = 0;
        winnerArray[1] = System.currentTimeMillis();
        winnerArray[2] = 0.0;
        winnerArray[3] = 0.0;
        playersScoreGainAndMatchStartTime.put(winner, winnerArray);
        privateField.set(soloPVPExecutor, playersScoreGainAndMatchStartTime);

        privateField = SoloPVPExecutor.class.getDeclaredField("playersInSoloPVP");
        privateField.setAccessible(true);
        Map<Player, Player> playersInSoloPVP = spy((Map<Player, Player>) privateField.get(soloPVPExecutor));
        playersInSoloPVP.put(winner, loser);
        playersInSoloPVP.put(loser, winner);
        privateField.set(soloPVPExecutor, playersInSoloPVP);


        //执行三次，也就是2:1
        try{
            soloPVPExecutor.oneMatchOver(event);
        }catch (Exception e){}
        event=new PlayerDeathEvent(winner,new ArrayList<>(),0, null);
        try{
            soloPVPExecutor.oneMatchOver(event);
        }catch (Exception e){}
        event=new PlayerDeathEvent(loser,new ArrayList<>(),0, null);
        try{
            soloPVPExecutor.oneMatchOver(event);
        }catch (Exception e){}

        verify(recordService,times(3)).getMaxGameId();
        //Assert that the winner's score is 20 and the loser's score is -5
        assertEquals(20,winnerArray[0]);
        assertEquals (-5,loserArray[0]);
    }
    @Test
    public void figureDamageTest(){
        Player damager=mock(Player.class);
        when(damager.getName()).thenReturn("damager");
        Player damagee=mock(Player.class);
        when(damagee.getName()).thenReturn("damagee");
        EntityDamageByEntityEvent event=new EntityDamageByEntityEvent(damager,damagee, EntityDamageByEntityEvent.DamageCause.ENTITY_ATTACK,2.0);

        Map<Player, Integer> playersMatchingGamemode = new ConcurrentHashMap<>();
        RecordServiceImpl recordService = mock(RecordServiceImpl.class);
        when(recordService.getMaxGameId()).thenReturn(maxGameid);
        maxGameid+=1;
        SoloPVPExecutor soloPVPExecutor = new SoloPVPExecutor(playersMatchingGamemode, recordService);

        Map<Player, Player> playersInSoloPVP = spy(soloPVPExecutor.playersInSoloPVP);
        playersInSoloPVP.put(damagee,damager);
        playersInSoloPVP.put(damager,damagee);
        soloPVPExecutor.playersInSoloPVP=playersInSoloPVP;

        Map<Player, Object[]> playersScoreGainAndMatchStartTime = spy(soloPVPExecutor.playersScoreGainAndMatchStartTime);
        Object[] damagerArray = new Object[4];
        damagerArray[0] = 0;
        damagerArray[1] = System.currentTimeMillis();
        damagerArray[2] = 0.0;
        damagerArray[3] = 0.0;
        playersScoreGainAndMatchStartTime.put(damager, damagerArray);
        Object[] damageeArray = new Object[4];
        damageeArray[0] = 0;
        damageeArray[1] = System.currentTimeMillis();
        damageeArray[2] = 0.0;
        damageeArray[3] = 0.0;
        playersScoreGainAndMatchStartTime.put(damagee, damageeArray);
        soloPVPExecutor.playersScoreGainAndMatchStartTime=playersScoreGainAndMatchStartTime;

        soloPVPExecutor.figureDamage(event);

        assertEquals(2, (Double) damagerArray[2],0.0001);
        assertEquals(2,((Double)damageeArray[3]),0.0001);
    }
    @Test
    public void onCommandTest_AlreadyInSoloMatching(){
        Map<Player, Integer> playersMatchingGamemode = new ConcurrentHashMap<>();
        RecordServiceImpl recordService = mock(RecordServiceImpl.class);
        when(recordService.getMaxGameId()).thenReturn(maxGameid);
        maxGameid+=1;
        SoloPVPExecutor soloPVPExecutor = new SoloPVPExecutor(playersMatchingGamemode, recordService);

        Player player=mock(Player.class);
        when(player.getName()).thenReturn("commandSender");//准备玩家
        playersMatchingGamemode.put(player,SOLOPVP_MODE);//显示正在单挑匹配中
        Command command=mock(Command.class);
        when(command.getName()).thenReturn("soloPVP");//准备命令
        when(command.getLabel()).thenReturn("solopvp");
        when(command.getUsage()).thenReturn("/<command>)");
        when(command.getPermission()).thenReturn(null);
        String s="solopvp";
        String[] strings= {};

        //开始执行
        soloPVPExecutor.onCommand(player,command,s,strings);
        verify(player).sendRawMessage("您已在单人PVP匹配中，无需再加入匹配");
    }
    @Test
    public void onCommandTest_AlreadyInOtherMatching(){
        Map<Player, Integer> playersMatchingGamemode = new ConcurrentHashMap<>();
        RecordServiceImpl recordService = mock(RecordServiceImpl.class);
        when(recordService.getMaxGameId()).thenReturn(maxGameid);
        maxGameid+=1;
        SoloPVPExecutor soloPVPExecutor = new SoloPVPExecutor(playersMatchingGamemode, recordService);

        Player player=mock(Player.class);
        when(player.getName()).thenReturn("commandSender");//准备玩家
        playersMatchingGamemode.put(player,BRAWL_MODE);//显示正在单挑匹配中
        Command command=mock(Command.class);
        when(command.getName()).thenReturn("soloPVP");//准备命令
        when(command.getLabel()).thenReturn("solopvp");
        when(command.getUsage()).thenReturn("/<command>)");
        when(command.getPermission()).thenReturn(null);
        String s="solopvp";
        String[] strings= {};

        //开始执行
        soloPVPExecutor.onCommand(player,command,s,strings);
        verify(player).sendRawMessage("您正在匹配其他游戏，请先退出");
    }
    @Test
    public void onCommandTest_JoinMatchingAndStartGame(){
        Map<Player, Integer> playersMatchingGamemode = new ConcurrentHashMap<>();
        RecordServiceImpl recordService = mock(RecordServiceImpl.class);
        //假设所有人、所有游戏都是600分，白银2
        when(recordService.getScoreTotal(anyString(), anyInt())).thenReturn(600);
        //假设游戏id是maxGameid
        when(recordService.getMaxGameId()).thenReturn(maxGameid);
        maxGameid+=1;
        SoloPVPExecutor soloPVPExecutor = new SoloPVPExecutor(playersMatchingGamemode, recordService);

        Player player=mock(Player.class);
        when(player.getName()).thenReturn("commandSender");//准备玩家
        Player matchingPlayer=mock(Player.class);
        when(matchingPlayer.getName()).thenReturn("matchingPlayer");//准备玩家
        Command command=mock(Command.class);
        when(command.getName()).thenReturn("soloPVP");//准备命令
        when(command.getLabel()).thenReturn("solopvp");
        when(command.getUsage()).thenReturn("/<command>)");
        when(command.getPermission()).thenReturn(null);
        String s="solopvp";
        String[] strings= {};
        Map<Integer, Player> matchingPlayers = spy(soloPVPExecutor.matchingPlayers);
        //有matchingPlayer在匹配中
        matchingPlayers.put(LevelUtil.getLevel(recordService.getScoreTotal(matchingPlayer.getName(),SOLOPVP_MODE)),matchingPlayer);
        soloPVPExecutor.matchingPlayers=matchingPlayers;


        //开始执行
        soloPVPExecutor.onCommand(player,command,s,strings);
        //验证
        assertNull(playersMatchingGamemode.get(player));
        verify(player).sendRawMessage("您已匹配对手"+matchingPlayer.getName()+",对局开始");
    }
}
