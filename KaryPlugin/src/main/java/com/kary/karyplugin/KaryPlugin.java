package com.kary.karyplugin;

import com.kary.karyplugin.gamemodeExecutors.QuitMatchingExecutor;
import com.kary.karyplugin.gamemodeExecutors.SoloPVPExecutor;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public final class KaryPlugin extends JavaPlugin {
    private Map<Player,Integer> playersMatchingGamemode=new ConcurrentHashMap<>();
    //1=Solo，2=起床
    @Override
    public void onEnable() {
        // Plugin startup logic
        SoloPVPExecutor soloPVPExecutor=new SoloPVPExecutor(playersMatchingGamemode);
        Bukkit.getPluginManager().registerEvents(soloPVPExecutor, this);
        Bukkit.getPluginCommand("joinGame").setExecutor(soloPVPExecutor);
        Bukkit.getPluginCommand("quitMatching").setExecutor(new QuitMatchingExecutor(playersMatchingGamemode));
    }
    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
