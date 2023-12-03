package com.kary.hahaha3.service.impl;

import com.kary.hahaha3.exceptions.errorInput.MatchTypeErrorException;
import com.kary.hahaha3.exceptions.errorInput.UsernameErrorException;
import com.kary.hahaha3.mapper.GamesMapper;
import com.kary.hahaha3.mapper.RecordMapper;
import com.kary.hahaha3.mapper.UserGameMapper;
import com.kary.hahaha3.mapper.UserMapper;
import com.kary.hahaha3.pojo.Games;
import com.kary.hahaha3.pojo.Record;
import com.kary.hahaha3.pojo.User;
import com.kary.hahaha3.pojo.vo.PersonalReport;
import com.kary.hahaha3.service.PersonalReportService;
import com.kary.hahaha3.utils.LevelUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author:123
 */
@Service
@Qualifier("PersonalReportService")
public class PersonalReportServiceImpl implements PersonalReportService {
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private UserGameMapper userGameMapper;
    @Autowired
    private GamesMapper gamesMapper;
    @Autowired
    private RecordMapper recordMapper;
    @Override
    public PersonalReport getPersonalReport(String username, int type) throws MatchTypeErrorException, UsernameErrorException {
        if(type!=1&&type!=2){
            throw new MatchTypeErrorException("错误的比赛类型");
        }
        User account=userMapper.selectUserByName(username);
        if(account==null){
            throw new UsernameErrorException("用户不存在");
        }
        Integer score=userGameMapper.getScoreByType(username,type);
        String level= LevelUtil.getLevel(score);
        PersonalReport res=new PersonalReport();
        res.setType(type);
        res.setLevel(level);

        int gameNums = 0;
        int win = 0;
        int lose = 0;
        double winRate = 0;

        List<Record> recordList=recordMapper.selectRecordsByUsername(username);
        for (Record record : recordList) {
            Games game=gamesMapper.getGameByIdAndType(record.getGameId(),type);
            if(game==null){
                continue;
            }
            gameNums+=1;
            if(type==1){
                if(record.getKill()>record.getDeath()){
                    win+=1;
                }else {
                    lose+=1;
                }
            }
        }
        winRate=(lose==0?(win*1.0/lose):win*1.0);
        res.setGameNums(gameNums);
        res.setWin(win);
        res.setLose(lose);
        res.setWinRate(winRate);
        return res;
    }
}
