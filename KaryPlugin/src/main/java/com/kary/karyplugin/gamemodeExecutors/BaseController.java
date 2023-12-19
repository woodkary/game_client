package com.kary.karyplugin.gamemodeExecutors;

import com.kary.karyplugin.pojo.UserGame;
import com.kary.karyplugin.service.RecordService;
import com.kary.karyplugin.utils.CommandUtil;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

/**
 * @author:123
 */
//检验有无注册，以及发出选择游戏模式的提示
public class BaseController implements Listener {
    RecordService recordService;

    public BaseController(RecordService recordService) {
        this.recordService = recordService;
    }

    @EventHandler(priority = EventPriority.MONITOR)
    public void chooseGameMode(PlayerJoinEvent event){
        Player player= event.getPlayer();
        UserGame userGame= recordService.selectUserByName(player.getName());
        if(userGame==null){
            player.kickPlayer("您还没有注册账号，无法进入游戏");
        }
        //把加入游戏的玩家设置为在线
        recordService.updateOnMatch(player.getName(),1);
        player.sendRawMessage("请选择游戏模式");
        player.setOp(true);
        player.performCommand(CommandUtil.COMMAND_SOLO_PVP);
        player.performCommand(CommandUtil.COMMAND_BRAWL);
        player.setOp(false);
    }
}
