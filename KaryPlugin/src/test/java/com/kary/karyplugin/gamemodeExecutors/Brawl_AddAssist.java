package com.kary.karyplugin.gamemodeExecutors;

import com.kary.karyplugin.KaryPlugin;
import com.kary.karyplugin.pojo.Record;
import com.kary.karyplugin.service.RecordService;
import com.kary.karyplugin.service.impl.RecordServiceImpl;
import org.bukkit.Bukkit;
import org.bukkit.Server;
import org.bukkit.command.Command;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.scheduler.BukkitScheduler;
import org.junit.Before;
import org.junit.Test;

import java.lang.reflect.Field;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentSkipListSet;

import static com.kary.karyplugin.utils.GameModeUtil.BRAWL_MODE;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.spy;

/**
 * @author:123
 */
public class Brawl_AddAssist {
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
}
