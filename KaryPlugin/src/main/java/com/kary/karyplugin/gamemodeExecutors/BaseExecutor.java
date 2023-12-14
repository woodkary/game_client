package com.kary.karyplugin.gamemodeExecutors;

import org.bukkit.command.CommandExecutor;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;

/**
 * @author:123
 */
public abstract class BaseExecutor implements Listener, CommandExecutor {
    public abstract void playerQuitMatching(Player player);
}
