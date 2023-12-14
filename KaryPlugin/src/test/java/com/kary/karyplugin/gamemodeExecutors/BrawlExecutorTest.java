package com.kary.karyplugin.gamemodeExecutors;

import com.kary.karyplugin.KaryPlugin;
import com.kary.karyplugin.pojo.Record;
import com.kary.karyplugin.service.RecordService;
import com.kary.karyplugin.service.impl.RecordServiceImpl;
import com.kary.karyplugin.utils.GameModeUtil;
import com.kary.karyplugin.utils.LevelUtil;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerQuitEvent;
import org.junit.Before;
import org.junit.Test;

import java.lang.reflect.Field;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import static org.junit.Assert.*;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.mockito.Mockito.*;

public class BrawlExecutorTest {
    private Integer maxGameid=2;
    private RecordService recordService;

    private Map<Player, Integer> playersMatchingGamemode;

    private KaryPlugin plugin;

    private BrawlExecutor brawlExecutor;
    private Integer gameMode= GameModeUtil.BRAWL_MODE;

    @Before
    public void setup() {
        playersMatchingGamemode = new ConcurrentHashMap<>();
        recordService = mock(RecordServiceImpl.class);
        //假设所有人、所有游戏都是600分，白银2
        when(recordService.getScoreTotal(anyString(), anyInt())).thenReturn(600);
        //假设游戏id是maxGameid
        when(recordService.getMaxGameId()).thenReturn(maxGameid);
        maxGameid+=1;
        plugin=mock(KaryPlugin.class);
        doNothing().when(plugin).onDisable();
        doNothing().when(plugin).onEnable();

        brawlExecutor = new BrawlExecutor(recordService, playersMatchingGamemode, plugin);
    }

    @Test
    public void playerNotInMatchQuitTest() throws NoSuchFieldException, IllegalAccessException {
        Player player = mock(Player.class);
        when(player.getName()).thenReturn("kary");
        PlayerQuitEvent event = new PlayerQuitEvent(player, "kary退出了游戏");

        Field privateField = BrawlExecutor.class.getDeclaredField("playerDuration");
        privateField.setAccessible(true);
        Map<Player, Long[]> playerDuration = spy((Map<Player, Long[]>)privateField.get(brawlExecutor));

        Map<Integer, Set<Player>> matchingPlayers = brawlExecutor.matchingPlayers;

        brawlExecutor.playerQuit(event);

        Long[] times = playerDuration.get(player);
        assertNull(times);//player没有参加游戏，就没有times  vvv

        int level= LevelUtil.getLevel(recordService.getScoreTotal(player.getName(),gameMode));
        Set<Player> matchingPlayer = matchingPlayers.get(level);
        assertFalse(matchingPlayer.remove(player));
        assertTrue(matchingPlayer.remove(player));


    }
    @Test
    public void playerInMatchQuitTest() throws NoSuchFieldException, IllegalAccessException {
        Player player = mock(Player.class);
        when(player.getName()).thenReturn("kary");
        PlayerQuitEvent event = new PlayerQuitEvent(player, "kary退出了游戏");

        Player player1 = mock(Player.class);
        when(player.getName()).thenReturn("kary1");
        Player player2 = mock(Player.class);
        when(player.getName()).thenReturn("kary2");

        Map<Player, Long[]> playerDuration = brawlExecutor.playerDuration;
        playerDuration.put(player,new Long[]{1L,2L});
        playerDuration.put(player1,new Long[]{3L,4L});
        playerDuration.put(player2,new Long[]{5L,6L});

        Map<Player, Record> players = spy(brawlExecutor.players);
        players.put(player, new Record());
        brawlExecutor.players=players;

        brawlExecutor.playerQuit(event);

        Long[] times = playerDuration.get(player);
        assertEquals(times[0].longValue(), 1);
        assertNotEquals(times[1].longValue(), 2);
        verify(players).get(player);
        assertEquals(players.get(player).getScoreGain(),-2);
        assertEquals(times[1].longValue(), 2);

    }
}
