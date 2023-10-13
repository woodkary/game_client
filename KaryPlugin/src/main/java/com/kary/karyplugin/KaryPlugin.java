package com.kary.karyplugin;

import com.kary.karyplugin.gamemodeExecutors.QuitMatchingExecutor;
import com.kary.karyplugin.gamemodeExecutors.SoloPVPExecutor;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import java.util.HashMap;
import java.util.Map;

public final class KaryPlugin extends JavaPlugin implements Listener {

    private Map<Player,Player> playersInSoloPVP=new HashMap<>();
    private Map<Player,Integer> playersMatchingGamemode=new HashMap<>();
    //1=Solo，2=起床
    @Override
    public void onEnable() {
        // Plugin startup logic
        Bukkit.getPluginManager().registerEvents(this, this);
        Bukkit.getPluginCommand("joinGame").setExecutor(new SoloPVPExecutor(playersInSoloPVP,playersMatchingGamemode));
        Bukkit.getPluginCommand("quitMatching").setExecutor(new QuitMatchingExecutor(playersMatchingGamemode));
    }
    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
    @EventHandler(priority = EventPriority.MONITOR)
    public void playerQuit(PlayerQuitEvent playerQuitEvent){
        if(playersInSoloPVP.size()==0){
            return;
        }
        Player loser=playerQuitEvent.getPlayer();
        Player winner=playersInSoloPVP.remove(loser);
        playersInSoloPVP.remove(winner);
        Bukkit.getServer().broadcastMessage("玩家"+loser.getName()+"退出了游戏，"+winner.getName()+"获得了胜利");
    }


    @EventHandler(priority = EventPriority.MONITOR)
    public void oneMatchOver(PlayerDeathEvent event){
        if(playersInSoloPVP.size()==0){
            return;
        }
        Player loser=event.getEntity();
        Player winner=playersInSoloPVP.remove(loser);
        playersInSoloPVP.remove(winner);
        Bukkit.getServer().broadcastMessage("玩家"+winner.getName()+"击败了"+loser.getName());
    }
}
