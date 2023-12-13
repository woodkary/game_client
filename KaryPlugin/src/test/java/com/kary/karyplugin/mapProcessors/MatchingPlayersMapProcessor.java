package com.kary.karyplugin.mapProcessors;

import org.bukkit.entity.Player;

import java.util.Map;

/**
 * @author:123
 */
public class MatchingPlayersMapProcessor {
    private Map<Integer, Player> matchingPlayers;

    public MatchingPlayersMapProcessor(Map<Integer, Player> matchingPlayers) {
        this.matchingPlayers = matchingPlayers;
    }
    public void removePlayerInThisLevel(Integer level,Player player){
        matchingPlayers.compute(level,(key,p)-> null);
    }
}
