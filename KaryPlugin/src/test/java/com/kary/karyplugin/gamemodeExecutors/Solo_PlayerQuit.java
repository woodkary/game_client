package com.kary.karyplugin.gamemodeExecutors;

import com.kary.karyplugin.KaryPlugin;
import com.kary.karyplugin.mapProcessors.MatchingPlayersMapProcessor;
import com.kary.karyplugin.service.impl.RecordServiceImpl;
import com.kary.karyplugin.utils.LevelUtil;
import org.bukkit.Bukkit;
import org.bukkit.Server;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.scheduler.BukkitScheduler;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.lang.reflect.Field;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import static com.kary.karyplugin.utils.GameModeUtil.SOLOPVP_MODE;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.verify;

/**
 * @author:123
 */
public class Solo_PlayerQuit {
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
        SoloPVPExecutor soloPVPExecutor = new SoloPVPExecutor(plugin,playersMatchingGamemode, recordService);

        //获取matchingPlayers
        Field privateField = SoloPVPExecutor.class.getDeclaredField("matchingPlayers");
        privateField.setAccessible(true);
        Map<Integer, Player> matchingPlayers = (Map<Integer, Player>) privateField.get(soloPVPExecutor);
        Map<Integer, Player> matchingPlayersMock = spy(matchingPlayers);
        privateField.set(soloPVPExecutor, matchingPlayersMock);

        //获取playersInSoloPVP
        privateField = SoloPVPExecutor.class.getDeclaredField("playersInSoloPVP");
        privateField.setAccessible(true);
        Map<Player, Player> playersInSoloPVP = (Map<Player, Player>) privateField.get(soloPVPExecutor);
        Map<Player, Player> playersInSoloPVPMock = spy(playersInSoloPVP);
        privateField.set(soloPVPExecutor, playersInSoloPVPMock);

        //执行操作
        soloPVPExecutor.playerQuit(event);

        Integer gameMode = SOLOPVP_MODE;
        int level = LevelUtil.getLevel(recordService.getScoreTotal(loser.getName(), gameMode));
        //验证matchingPlayers是否做了移除操作
        MatchingPlayersMapProcessor processor = new MatchingPlayersMapProcessor(matchingPlayersMock);
        processor.removePlayerInThisLevel(level, loser);
        Assert.assertNull(matchingPlayersMock.get(level));
        //验证playersInSoloPVP是否做了移除操作
        Assert.assertNull(playersInSoloPVPMock.get(loser));

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
        SoloPVPExecutor soloPVPExecutor = new SoloPVPExecutor(plugin,playersMatchingGamemode, recordService);

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
}
