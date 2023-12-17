package com.kary.karyplugin.gamemodeExecutors;

import com.kary.karyplugin.KaryPlugin;
import com.kary.karyplugin.service.impl.RecordServiceImpl;
import org.bukkit.Bukkit;
import org.bukkit.Server;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.scheduler.BukkitScheduler;
import org.junit.Before;
import org.junit.Test;

import java.lang.reflect.Field;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.times;

/**
 * @author:123
 */
public class Solo_Over {
    private int maxGameid=2;
    private KaryPlugin plugin;

    @Before
    public void before() throws NoSuchFieldException, IllegalAccessException {
        Field privateField = Bukkit.class.getDeclaredField("server");
        privateField.setAccessible(true);
        Server server = mock(Server.class);
        BukkitScheduler scheduler= mock(BukkitScheduler.class);
        when(server.getScheduler()).thenReturn(scheduler);
        privateField.set(null, server);
        plugin = mock(KaryPlugin.class);
        doNothing().when(plugin).onDisable();
        doNothing().when(plugin).onEnable();
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
        SoloPVPExecutor soloPVPExecutor = new SoloPVPExecutor(plugin, playersMatchingGamemode,recordService);


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
        SoloPVPExecutor soloPVPExecutor = new SoloPVPExecutor(plugin,playersMatchingGamemode, recordService);

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
        SoloPVPExecutor soloPVPExecutor = new SoloPVPExecutor(plugin,playersMatchingGamemode, recordService);

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
}
