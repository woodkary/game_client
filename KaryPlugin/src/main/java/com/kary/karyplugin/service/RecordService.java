package com.kary.karyplugin.service;

import com.kary.karyplugin.pojo.UserGame;

/**
 * @author:123
 */
public interface RecordService {
    Integer getScoreTotal(String username,Integer gameMode);

    void addScore(String username, Integer gameMode, Integer addNum);

    UserGame selectUserByName(String username);
    void recordNewMatch(Long duration,
                        String username,
                        Integer kill,
                        Integer death,
                        Integer gameMode,
                        Integer assists,
                        Integer scoreGain,
                        Double takeDamage,
                        Double takenDamage,
                        String mvpPlayer);
}