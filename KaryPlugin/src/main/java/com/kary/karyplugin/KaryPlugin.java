package com.kary.karyplugin;

import com.kary.karyplugin.dao.RecordMapper;
import com.kary.karyplugin.dao.SqlSessionSettings;
import com.kary.karyplugin.gamemodeExecutors.QuitMatchingExecutor;
import com.kary.karyplugin.gamemodeExecutors.SoloPVPExecutor;
import com.kary.karyplugin.service.RecordService;
import com.kary.karyplugin.service.impl.RecordServiceImpl;
import org.apache.ibatis.session.SqlSession;
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
                                             Integer gameMode){
        recordService.addScore(username,gameMode,scoreGain);
        recordService.recordNewMatch(
                duration,
                username,
                kill,
                death,
                gameMode,
                assist
        );
    }
    //1=Solo
    @Override
    public void onEnable() {
        // Plugin startup logic
        SoloPVPExecutor soloPVPExecutor=new SoloPVPExecutor(playersMatchingGamemode,recordService);
        Bukkit.getPluginManager().registerEvents(soloPVPExecutor, this);
        Bukkit.getPluginCommand("joinGame").setExecutor(soloPVPExecutor);
        Bukkit.getPluginCommand("quitMatching").setExecutor(new QuitMatchingExecutor(playersMatchingGamemode));
        Bukkit.getPluginCommand("testDatabase").setExecutor(new DatabaseTester(recordService));
    }
    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
