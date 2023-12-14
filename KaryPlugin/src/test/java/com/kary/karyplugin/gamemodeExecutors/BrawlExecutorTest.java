package com.kary.karyplugin.gamemodeExecutors;

import com.kary.karyplugin.KaryPlugin;
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

import static org.junit.Assert.assertNull;
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
        Map<Player, Long[]> playerDuration = (Map<Player, Long[]>) spy(privateField.get(brawlExecutor));

        privateField = BrawlExecutor.class.getDeclaredField("matchingPlayers");
        privateField.setAccessible(true);
        Map<Integer, Set<Player>> matchingPlayers = (Map<Integer,Set<Player>>) spy(privateField.get(brawlExecutor));

        brawlExecutor.playerQuit(event);

        Long[] times = playerDuration.get(player);
        assertNull(times);//player没有参加游戏，就没有times  vvv

        int level= LevelUtil.getLevel(recordService.getScoreTotal(player.getName(),gameMode));
        Set<Player> matchingPlayer = matchingPlayers.get(level);
        verify(matchingPlayer).remove(player);


    }
}
