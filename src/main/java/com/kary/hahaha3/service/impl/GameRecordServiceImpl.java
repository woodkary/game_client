package com.kary.hahaha3.service.impl;

import com.kary.hahaha3.mapper.GamesMapper;
import com.kary.hahaha3.mapper.RecordMapper;
import com.kary.hahaha3.mapper.UserGameMapper;
import com.kary.hahaha3.pojo.UserGame;
import com.kary.hahaha3.service.GameRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author:123
 */
@Service
@Qualifier("GameRecordService")
public class GameRecordServiceImpl implements GameRecordService {
    @Autowired
    RecordMapper recordMapper;
    @Autowired
    GamesMapper gamesMapper;
    @Autowired
    UserGameMapper userGameMapper;
    @Override
    @Transactional
    public Integer recordNewMatch(Integer maxGameId, Long duration, String username, Integer kill, Integer death, Integer scoreGain, Integer assist, Double takeDamage, Double takenDamage, String mvpPlayer, Integer gameMode) {
        Integer num1=userGameMapper.addScore(username,gameMode,scoreGain);//先给用户加分
        //再更新用户的最高分数
        UserGame user=userGameMapper.getUserGame(username);//获取用户信息
        int scoreTotal1v1=user.getScoreTotal1v1();//用户现有分数
        int scoreTotalBrawl=user.getScoreTotalBrawl();
        int maxScore1v1=user.getMaxScore1v1();//用户最高分数
        int maxScoreBrawl=user.getMaxScoreBrawl();
        //更新用户的最高分数
        if(scoreTotal1v1>maxScore1v1){
            userGameMapper.updateMaxScore(username,1);
        }
        if(scoreTotalBrawl>maxScoreBrawl){
            userGameMapper.updateMaxScore(username,2);
        }
        Integer num2=userGameMapper.updateMaxScore(username,gameMode);
        Integer num3=recordMapper.addGamesCount(username);
        Integer num4=0;
        if(gameMode==1){
            num4=recordMapper.addGamesCount1v1(username);
        }
        if(gameMode==2){
            num4=recordMapper.addGamesCountBrawl(username);
        }
        Integer num5=recordMapper.addNewRecord(maxGameId,username,kill,death,(gameMode==1)?0:assist,scoreGain,takeDamage, takenDamage);
        return (num1==1&&num2==1&&num3==1&&num4==1&&num5==1)?1:0;
    }
    @Override
    public Integer addNewGame(Integer type, Integer gameId, Long duration, String mvpPlayer) {
        return gamesMapper.addNewGame(type,gameId,duration,mvpPlayer);
    }

}
