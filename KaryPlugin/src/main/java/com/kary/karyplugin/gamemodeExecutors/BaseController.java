package com.kary.karyplugin.gamemodeExecutors;

import com.kary.karyplugin.utils.CommandUtil;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

/**
 * @author:123
 */
public class BaseController implements Listener {
    @EventHandler(priority = EventPriority.MONITOR)
    public void chooseGameMode(PlayerJoinEvent event){
        Player player= event.getPlayer();
        player.performCommand(CommandUtil.COMMAND_SOLO_PVP);
        player.performCommand(CommandUtil.COMMAND_BRAWL);
    }
}
