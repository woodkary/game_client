package com.kary.karyplugin;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Map;

public class SoloPVPExecutor implements CommandExecutor {
    private Map<Player,Player> playersFighting;
    private Player matchingPlayers;

    public SoloPVPExecutor(Map<Player, Player> playersFighting) {
        this.playersFighting = playersFighting;
    }

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] args) {
        if("soloPVP".equals(args[0])&&commandSender instanceof Player){
            if(matchingPlayers==null){
                matchingPlayers= (Player) commandSender;
                ((Player) commandSender).sendRawMessage("请等待其他玩家加入游戏");
            }else{
                playersFighting.put(matchingPlayers, (Player) commandSender);
                playersFighting.put((Player) commandSender,matchingPlayers);
                ((Player) commandSender).sendRawMessage("成功加入游戏");
                matchingPlayers=null;
            }
            return true;
        }
        return false;
    }
}
