package com.kary.karyplugin.gamemodeExecutors;

import com.kary.karyplugin.KaryPlugin;
import com.kary.karyplugin.service.impl.RecordServiceImpl;
import org.bukkit.Bukkit;
import org.bukkit.Server;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.scheduler.BukkitScheduler;
import org.junit.Before;
import org.junit.Test;

import java.lang.reflect.Field;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.spy;

/**
 * @author:123
 */
public class Solo_Damage {
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
    public void figureDamageTest(){
        Player damager=mock(Player.class);
        when(damager.getName()).thenReturn("damager");
        Player damagee=mock(Player.class);
        when(damagee.getName()).thenReturn("damagee");
        EntityDamageByEntityEvent event=new EntityDamageByEntityEvent(damager,damagee, EntityDamageByEntityEvent.DamageCause.ENTITY_ATTACK,2.0);

        Map<Player, Integer> playersMatchingGamemode = new ConcurrentHashMap<>();
        RecordServiceImpl recordService = mock(RecordServiceImpl.class);
        when(recordService.getMaxGameId()).thenReturn(maxGameid);
        maxGameid+=1;
        SoloPVPExecutor soloPVPExecutor = new SoloPVPExecutor(plugin,playersMatchingGamemode, recordService);

        Map<Player, Player> playersInSoloPVP = spy(soloPVPExecutor.playersInSoloPVP);
        playersInSoloPVP.put(damagee,damager);
        playersInSoloPVP.put(damager,damagee);
        soloPVPExecutor.playersInSoloPVP=playersInSoloPVP;

        Map<Player, Object[]> playersScoreGainAndMatchStartTime = spy(soloPVPExecutor.playersScoreGainAndMatchStartTime);
        Object[] damagerArray = new Object[4];
        damagerArray[0] = 0;
        damagerArray[1] = System.currentTimeMillis();
        damagerArray[2] = 0.0;
        damagerArray[3] = 0.0;
        playersScoreGainAndMatchStartTime.put(damager, damagerArray);
        Object[] damageeArray = new Object[4];
        damageeArray[0] = 0;
        damageeArray[1] = System.currentTimeMillis();
        damageeArray[2] = 0.0;
        damageeArray[3] = 0.0;
        playersScoreGainAndMatchStartTime.put(damagee, damageeArray);
        soloPVPExecutor.playersScoreGainAndMatchStartTime=playersScoreGainAndMatchStartTime;

        soloPVPExecutor.figureDamage(event);

        assertEquals(2, (Double) damagerArray[2],0.0001);
        assertEquals(2,((Double)damageeArray[3]),0.0001);
    }
}
