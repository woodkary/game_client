package com.kary.karyplugin.killingrecording;

import org.bukkit.Bukkit;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerLoginEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.*;


public final class KillingRecording extends JavaPlugin implements Listener {

    private Map<Player,Player> playersFighting=new HashMap<>();
    @Override
    public void onEnable() {
        // Plugin startup logic
        Bukkit.getPluginManager().registerEvents(this,this);
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
    @EventHandler(priority = EventPriority.MONITOR)
    public void playerQuit(PlayerQuitEvent playerQuitEvent){
        Player loser=playerQuitEvent.getPlayer();
        Player winner=playersFighting.remove(loser);
        playersFighting.remove(winner);
        Bukkit.getServer().broadcastMessage("玩家"+loser.getName()+"退出了游戏，"+winner.getName()+"获得了胜利");
    }
    @EventHandler(priority = EventPriority.MONITOR)
    public void checkTwoFightingPlayers(EntityDamageByEntityEvent event){
        Entity damager=event.getDamager(),damagee=event.getEntity();
        if(damager instanceof Player&&damagee instanceof Player){
            playersFighting.put((Player) damager, (Player) damagee);
            playersFighting.put((Player) damagee, (Player) damager);
        }
    }
    @EventHandler(priority = EventPriority.MONITOR)
    public void oneMatchOver(PlayerDeathEvent event){
        Player loser=event.getEntity();
        Player winner=playersFighting.remove(loser);
        playersFighting.remove(winner);
        Bukkit.getServer().broadcastMessage("玩家"+winner.getName()+"击败了"+loser.getName());
    }
}
