package com.kary.hahaha3.service.impl;

import com.kary.hahaha3.mapper.GamesMapper;
import com.kary.hahaha3.mapper.RecordMapper;
import com.kary.hahaha3.pojo.Games;
import com.kary.hahaha3.pojo.Record;
import com.kary.hahaha3.pojo.vo.Records;
import com.kary.hahaha3.service.RecordsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @author:123
 */
@Service
@Qualifier("RecordsService")
public class RecordsServiceImpl implements RecordsService {
    @Autowired
    private GamesMapper gamesMapper;
    @Autowired
    private RecordMapper recordMapper;
    @Override
    public Records getAllGame(String username) {
        Records res=new Records();
        int kill=0,death=0,win=0,gameNums=0;
        List<Record> recordList=recordMapper.selectRecordsByUsername(username);
        gameNums=recordList.size();
        for (Record record : recordList) {
            int gameId= record.getGameId();
            Games game=gamesMapper.getGameById(gameId);
            kill+= record.getKill();
            death+= record.getDeath();
            if(game.getType()==1&&record.getKill()>record.getDeath()){
                win+=1;
            }
        }
        res.setGameNums(gameNums);
        res.setTotalKills(kill);
        res.setWinRate(gameNums>0?(win*1.0/gameNums):win*1.0);
        res.setKda(death>0?(kill*1.0/death):kill*1.0);
        return res;
    }

    @Override
    public Records getAllGameThisMonth(String username) {
        Records res=new Records();
        int kill=0,death=0,win=0,gameNums=0;
        List<Record> recordList=recordMapper.selectRecordsByUsername(username);
        for (Record record : recordList) {
            int gameId= record.getGameId();
            Games game=gamesMapper.getThisMonthGameById(gameId);
            if(game==null){
                continue;
            }
            gameNums+=1;
            kill+= record.getKill();
            death+= record.getDeath();
            if(game.getType()==1&&record.getKill()>record.getDeath()){
                win+=1;
            }
        }
        res.setGameNums(gameNums);
        res.setTotalKills(kill);
        res.setWinRate(gameNums>0?(win*1.0/gameNums):win*1.0);
        res.setKda(death>0?(kill*1.0/death):kill*1.0);
        return res;
    }
}
