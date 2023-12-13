package com.kary.karyplugin.gamemodeExecutors;

import com.kary.karyplugin.mapProcessors.MatchingPlayersMapProcessor;
import com.kary.karyplugin.mapProcessors.PlayersInSoloPVPProcessor;
import com.kary.karyplugin.service.impl.RecordServiceImpl;
import com.kary.karyplugin.utils.GameModeUtil;
import com.kary.karyplugin.utils.LevelUtil;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerQuitEvent;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;

import java.lang.reflect.Field;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

/**
 * @author:123
 */
public class SoloPVPExecutorTest {
    //测试玩家在匹配中退出游戏
    @Test
    public void soloPVPQuitTest_NotInMatch() throws NoSuchFieldException, IllegalAccessException {
        Player loser=mock(Player.class);
        when(loser.getName()).thenReturn("kary");
        PlayerQuitEvent event=new PlayerQuitEvent(loser,"kary退出了游戏");

        Map<Player,Integer> playersMatchingGamemode=new ConcurrentHashMap<>();
        RecordServiceImpl recordService=new RecordServiceImpl();
        SoloPVPExecutor soloPVPExecutor=new SoloPVPExecutor(playersMatchingGamemode,recordService);

        //执行操作
        soloPVPExecutor.playerQuit(event);

        //获取matchingPlayers
        Field privateField = SoloPVPExecutor.class.getDeclaredField("matchingPlayers");
        privateField.setAccessible(true);
        Map<Integer,Player> matchingPlayers=(Map<Integer,Player>)privateField.get(soloPVPExecutor);
        Map<Integer,Player> matchingPlayersMock=spy(matchingPlayers);

        Integer gameMode= GameModeUtil.SOLOPVP_MODE;
        int level= LevelUtil.getLevel(recordService.getScoreTotal(loser.getName(),gameMode));
        //验证matchingPlayers是否做了移除操作
        MatchingPlayersMapProcessor processor=new MatchingPlayersMapProcessor(matchingPlayersMock);
        processor.removePlayerInThisLevel(level,loser);
        verify(matchingPlayersMock).compute(eq(level),any());

        //获取playersInSoloPVP
        privateField = SoloPVPExecutor.class.getDeclaredField("playersInSoloPVP");
        privateField.setAccessible(true);
        Map<Player,Player> playersInSoloPVP=(Map<Player,Player>)privateField.get(soloPVPExecutor);
        Map<Player,Player> playersInSoloPVPMock=spy(playersInSoloPVP);
        //验证playersInSoloPVP是否做了移除操作
        /*ArgumentCaptor<Player> keyCaptor = ArgumentCaptor.forClass(Player.class);*/

        PlayersInSoloPVPProcessor processor1 = new PlayersInSoloPVPProcessor(playersInSoloPVPMock);
        Player winner=processor1.removePlayer(loser);
        verify(playersInSoloPVPMock).remove(loser);
        Assertions.assertNull(winner);//loser没有参加比赛时，winner应该为null
    }
    //测试玩家在比赛中退出游戏
    @Test
    public void soloPVPQuitTest_InMatch() throws NoSuchFieldException, IllegalAccessException {
        Player loser=mock(Player.class);
        when(loser.getName()).thenReturn("kary");
        PlayerQuitEvent event=new PlayerQuitEvent(loser,"kary退出了游戏");

        Map<Player,Integer> playersMatchingGamemode=new ConcurrentHashMap<>();
        RecordServiceImpl recordService=new RecordServiceImpl();
        SoloPVPExecutor soloPVPExecutor=new SoloPVPExecutor(playersMatchingGamemode,recordService);

        // 获取playersInSoloPVP并添加模拟玩家
        Field privateField = SoloPVPExecutor.class.getDeclaredField("playersInSoloPVP");
        privateField.setAccessible(true);
        Map<Player,Player> playersInSoloPVP=spy((Map<Player,Player>)privateField.get(soloPVPExecutor));
        Player winner = mock(Player.class);
        when(winner.getName()).thenReturn("kary1");
        playersInSoloPVP.put(winner, loser);
        playersInSoloPVP.put(loser, winner);

        //模拟放入记录数组
        privateField = SoloPVPExecutor.class.getDeclaredField("playersScoreGainAndMatchStartTime");
        privateField.setAccessible(true);
        Map<Player,Object[]> playersScoreGainAndMatchStartTime=spy((Map<Player,Object[]>)privateField.get(soloPVPExecutor));
        Object[] loserArray=new Object[4];
        loserArray[0]= 0;
        loserArray[1]= 114514L;
        loserArray[2]=0.0;
        loserArray[3]=0.0;
        playersScoreGainAndMatchStartTime.put(loser,loserArray);
        Object[] winnerArray=new Object[4];
        winnerArray[0]= 0;
        winnerArray[1]= 114514L;
        winnerArray[2]=0.0;
        winnerArray[3]=0.0;
        playersScoreGainAndMatchStartTime.put(winner,winnerArray);

        //执行操作

        try {
            soloPVPExecutor.playerQuit(event);
        }catch (Exception e){
        }
        // 验证playersInSoloPVP是否做了移除操作
        PlayersInSoloPVPProcessor processor = new PlayersInSoloPVPProcessor(playersInSoloPVP);
        Player actualWinner=processor.removePlayer(loser);
        verify(playersInSoloPVP).remove(loser);
        Assertions.assertEquals(winner,actualWinner);//loser参加比赛，移除的actualWinner应该为winner

        //验证模拟的winner是否被移除
        Assertions.assertTrue(playersInSoloPVP.containsKey(winner));
    }
}