package com.kary.karyplugin;

import com.kary.karyplugin.gamemodeExecutors.BrawlExecutor;
import com.kary.karyplugin.gamemodeExecutors.QuitMatchingExecutor;
import com.kary.karyplugin.gamemodeExecutors.SoloPVPExecutor;
import com.kary.karyplugin.service.RecordService;
import com.kary.karyplugin.service.impl.RecordServiceImpl;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public final class KaryPlugin extends JavaPlugin {
    private static Map<Player,Integer> playersMatchingGamemode=new ConcurrentHashMap<>();
    private static  RecordService recordService=new RecordServiceImpl();
    public static synchronized void updateDatabase(Long duration,
                                             String username,
                                             Integer kill,
                                             Integer death,
                                             Integer scoreGain,
                                             Integer assist,
                                             Integer gameMode,
                                             String mvpPlayer){
        recordService.addScore(username,gameMode,scoreGain);
        recordService.recordNewMatch(
                duration,
                username,
                kill,
                death,
                gameMode,
                assist,
                mvpPlayer
        );
    }
    //1=Solo
    @Override
    public void onEnable() {
        // Plugin startup logic
        SoloPVPExecutor soloPVPExecutor=new SoloPVPExecutor(playersMatchingGamemode,recordService);
        BrawlExecutor brawlExecutor=new BrawlExecutor(recordService,playersMatchingGamemode,this);
        Bukkit.getPluginManager().registerEvents(soloPVPExecutor, this);
        Bukkit.getPluginManager().registerEvents(brawlExecutor,this);
        Bukkit.getPluginCommand("soloPVP").setExecutor(soloPVPExecutor);
        Bukkit.getPluginCommand("brawl").setExecutor(brawlExecutor);
        Bukkit.getPluginCommand("quitMatching").setExecutor(new QuitMatchingExecutor(playersMatchingGamemode));
        Bukkit.getPluginCommand("testDatabase").setExecutor(new DatabaseTester(recordService));
    }
    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
