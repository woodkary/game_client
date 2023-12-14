package com.kary.karyplugin.gamemodeExecutors;

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

    public QuitMatchingExecutor(Map<Player, Integer> playersMatchingGamemode,BaseExecutor... executors) {
        this.playersMatchingGamemode = playersMatchingGamemode;
        this.executors=executors;
    }
    @EventHandler(priority = EventPriority.MONITOR)
    public void onPlayerQuit(PlayerQuitEvent event){
        Player player=event.getPlayer();
        playersMatchingGamemode.remove(player);
    }

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        Integer gamemode=playersMatchingGamemode.remove(commandSender);
        if(gamemode!=null){
            Player player=((Player) commandSender);
            //通知所有的监听器取消掉该玩家
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
