package com.kary.karyplugin;

import com.kary.karyplugin.gamemodeExecutors.BaseController;
import com.kary.karyplugin.gamemodeExecutors.BrawlExecutor;
import com.kary.karyplugin.gamemodeExecutors.QuitMatchingExecutor;
import com.kary.karyplugin.gamemodeExecutors.SoloPVPExecutor;
import com.kary.karyplugin.service.impl.RecordServiceImpl;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author karywoodOyo
 */
public class KaryPlugin extends JavaPlugin {
    private static Map<Player,Integer> playersMatchingGamemode=new ConcurrentHashMap<>();
    private static  RecordServiceImpl recordService=new RecordServiceImpl();
    public static synchronized void updateDatabase(Integer maxGameId,
                                             Long duration,
                                             String username,
                                             Integer kill,
                                             Integer death,
                                             Integer scoreGain,
                                             Integer assist,
                                             Double takeDamage,
                                             Double takenDamage,
                                             String mvpPlayer,
                                             Integer gameMode){
        recordService.addScore(username,gameMode,scoreGain);
        recordService.recordNewMatch(
                maxGameId,
                duration,
                username,
                kill,
                death,
                gameMode,
                assist,
                scoreGain,
                takeDamage,
                takenDamage,
                mvpPlayer
        );
        recordService.session.commit();
    }
    //1=Solo
    @Override
    public void onEnable() {
        // Plugin startup logic
        SoloPVPExecutor soloPVPExecutor=new SoloPVPExecutor(playersMatchingGamemode,recordService);
        BrawlExecutor brawlExecutor=new BrawlExecutor(recordService,playersMatchingGamemode,this);
        Bukkit.getPluginManager().registerEvents(soloPVPExecutor, this);
        Bukkit.getPluginManager().registerEvents(brawlExecutor,this);
        Bukkit.getPluginManager().registerEvents(new BaseController(recordService),this);
        Bukkit.getPluginCommand("soloPVP").setExecutor(soloPVPExecutor);
        Bukkit.getPluginCommand("brawl").setExecutor(brawlExecutor);
        Bukkit.getPluginCommand("quitMatching").setExecutor(new QuitMatchingExecutor(playersMatchingGamemode,soloPVPExecutor,brawlExecutor));
        Bukkit.getPluginCommand("testDatabase").setExecutor(new DatabaseTester(recordService));
    }
    @Override
    public void onDisable() {
        recordService.session.close();
        // Plugin shutdown logic
    }
}
