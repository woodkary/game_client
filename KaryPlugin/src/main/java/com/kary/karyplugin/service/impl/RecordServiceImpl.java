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
        //更新用户的最高分数
        UserGame user=recordMapper.selectUserByName(username);//获取用户信息
        int scoreTotal1v1=user.getScoreTotal1v1();//用户现有分数
        int scoreTotalBrawl=user.getScoreTotalBrawl();
        int maxScore1v1=user.getMaxScore1v1();//用户最高分数
        int maxScoreBrawl=user.getMaxScoreBrawl();
        //更新用户的最高分数
        if(scoreTotal1v1>maxScore1v1){
            recordMapper.updateMaxScore(username,1);
        }
        if(scoreTotalBrawl>maxScoreBrawl){
            recordMapper.updateMaxScore(username,2);
        }
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
    public void updateOnMatch(String username, Integer onMatch) {
        recordMapper.updateOnMatch(username,onMatch);
        session.commit();
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
