package com.kary.karyplugin.gamemodeExecutors;

import com.kary.karyplugin.service.RecordService;
import com.kary.karyplugin.utils.CommandUtil;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

import java.util.Map;

public class QuitMatchingExecutor implements CommandExecutor, Listener {
    private Map<Player,Integer> playersMatchingGamemode;
    private BaseExecutor[] executors;
    RecordService recordService;

    public QuitMatchingExecutor(Map<Player, Integer> playersMatchingGamemode,RecordService recordService,BaseExecutor... executors) {
        this.playersMatchingGamemode = playersMatchingGamemode;
        this.executors=executors;
        this.recordService=recordService;
    }
    @EventHandler(priority = EventPriority.MONITOR)
    public void onPlayerQuit(PlayerQuitEvent event){
        Player player=event.getPlayer();
        playersMatchingGamemode.remove(player);
        //把退出游戏的玩家设置为不在线
        recordService.updateOnMatch(player.getName(),0);

    }

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        Integer gamemode=playersMatchingGamemode.remove(commandSender);
        if(gamemode!=null){
            Player player=((Player) commandSender);
            //有人退出匹配，通知所有的监听器取消掉该玩家
            for (BaseExecutor executor : executors) {
                executor.playerQuitMatching(player);
            }
            player.sendRawMessage("成功退出匹配");
            player.sendRawMessage("请选择游戏模式");
            player.setOp(true);
            player.performCommand(CommandUtil.COMMAND_SOLO_PVP);
            player.performCommand(CommandUtil.COMMAND_BRAWL);
            player.setOp(false);
            return true;
        }else{
            ((Player) commandSender).sendRawMessage("您没有正在匹配的游戏");
            return false;
        }

    }
}
