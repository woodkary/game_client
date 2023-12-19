package com.kary.hahaha3.service;

/**
 * @author:123
 */
public interface GameRecordService {
    Integer recordNewMatch(Integer maxGameId,
                        Long duration,
                        String username,
                        Integer kill,
                        Integer death,
                        Integer scoreGain,
                        Integer assist,
                        Double takeDamage,
                        Double takenDamage,
                        String mvpPlayer,
                        Integer gameMode);
}
