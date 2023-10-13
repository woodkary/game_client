package com.kary.karyplugin;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.Bukkit;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import java.util.HashMap;
import java.util.Map;

public final class KaryPlugin extends JavaPlugin implements Listener {

    private Map<Player,Player> playersFighting=new HashMap<>();
    @Override
    public void onEnable() {
        // Plugin startup logic
        Bukkit.getPluginManager().registerEvents(this, this);
        Bukkit.getPluginCommand("join").setExecutor(new SoloPVPExecutor(playersFighting));
    }
    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
    @EventHandler(priority = EventPriority.MONITOR)
    public void playerQuit(PlayerQuitEvent playerQuitEvent){
        if(playersFighting.size()==0){
            return;
        }
        Player loser=playerQuitEvent.getPlayer();
        Player winner=playersFighting.remove(loser);
        playersFighting.remove(winner);
        Bukkit.getServer().broadcastMessage("玩家"+loser.getName()+"退出了游戏，"+winner.getName()+"获得了胜利");
    }


    @EventHandler(priority = EventPriority.MONITOR)
    public void oneMatchOver(PlayerDeathEvent event){
        if(playersFighting.size()==0){
            return;
        }
        Player loser=event.getEntity();
        Player winner=playersFighting.remove(loser);
        playersFighting.remove(winner);
        Bukkit.getServer().broadcastMessage("玩家"+winner.getName()+"击败了"+loser.getName());
    }
}
