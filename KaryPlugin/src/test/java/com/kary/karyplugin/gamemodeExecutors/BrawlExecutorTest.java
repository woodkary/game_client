package com.kary.karyplugin.gamemodeExecutors;

import com.kary.karyplugin.KaryPlugin;
import com.kary.karyplugin.pojo.Record;
import com.kary.karyplugin.service.RecordService;
import com.kary.karyplugin.service.impl.RecordServiceImpl;
import com.kary.karyplugin.utils.GameModeUtil;
import com.kary.karyplugin.utils.LevelUtil;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.junit.Before;
import org.junit.Test;

import java.lang.reflect.Field;
import java.util.Map;
import java.util.Set;
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
        assistMap.put(damager,new ConcurrentSkipListSet<>());
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
}
