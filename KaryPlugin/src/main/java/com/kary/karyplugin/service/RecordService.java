package com.kary.karyplugin.service;

import com.kary.karyplugin.pojo.User;

/**
 * @author:123
 */
public interface RecordService {
    void recordNewMatch(Long duration,
                        String usernamePlay1,
                        String usernamePlay2,
                        Integer killPlay1,
                        Integer killPlay2,
                        Integer deathPlay1,
                        Integer deathPlay2,
                        Integer scoreGainPlay1,
                        Integer scoreGainPlay2,
                        Integer scoreTotalPlay1,
                        Integer scoreTotalPlay2);
    Integer getScoreTotal(String username);
    void addScore(String username,Integer addNum);
    User selectUserByName(String username);
}
