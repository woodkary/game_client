package com.kary.karyplugin.finalClassProvider;

import com.kary.karyplugin.KaryPlugin;

/**
 * @author:123
 */
public class KaryPluginProvider {
    public static synchronized void updateDatabase(Integer maxGameId,Long duration,
                                                   String username,
                                                   Integer kill,
                                                   Integer death,
                                                   Integer scoreGain,
                                                   Integer assist,
                                                   Double takeDamage,
                                                   Double takenDamage,
                                                   String mvpPlayer,
                                                   Integer gameMode){
        KaryPlugin.updateDatabase(maxGameId,duration,username,kill,death,scoreGain,assist,takeDamage,takenDamage,mvpPlayer,gameMode);
    }
}
