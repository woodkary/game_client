package com.kary.karyplugin.service.impl;

import com.kary.karyplugin.dao.GamesMapper;
import com.kary.karyplugin.dao.RecordMapper;
import com.kary.karyplugin.dao.SqlSessionSettings;
import com.kary.karyplugin.pojo.UserGame;
import com.kary.karyplugin.service.RecordService;
import org.apache.ibatis.session.SqlSession;

/**
 * @author:123
 */
public class RecordServiceImpl implements RecordService {
    public SqlSession session;
    RecordMapper recordMapper;
    GamesMapper gamesMapper;

    public RecordServiceImpl() {
        session= new SqlSessionSettings().getSqlSession();
        recordMapper=session.getMapper(RecordMapper.class);
        gamesMapper=session.getMapper(GamesMapper.class);
    }

    @Override
    public void recordNewMatch(Integer maxGameId,
                               Long duration,
                               String username,
                               Integer kill,
                               Integer death,
                               Integer gameMode,
                               Integer assists,
                               Integer scoreGain,
                               Double takeDamage,
                               Double takenDamage,
                               String mvpPlayer) {
        recordMapper.addGamesCount(username);
        if(gameMode==1){
            recordMapper.addGamesCount1v1(username);
        }
        if(gameMode==2){
            recordMapper.addGamesCountBrawl(username);
        }
        recordMapper.addNewRecord(maxGameId,username,kill,death,(gameMode==1)?0:assists,scoreGain,takeDamage, takenDamage);
    }

    @Override
    public Integer getMaxGameId() {
        return gamesMapper.getMaxGameId();
    }

    @Override
    public Integer addNewGame(Integer type, Integer gameId, Long duration, String mvpPlayer) {
        return gamesMapper.addNewGame(type,gameId,duration,mvpPlayer);
    }

    @Override
    public Integer getScoreTotal(String username,Integer gameMode) {
        return recordMapper.getScoreTotal(username,gameMode);
    }

    @Override
    public void addScore(String username, Integer gameMode, Integer addNum) {
        recordMapper.addScore(username,gameMode,addNum);
    }

    @Override
    public UserGame selectUserByName(String username) {
        return recordMapper.selectUserByName(username);
    }
}
