package com.kary.karyplugin;

import com.kary.karyplugin.gamemodeExecutors.BaseController;
import com.kary.karyplugin.gamemodeExecutors.BrawlExecutor;
import com.kary.karyplugin.gamemodeExecutors.QuitMatchingExecutor;
import com.kary.karyplugin.gamemodeExecutors.SoloPVPExecutor;
import com.kary.karyplugin.service.impl.RecordServiceImpl;
import okhttp3.*;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;
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
                                             Integer gameMode) {
        /*
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

         */
        OkHttpClient client = new OkHttpClient();

        // 构建 JSON 请求体
        MediaType mediaType = MediaType.parse("application/json");
        String jsonData = "{\"maxGameId\":" + maxGameId +
                ",\"duration\":" + duration +
                ",\"username\":\"" + username +
                "\",\"kill\":" + kill +
                ",\"death\":" + death +
                ",\"scoreGain\":" + scoreGain +
                ",\"assist\":" + assist +
                ",\"takeDamage\":" + takeDamage +
                ",\"takenDamage\":" + takenDamage +
                ",\"mvpPlayer\":\"" + mvpPlayer +
                "\",\"gameMode\":" + gameMode + "}";

        RequestBody requestBody = RequestBody.create(mediaType,jsonData);

        // 构建 HTTP 请求
        Request request = new Request.Builder()
                .url("http://localhost:8080/games/recordNewMatch")
                .post(requestBody)
                .build();

        try {
            // 发送 HTTP 请求
            client.newCall(request).enqueue(new Callback() {
                // 请求失败的回调方法
                @Override
                public void onFailure(@NotNull Call call, @NotNull IOException e) {
                    e.printStackTrace();
                    //重新发送请求
                    client.newCall(request).enqueue(this);

                }

                // 请求成功的回调方法
                @Override
                public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                    // 获取响应体的字符串
                    String string = null;
                    if (response.body() != null) {
                        string = response.body().string();
                    }
                    System.out.println(string);
                }
            });
            /*// 处理响应，可以根据需要进行错误处理或其他操作
            if (!response.isSuccessful()) {
                throw new IOException("Unexpected code " + response);
            }

            // 关闭响应
            response.close();*/
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    //1=Solo
    @Override
    public void onEnable() {
        // Plugin startup logic
        Bukkit.getServer().setIdleTimeout(3000);
        Bukkit.getWorld("world").setSpawnLocation(new Location(Bukkit.getWorld("world"),223,4,-345));
        SoloPVPExecutor soloPVPExecutor=new SoloPVPExecutor(this,playersMatchingGamemode,recordService);
        BrawlExecutor brawlExecutor=new BrawlExecutor(recordService,playersMatchingGamemode,this);
        QuitMatchingExecutor quitMatchingExecutor = new QuitMatchingExecutor(playersMatchingGamemode, recordService, soloPVPExecutor, brawlExecutor);
        Bukkit.getPluginManager().registerEvents(soloPVPExecutor, this);
        Bukkit.getPluginManager().registerEvents(brawlExecutor,this);
        Bukkit.getPluginManager().registerEvents(quitMatchingExecutor,this);
        Bukkit.getPluginManager().registerEvents(new BaseController(recordService),this);
        Bukkit.getPluginCommand("soloPVP").setExecutor(soloPVPExecutor);
        Bukkit.getPluginCommand("brawl").setExecutor(brawlExecutor);
        //加入新游戏模式，需要修改此处
        Bukkit.getPluginCommand("quitMatching").setExecutor(quitMatchingExecutor);
        Bukkit.getPluginCommand("testDatabase").setExecutor(new DatabaseTester(recordService));
    }
    @Override
    public void onDisable() {
        Bukkit.getServer().getOnlinePlayers().forEach(player -> {
            //把所有玩家设置为不在线
            recordService.updateOnMatch(player.getName(),0);
        });
        recordService.session.close();
        // Plugin shutdown logic
    }
}
