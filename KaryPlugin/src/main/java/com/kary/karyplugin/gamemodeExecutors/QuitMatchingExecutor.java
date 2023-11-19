package com.kary.karyplugin.gamemodeExecutors;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Map;

public class QuitMatchingExecutor implements CommandExecutor {
    private Map<Player,Integer> playersMatchingGamemode;
    public static final int SOLOPVP_MODE=1;

    public QuitMatchingExecutor(Map<Player, Integer> playersMatchingGamemode) {
        this.playersMatchingGamemode = playersMatchingGamemode;
    }

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        Integer gamemode=playersMatchingGamemode.remove(commandSender);
        if(gamemode!=null){
            ((Player) commandSender).sendRawMessage("成功退出匹配");
            return true;
        }else{
            ((Player) commandSender).sendRawMessage("您没有正在匹配的游戏");
            return false;
        }

    }
}
