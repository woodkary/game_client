package com.kary.karyplugin.gamemodeExecutors;

import com.kary.karyplugin.KaryPlugin;
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
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentSkipListSet;

import static com.kary.karyplugin.utils.GameModeUtil.BRAWL_MODE;
import static com.kary.karyplugin.utils.GameModeUtil.SOLOPVP_MODE;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

/**
 * @author:123
 */
public class Solo_Command {
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
    public void onCommandTest_AlreadyInSoloMatching(){
        Map<Player, Integer> playersMatchingGamemode = new ConcurrentHashMap<>();
        RecordServiceImpl recordService = mock(RecordServiceImpl.class);
        when(recordService.getMaxGameId()).thenReturn(maxGameid);
        maxGameid+=1;
        SoloPVPExecutor soloPVPExecutor = new SoloPVPExecutor(plugin,playersMatchingGamemode, recordService);

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
        SoloPVPExecutor soloPVPExecutor = new SoloPVPExecutor(plugin,playersMatchingGamemode, recordService);

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
    public void onCommandTest_JoinMatchingAndStartGame() throws NoSuchFieldException, IllegalAccessException {
        Map<Player, Integer> playersMatchingGamemode = new ConcurrentHashMap<>();
        RecordServiceImpl recordService = mock(RecordServiceImpl.class);
        //假设所有人、所有游戏都是600分，白银2
        when(recordService.getScoreTotal(anyString(), anyInt())).thenReturn(600);
        //假设游戏id是maxGameid
        when(recordService.getMaxGameId()).thenReturn(maxGameid);
        maxGameid+=1;
        SoloPVPExecutor soloPVPExecutor = new SoloPVPExecutor(plugin,playersMatchingGamemode, recordService);

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

        Map<Player, Object[]> playersScoreGainAndMatchStartTime = spy(soloPVPExecutor.playersScoreGainAndMatchStartTime);
        soloPVPExecutor.playersScoreGainAndMatchStartTime=playersScoreGainAndMatchStartTime;

        Map<Player, Player> playersInSoloPVP = spy(soloPVPExecutor.playersInSoloPVP);
        soloPVPExecutor.playersInSoloPVP=playersInSoloPVP;

        ConcurrentSkipListSet<double[]> warFieldPosition=soloPVPExecutor.warFieldPosition;
        //开始执行
        soloPVPExecutor.onCommand(player,command,s,strings);
        //验证是否已经去除了一个位置
        assertEquals(warFieldPosition.size(),5);
    }
}
