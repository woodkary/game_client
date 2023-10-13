package com.kary.karyplugin.gamemodeExecutors;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Map;

//gamemode==1才符合
public class SoloPVPExecutor implements CommandExecutor {
    private Map<Player,Player> playersInSoloPVP;
    private Map<Player,Integer> playersMatchingGamemode;
    private Player matchingPlayer;

    public SoloPVPExecutor(Map<Player, Player> playersInSoloPVP,Map<Player,Integer> playersMatchingGamemode) {
        this.playersInSoloPVP = playersInSoloPVP;
        this.playersMatchingGamemode=playersMatchingGamemode;
    }

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] args) {
        boolean result = false;
        if ("soloPVP".equals(args[0]) && commandSender instanceof Player) {
            Integer gamemode=playersMatchingGamemode.get(commandSender);
            if(gamemode==null){
                if(matchingPlayer==null){
                    playersMatchingGamemode.put((Player) commandSender,1);
                    matchingPlayer= (Player) commandSender;
                    ((Player) commandSender).sendRawMessage("您正在匹配单人PVP，等待其他玩家加入游戏……");
                }else{
                    playersInSoloPVP.put((Player) commandSender,matchingPlayer);
                    playersInSoloPVP.put(matchingPlayer, (Player) commandSender);
                    playersMatchingGamemode.remove(matchingPlayer);
                    ((Player) commandSender).sendRawMessage("您已匹配对手"+matchingPlayer.getName()+",对局开始");
                    matchingPlayer.sendRawMessage("您的对手是"+commandSender.getName()+",对局开始");
                    matchingPlayer=null;
                    result=true;
                }
            }else{
                if(gamemode==1){
                    ((Player) commandSender).sendRawMessage("您已在单人PVP匹配中，无需再加入匹配");
                }else{
                    ((Player) commandSender).sendRawMessage("您正在匹配其他游戏，请先退出");
                }
            }
        }
        return result;
    }
}
