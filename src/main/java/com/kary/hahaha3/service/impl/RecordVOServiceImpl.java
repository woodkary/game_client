package com.kary.hahaha3.service.impl;

import com.kary.hahaha3.exceptions.errorInput.MatchTypeErrorException;
import com.kary.hahaha3.mapper.GamesMapper;
import com.kary.hahaha3.mapper.RecordMapper;
import com.kary.hahaha3.pojo.Games;
import com.kary.hahaha3.pojo.Record;
import com.kary.hahaha3.pojo.vo.RecordVO;
import com.kary.hahaha3.service.RecordVOService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @author:123
 */
@Service
@Qualifier("RecordVOService")
public class RecordVOServiceImpl implements RecordVOService {
    @Autowired
    private RecordMapper recordMapper;
    @Autowired
    private GamesMapper gamesMapper;
    @Override
    //输入我自己的名字和比赛列表
    public List<RecordVO> getGamesByIds(String username,Integer type) throws MatchTypeErrorException {
        List<RecordVO> records=new ArrayList<>();
        List<Record> recordList=recordMapper.selectRecordsByUsername(username);
        for (Record record : recordList) {
            Games game;
            if(type==null){
                game=gamesMapper.getGameById(record.getGameId());
            }else{
                if(type!=1&&type!=2){
                    throw new MatchTypeErrorException("错误的比赛类型");
                }
                game=gamesMapper.getGameByIdAndType(record.getGameId(),type);
            }

            int kill=record.getKill();
            int death=record.getDeath();
            double kd=kill*1.0/death;
            RecordVO recordVO=new RecordVO();
            recordVO.setGameTime(game.getGameTime());
            recordVO.setKills(kill);
            recordVO.setDeaths(death);
            recordVO.setAssists(record.getAssist());
            recordVO.setKd(kd);
            recordVO.setDuration(game.getDuration());
            String typeString = switch (type) {
                case 1 -> "1v1";
                case 2 -> "大乱斗";
                default -> "";
            };
            recordVO.setType(typeString);
            records.add(recordVO);
        }

        return records;
    }

    @Override
    public List<RecordVO> getGamesByIds(String username,Integer type,int page) throws MatchTypeErrorException {
        int length=64,fromIndex=page-1,toIndex=fromIndex+length;
        List<RecordVO> res=getGamesByIds(username, type);
        if(fromIndex>=res.size()){
            return new ArrayList<>();
        }
        return res.subList(fromIndex,toIndex< res.size()?toIndex: res.size());
    }
}
