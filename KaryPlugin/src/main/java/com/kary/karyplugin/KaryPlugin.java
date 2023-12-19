package com.kary.karyplugin;

import com.kary.karyplugin.gamemodeExecutors.BaseController;
import com.kary.karyplugin.gamemodeExecutors.BrawlExecutor;
import com.kary.karyplugin.gamemodeExecutors.QuitMatchingExecutor;
import com.kary.karyplugin.gamemodeExecutors.SoloPVPExecutor;
import com.kary.karyplugin.service.impl.RecordServiceImpl;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author karywoodOyo
 */
//每有一个新游戏模式加入，都要继承BaseExecutor，插入到QuitMatchingExecutor,然后在onEnable里面注册事件和命令
//TODO 不要用op发命令，用服务器发命令
//TODO 禁用多余的输出，如命令的输出
//TODO 看能否读取server.properties里面的配置,比如场地数量和位置
public class KaryPlugin extends JavaPlugin {
    private static Map<Player,Integer> playersMatchingGamemode=new ConcurrentHashMap<>();
    private static  RecordServiceImpl recordService=new RecordServiceImpl();
    //更新数据库操作
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
        //先给用户加分
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
        Bukkit.getServer().setIdleTimeout(3000);
        Bukkit.getWorld("world").setSpawnLocation(new Location(Bukkit.getWorld("world"),223,4,-345));
        SoloPVPExecutor soloPVPExecutor=new SoloPVPExecutor(this,playersMatchingGamemode,recordService);
        BrawlExecutor brawlExecutor=new BrawlExecutor(recordService,playersMatchingGamemode,this);
        Bukkit.getPluginManager().registerEvents(soloPVPExecutor, this);
        Bukkit.getPluginManager().registerEvents(brawlExecutor,this);
        Bukkit.getPluginManager().registerEvents(new BaseController(recordService),this);
        Bukkit.getPluginCommand("soloPVP").setExecutor(soloPVPExecutor);
        Bukkit.getPluginCommand("brawl").setExecutor(brawlExecutor);
        //加入新游戏模式，需要修改此处
        Bukkit.getPluginCommand("quitMatching").setExecutor(new QuitMatchingExecutor(playersMatchingGamemode,soloPVPExecutor,brawlExecutor));
        Bukkit.getPluginCommand("testDatabase").setExecutor(new DatabaseTester(recordService));
    }
    @Override
    public void onDisable() {
        recordService.session.close();
        // Plugin shutdown logic
    }
}
