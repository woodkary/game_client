package com.kary.karyplugin.service.impl;

import com.kary.karyplugin.dao.RecordMapper;
import com.kary.karyplugin.dao.SqlSessionSettings;
import com.kary.karyplugin.service.RecordService;

/**
 * @author:123
 */
public class RecordServiceImpl implements RecordService {
    RecordMapper recordMapper= SqlSessionSettings.getSqlSession().getMapper(RecordMapper.class);
    @Override
    public void recordNewMatch(Long duration, String usernamePlay1, String usernamePlay2, Integer killPlay1, Integer killPlay2, Integer deathPlay1, Integer deathPlay2, Integer scoreGainPlay1, Integer scoreGainPlay2, Integer scoreTotalPlay1, Integer scoreTotalPlay2) {
        Integer maxGameId=recordMapper.getMaxGameId();
        maxGameId+=1;
        recordMapper.addNewRecord(maxGameId,duration,usernamePlay1,usernamePlay2,killPlay1,killPlay2,deathPlay1,deathPlay2,scoreGainPlay1,scoreGainPlay2,scoreTotalPlay1,scoreTotalPlay2);
        recordMapper.addUserNewGameId(usernamePlay1,maxGameId.toString());
    }

    @Override
    public Integer getScoreTotal(String username) {
        return recordMapper.getScoreTotal(username);
    }

    @Override
    public void addScore(String username, Integer addNum) {
        recordMapper.addScore(username,addNum);
    }
}
