package com.kary.karyplugin.gamemodeExecutors;

import com.kary.karyplugin.KaryPlugin;
import com.kary.karyplugin.pojo.Record;
import com.kary.karyplugin.service.RecordService;
import com.kary.karyplugin.service.impl.RecordServiceImpl;
import org.bukkit.Bukkit;
import org.bukkit.Server;
import org.bukkit.command.Command;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.scheduler.BukkitScheduler;
import org.junit.Before;
import org.junit.Test;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentSkipListSet;

import static com.kary.karyplugin.utils.GameModeUtil.BRAWL_MODE;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.spy;

/**
 * @author:123
 */
public class Brawl_PlayerKill {
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
    public void playerKillTest(){
        Player killer = mock(Player.class);
        Player deadPlayer = mock(Player.class);
        Player assistPlayer1 = mock(Player.class);
        Player assistPlayer2 = mock(Player.class);
        Player assistPlayer3 = mock(Player.class);

        when(killer.getName()).thenReturn("killer");
        when(killer.getUniqueId()).thenReturn(UUID.fromString("aca624b8-3f21-4203-b7b5-2fb6bc19b949"));
        when(deadPlayer.getName()).thenReturn("deadPlayer");
        when(deadPlayer.getKiller()).thenReturn(killer);
        when(deadPlayer.getUniqueId()).thenReturn(UUID.randomUUID());
        when(assistPlayer1.getName()).thenReturn("assistPlayer1");
        when(assistPlayer2.getName()).thenReturn("assistPlayer2");
        when(assistPlayer3.getName()).thenReturn("assistPlayer3");
        when(assistPlayer1.getUniqueId()).thenReturn(UUID.fromString("22e3c337-84f2-4bcd-a33d-7c64e0c22654"));
        when(assistPlayer2.getUniqueId()).thenReturn(UUID.fromString("f79c3201-72ba-4560-95b1-d956b087893e"));
        when(assistPlayer3.getUniqueId()).thenReturn(UUID.fromString("613949e7-814a-4140-a844-11cbc363abca"));

        Map<Player, Record> players = spy(brawlExecutor.players);
        players.put(deadPlayer, new Record());
        players.put(killer, new Record());
        players.put(assistPlayer1, new Record());
        players.put(assistPlayer2, new Record());
        players.put(assistPlayer3, new Record());
        brawlExecutor.players=players;//放入监测记录的数组

        Map<Player, ConcurrentSkipListSet<BrawlExecutor.PlayerAndTime>> assistMap = spy(brawlExecutor.assistMap);
        ConcurrentSkipListSet<BrawlExecutor.PlayerAndTime> deadAssists = new ConcurrentSkipListSet<>();
        deadAssists.add(new BrawlExecutor.PlayerAndTime(assistPlayer1,System.currentTimeMillis()));
        deadAssists.add(new BrawlExecutor.PlayerAndTime(assistPlayer2,System.currentTimeMillis()));
        deadAssists.add(new BrawlExecutor.PlayerAndTime(assistPlayer3,System.currentTimeMillis()));
        assistMap.put(deadPlayer,deadAssists);//放入被杀死者的助攻列表，假设有三个人
        brawlExecutor.assistMap=assistMap;//监测助攻列表

        brawlExecutor.playerKill(new PlayerDeathEvent(deadPlayer,new ArrayList<>(),3,"killer 杀死了 deadPlayer"));//杀死了deadPlayer

        Record deadRecord = players.get(deadPlayer);
        assertEquals(deadRecord.getDeath(),1);
        assertEquals(deadRecord.getScoreGain(),-4);//被杀死者扣四分

        Record killerRecord = players.get(killer);
        assertEquals(killerRecord.getKill(),1);
        assertEquals(killerRecord.getScoreGain(),10);//杀一人，加十分

        Record assistRecord1 = players.get(assistPlayer1);
        assertEquals(assistRecord1.getAssist(),1);
        assertEquals(assistRecord1.getScoreGain(),5);//助攻一人，加五分
        Record assistRecord2 = players.get(assistPlayer2);
        assertEquals(assistRecord2.getAssist(),1);
        assertEquals(assistRecord2.getScoreGain(),5);
        Record assistRecord3 = players.get(assistPlayer3);
        assertEquals(assistRecord3.getAssist(),1);
        assertEquals(assistRecord3.getScoreGain(),5);

        assertEquals(deadAssists.size(),0);//助攻列表应该被清空

    }
    @Test
    public void playerKillTest_OneOfThemIsKiller(){
        Player killer = mock(Player.class);
        Player deadPlayer = mock(Player.class);
        Player assistPlayer1 = mock(Player.class);
        Player assistPlayer2 = mock(Player.class);

        when(killer.getName()).thenReturn("killer");
        when(killer.getUniqueId()).thenReturn(UUID.fromString("aca624b8-3f21-4203-b7b5-2fb6bc19b949"));
        when(deadPlayer.getName()).thenReturn("deadPlayer");
        when(deadPlayer.getKiller()).thenReturn(killer);
        when(deadPlayer.getUniqueId()).thenReturn(UUID.randomUUID());
        when(assistPlayer1.getName()).thenReturn("assistPlayer1");
        when(assistPlayer2.getName()).thenReturn("assistPlayer2");
        when(assistPlayer1.getUniqueId()).thenReturn(UUID.fromString("22e3c337-84f2-4bcd-a33d-7c64e0c22654"));
        when(assistPlayer2.getUniqueId()).thenReturn(UUID.fromString("f79c3201-72ba-4560-95b1-d956b087893e"));

        Map<Player, Record> players = spy(brawlExecutor.players);
        players.put(deadPlayer, new Record());
        players.put(killer, new Record());
        players.put(assistPlayer1, new Record());
        players.put(assistPlayer2, new Record());
        brawlExecutor.players=players;//放入监测记录的数组

        Map<Player, ConcurrentSkipListSet<BrawlExecutor.PlayerAndTime>> assistMap = spy(brawlExecutor.assistMap);
        ConcurrentSkipListSet<BrawlExecutor.PlayerAndTime> deadAssists = new ConcurrentSkipListSet<>();
        deadAssists.add(new BrawlExecutor.PlayerAndTime(assistPlayer1,System.currentTimeMillis()));
        deadAssists.add(new BrawlExecutor.PlayerAndTime(assistPlayer2,System.currentTimeMillis()));
        deadAssists.add(new BrawlExecutor.PlayerAndTime(killer,System.currentTimeMillis()));
        assistMap.put(deadPlayer,deadAssists);//放入被杀死者的助攻列表，假设有三个人
        brawlExecutor.assistMap=assistMap;//监测助攻列表

        brawlExecutor.playerKill(new PlayerDeathEvent(deadPlayer,new ArrayList<>(),3,"killer 杀死了 deadPlayer"));//杀死了deadPlayer

        Record deadRecord = players.get(deadPlayer);
        assertEquals(deadRecord.getDeath(),1);
        assertEquals(deadRecord.getScoreGain(),-4);//被杀死者扣四分

        Record killerRecord = players.get(killer);
        assertEquals(killerRecord.getKill(),1);
        assertEquals(killerRecord.getScoreGain(),10);//杀一人，加十分，而不是十五分
        assertEquals(killerRecord.getAssist(),0);//杀人者也是助攻者，应该不加助攻

        Record assistRecord1 = players.get(assistPlayer1);
        assertEquals(assistRecord1.getAssist(),1);//助攻人数加一
        assertEquals(assistRecord1.getScoreGain(),5);//助攻一人，加五分
        Record assistRecord2 = players.get(assistPlayer2);
        assertEquals(assistRecord2.getAssist(),1);
        assertEquals(assistRecord2.getScoreGain(),5);

        assertEquals(deadAssists.size(),0);//助攻列表应该被清空
    }
}
