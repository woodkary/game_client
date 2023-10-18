package com.kary.karyplugin.gamemodeExecutors;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import java.util.HashMap;
import java.util.Map;

//gamemode==1才符合
public class SoloPVPExecutor implements CommandExecutor, Listener {
    private Map<Player,Player> playersInSoloPVP=new HashMap<>();
    private Map<Player,Integer> playersMatchingGamemode;
    private Player matchingPlayer;

    public SoloPVPExecutor(Map<Player,Integer> playersMatchingGamemode) {
        this.playersMatchingGamemode=playersMatchingGamemode;
    }
    @EventHandler(priority = EventPriority.MONITOR)
    public void playerQuit(PlayerQuitEvent playerQuitEvent){
        if(playersInSoloPVP.size()==0){
            return;
        }
        Player loser=playerQuitEvent.getPlayer();
        Player winner=playersInSoloPVP.remove(loser);
        playersInSoloPVP.remove(winner);
        Bukkit.getServer().broadcastMessage("玩家"+loser.getName()+"退出了游戏，"+winner.getName()+"获得了胜利");
    }


    @EventHandler(priority = EventPriority.MONITOR)
    public void oneMatchOver(PlayerDeathEvent event){
        if(playersInSoloPVP.size()==0){
            return;
        }
        Player loser=event.getEntity();
        Player winner=playersInSoloPVP.remove(loser);
        playersInSoloPVP.remove(winner);
        Bukkit.getServer().broadcastMessage("玩家"+winner.getName()+"击败了"+loser.getName());
    }

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] args) {
        boolean result = false;
        if ("soloPVP".equals(args[0]) && commandSender instanceof Player) {
            Integer gamemode=playersMatchingGamemode.get(commandSender);
            if(gamemode==null){
                //如果此人还未进入匹配，给他加入匹配
                if(matchingPlayer==null){
                    //如果此时没有一人正在匹配，把此人设为正在匹配，并加入记录此时正在匹配人的集合
                    playersMatchingGamemode.put((Player) commandSender,1);
                    matchingPlayer= (Player) commandSender;
                    ((Player) commandSender).sendRawMessage("您正在匹配单人PVP，等待其他玩家加入游戏……");
                }else{
                    //将他的对手设为正在匹配的那人，将此人从时正在匹配人的集合移除，此时正在匹配的记录设为null
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
