package com.kary.karyplugin.gamemodeExecutors;

import com.kary.karyplugin.KaryPlugin;
import com.kary.karyplugin.pojo.Record;
import com.kary.karyplugin.service.RecordService;
import com.kary.karyplugin.service.impl.RecordServiceImpl;
import com.kary.karyplugin.utils.GameModeUtil;
import com.kary.karyplugin.utils.LevelUtil;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.junit.Before;
import org.junit.Test;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentSkipListSet;

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
        assertEquals(players.get(player).getScoreGain(),-2);
        assertEquals(times[1].longValue(), 2);

    }
    @Test
    public void addAssistTest(){
        Player damager = mock(Player.class);
        when(damager.getName()).thenReturn("kary1");
        Player damagee = mock(Player.class);
        when(damagee.getName()).thenReturn("kary2");
        //攻击伤害为2
        EntityDamageByEntityEvent event=new EntityDamageByEntityEvent(damager,damagee, EntityDamageByEntityEvent.DamageCause.ENTITY_ATTACK,2);

        Map<Player, ConcurrentSkipListSet<BrawlExecutor.PlayerAndTime>> assistMap = brawlExecutor.assistMap;
        assistMap=spy(assistMap);
        //放入被伤害者的助攻列表
        assistMap.put(damagee,new ConcurrentSkipListSet<>());
        brawlExecutor.assistMap=assistMap;//监测助攻列表
        ConcurrentSkipListSet<BrawlExecutor.PlayerAndTime> danagerList=brawlExecutor.assistMap.get(damagee);
        /*ConcurrentSkipListSet<BrawlExecutor.PlayerAndTime> danagerListMock=spy(danagerList);//监测助攻列表中的玩家*/
        assistMap.put(damagee,danagerList);//在助攻列表中加入被攻击者的助攻列表

        Map<Player, Record> players = spy(brawlExecutor.players);
        players.put(damager, new Record());
        players.put(damagee, new Record());
        brawlExecutor.players=players;//放入监测记录的数组

        BrawlExecutor.PlayerAndTime temp=new BrawlExecutor.PlayerAndTime(damager,System.currentTimeMillis());
        assertFalse(danagerList.contains(temp));//初始不含有PlayerAndTime
        brawlExecutor.addAssist(event);
        assertTrue(danagerList.contains(temp));//攻击之后含有攻击者对应的PlayerAndTime

        Record damagerRecord=players.get(damager);
        Record damageeRecord=players.get(damagee);
        //检验两者的伤害值
        assertEquals(damagerRecord.getTakeDamage(),2,0.0001);
        assertEquals(damageeRecord.getTakenDamage(),2,0.0001);
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
        assertEquals(assistRecord1.getAssist(),1);
        assertEquals(assistRecord1.getScoreGain(),5);//助攻一人，加五分
        Record assistRecord2 = players.get(assistPlayer2);
        assertEquals(assistRecord2.getAssist(),1);
        assertEquals(assistRecord2.getScoreGain(),5);

        assertEquals(deadAssists.size(),0);//助攻列表应该被清空
    }
}
