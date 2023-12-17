package com.kary.karyplugin.gamemodeExecutors;

import com.kary.karyplugin.KaryPlugin;
import com.kary.karyplugin.service.RecordService;
import com.kary.karyplugin.service.impl.RecordServiceImpl;
import com.kary.karyplugin.utils.LevelUtil;
import org.bukkit.Bukkit;
import org.bukkit.Server;
import org.bukkit.command.Command;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitScheduler;
import org.junit.Before;
import org.junit.Test;

import java.lang.reflect.Field;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentSkipListSet;
import java.util.concurrent.CopyOnWriteArraySet;

import static com.kary.karyplugin.gamemodeExecutors.BrawlExecutor.MAX_MATCH_NUM;
import static com.kary.karyplugin.utils.GameModeUtil.BRAWL_MODE;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.*;

public class Brawl_OnCommand {
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
    public void onCommandTest_JoinMatchButNotStart(){
        Player commandSender=mock(Player.class);
        when(commandSender.getName()).thenReturn("commandSender");
        int level= LevelUtil.getLevel(recordService.getScoreTotal(commandSender.getName(),gameMode));
        Map<Player, Integer> playersMatchingGamemode = spy(brawlExecutor.playersMatchingGamemode);
        brawlExecutor.playersMatchingGamemode=playersMatchingGamemode;//监测匹配列表

        Map<Integer, Set<Player>> matchingPlayers = spy(brawlExecutor.matchingPlayers);
        matchingPlayers.put(LevelUtil.COPPER, mock(CopyOnWriteArraySet.class));
        matchingPlayers.put(LevelUtil.SILVER, mock(CopyOnWriteArraySet.class));
        matchingPlayers.put(LevelUtil.GOLD, mock(CopyOnWriteArraySet.class));
        matchingPlayers.put(LevelUtil.PLATINUM, mock(CopyOnWriteArraySet.class));
        matchingPlayers.put(LevelUtil.DIAMOND,mock(CopyOnWriteArraySet.class));
        matchingPlayers.put(LevelUtil.MASTER, mock(CopyOnWriteArraySet.class));
        matchingPlayers.put(LevelUtil.KING, mock(CopyOnWriteArraySet.class));
        brawlExecutor.matchingPlayers=matchingPlayers;//监测匹配列表


        brawlExecutor.onCommand(commandSender,command,s,strings);
        verify(playersMatchingGamemode).put(commandSender,BRAWL_MODE);
        Set<Player> matchingPlayer=matchingPlayers.get(level);
        verify(matchingPlayer).add(commandSender);
        assertTrue(matchingPlayer.size()<MAX_MATCH_NUM);
    }
    @Test
    public void onCommandTest_JoinMatchAndStart(){
        Player commandSender=mock(Player.class);
        when(commandSender.getName()).thenReturn("commandSender");
        Player player1=mock(Player.class);
        when(player1.getName()).thenReturn("player1");
        Player player2=mock(Player.class);
        when(player2.getName()).thenReturn("player2");

        int level= LevelUtil.getLevel(recordService.getScoreTotal(commandSender.getName(),gameMode));
        Map<Player, Integer> playersMatchingGamemode = spy(brawlExecutor.playersMatchingGamemode);
        brawlExecutor.playersMatchingGamemode=playersMatchingGamemode;//监测匹配列表

        brawlExecutor.matchingPlayers.get(level).add(player1);
        brawlExecutor.matchingPlayers.get(level).add(player2);
        //准备用于监测的匹配列表

        Map<Player, ConcurrentSkipListSet<BrawlExecutor.PlayerAndTime>> assistMap = spy(brawlExecutor.assistMap);
        brawlExecutor.assistMap=assistMap;

        brawlExecutor.onCommand(commandSender, command, s, strings);

        //检测commandSender是否成功匹配
        verify(playersMatchingGamemode).put(commandSender,BRAWL_MODE);
        Set<Player> matchingPlayer=brawlExecutor.matchingPlayers.get(level);
        if(!matchingPlayer.isEmpty()){
            throw new RuntimeException("匹配列表没有完全清空");
        }

        //检测是否成功加入助攻列表
        assertNotNull(assistMap.get(commandSender));
        assertNotNull(assistMap.get(player1));
        assertNotNull(assistMap.get(player2));

    }
}
