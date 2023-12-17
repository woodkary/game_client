package com.kary.karyplugin.gamemodeExecutors;

import com.kary.karyplugin.KaryPlugin;
import com.kary.karyplugin.pojo.Record;
import com.kary.karyplugin.service.RecordService;
import com.kary.karyplugin.service.impl.RecordServiceImpl;
import com.kary.karyplugin.utils.LevelUtil;
import org.bukkit.Bukkit;
import org.bukkit.Server;
import org.bukkit.command.Command;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.scheduler.BukkitScheduler;
import org.junit.Before;
import org.junit.Test;

import java.lang.reflect.Field;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import static com.kary.karyplugin.utils.GameModeUtil.BRAWL_MODE;
import static org.junit.Assert.*;
import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

/**
 * @author:123
 */
public class Brawl_PlayerQuit {
    private Integer maxGameid=2;
    private RecordService recordService;

    private Map<Player, Integer> playersMatchingGamemode;

    private KaryPlugin plugin;

    private BrawlExecutor brawlExecutor;
    private Integer gameMode= BRAWL_MODE;
    private Command command;
    private String s;
    private String[] strings;

    @Before
    public void setup() throws NoSuchFieldException, IllegalAccessException {
        Field privateField = Bukkit.class.getDeclaredField("server");
        privateField.setAccessible(true);
        Server server = mock(Server.class);
        BukkitScheduler scheduler= mock(BukkitScheduler.class);
        when(server.getScheduler()).thenReturn(scheduler);
        privateField.set(null, server);
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

        command=mock(Command.class);
        when(command.getName()).thenReturn("brawl");//准备命令
        when(command.getLabel()).thenReturn("brawl");
        when(command.getUsage()).thenReturn("/<command>)");
        when(command.getPermission()).thenReturn(null);
        s="brawl";
        strings= new String[]{};

        brawlExecutor = new BrawlExecutor(recordService, playersMatchingGamemode, plugin);
    }

    @Test
    public void playerNotInMatchQuitTest() throws NoSuchFieldException, IllegalAccessException {
        Player player = mock(Player.class);
        when(player.getName()).thenReturn("kary");
        PlayerQuitEvent event = new PlayerQuitEvent(player, "kary退出了游戏");

        Map<Player, Long[]> playerDuration = spy(brawlExecutor.playerDuration);
        brawlExecutor.playerDuration=playerDuration;//放入监测持续时间的数组

        Map<Integer, Set<Player>> matchingPlayers = brawlExecutor.matchingPlayers;

        brawlExecutor.playerQuit(event);

        Long[] times = playerDuration.get(player);
        assertNull(times);//player没有参加游戏，就没有times  vvv

        int level= LevelUtil.getLevel(recordService.getScoreTotal(player.getName(),gameMode));
        Set<Player> matchingPlayer = matchingPlayers.get(level);
        assertFalse(matchingPlayer.remove(player));
        /*assertTrue(matchingPlayer.remove(player));*/


    }
    @Test
    public void playerInMatchQuitTest() throws NoSuchFieldException, IllegalAccessException {
        Player player = mock(Player.class);
        when(player.getName()).thenReturn("kary");
        PlayerQuitEvent event = new PlayerQuitEvent(player, "kary退出了游戏");

        Player player1 = mock(Player.class);
        when(player1.getName()).thenReturn("kary1");
        Player player2 = mock(Player.class);
        when(player2.getName()).thenReturn("kary2");

        Map<Player, Long[]> playerDuration = spy(brawlExecutor.playerDuration);
        playerDuration.put(player,new Long[]{1L,2L});
        playerDuration.put(player1,new Long[]{3L,4L});
        playerDuration.put(player2,new Long[]{5L,6L});
        brawlExecutor.playerDuration=playerDuration;//放入监测持续时间的数组

        Map<Player, Record> players = spy(brawlExecutor.players);
        players.put(player, new Record());
        players.put(player1, new Record());
        players.put(player2, new Record());
        brawlExecutor.players=players;//放入监测记录的数组
        //测试开始
        brawlExecutor.playerQuit(event);

        Long[] times = playerDuration.get(player);
        assertEquals(times[0].longValue(), 1);
        assertNotEquals(times[1].longValue(), 2);
        verify(players).get(player);
        assertEquals(players.get(player).getScoreGain(),-15);
        assertEquals(times[1].longValue(), 2);

    }
}
