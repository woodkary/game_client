package com.kary.karyplugin.mapProcessors;

import org.bukkit.entity.Player;

import java.util.Map;

/**
 * @author:123
 */
public class PlayersInSoloPVPProcessor {
    private Map<Player,Player> playersInSoloPVP;

    public PlayersInSoloPVPProcessor(Map<Player, Player> playersInSoloPVP) {
        this.playersInSoloPVP = playersInSoloPVP;
    }
    public Player removePlayer(Player player){
        return playersInSoloPVP.remove(player);
    }
}
