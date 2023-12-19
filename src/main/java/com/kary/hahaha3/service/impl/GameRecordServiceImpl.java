package com.kary.hahaha3.service.impl;

import com.kary.hahaha3.mapper.GamesMapper;
import com.kary.hahaha3.mapper.RecordMapper;
import com.kary.hahaha3.mapper.UserGameMapper;
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
        Integer num1=userGameMapper.addScore(username,gameMode,scoreGain);
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


}
