package com.kary.hahaha3.service.impl;

import com.kary.hahaha3.exceptions.errorInput.GameNotFoundException;
import com.kary.hahaha3.exceptions.errorInput.MatchTypeErrorException;
import com.kary.hahaha3.mapper.GamesMapper;
import com.kary.hahaha3.mapper.RecordMapper;
import com.kary.hahaha3.mapper.UserGameMapper;
import com.kary.hahaha3.pojo.Games;
import com.kary.hahaha3.pojo.Record;
import com.kary.hahaha3.pojo.vo.RecordVO;
import com.kary.hahaha3.service.RecordVOService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
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
    @Autowired
    private UserGameMapper userGameMapper;
    @Override
    //输入我自己的名字和比赛列表
    public List<RecordVO> getGamesByUsername(String username,Integer type) throws MatchTypeErrorException {
        List<RecordVO> records=new ArrayList<>();
        List<Record> recordList=recordMapper.selectRecordsByUsername(username);
        Integer portrait=userGameMapper.getPortrait(username);
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
            int gameId= game.getGameId();
            int kill=record.getKill();
            int death=record.getDeath();
            int assist= record.getAssist();
            double kda=(kill*1.0+assist*0.7)/(death!=0?death:1);
            boolean isMVP=username.equals(game.getMvpPlayer());
            RecordVO recordVO=new RecordVO();
            recordVO.setGameId(gameId);
            recordVO.setGameTime(game.getGameTime());
            recordVO.setKills(kill);
            recordVO.setDeaths(death);
            recordVO.setAssists(assist);
            recordVO.setKda(kda);
            recordVO.setDuration(game.getDuration());
            recordVO.setScoreGain(record.getScoreGain());
            recordVO.setUsername(username);
            recordVO.setPortrait(portrait);
            recordVO.setMVP(isMVP);
            recordVO.setTakeDamage(record.getTakeDamage());
            recordVO.setTakenDamage(record.getTakenDamage());
            String typeString="";
            switch (game.getType()){
                case 1:typeString="1v1";break;
                case 2:typeString="大乱斗";break;
                default:break;
            }
            recordVO.setType(typeString);
            records.add(recordVO);
        }
        records.sort((o1, o2) -> {
            if(o1.getGameTime().after(o2.getGameTime())){
                return -1;
            }else if(o1.getGameTime().before(o2.getGameTime())){
                return 1;
            }else{
                return 0;
            }
        });
        return records;
    }

    @Override
    public List<RecordVO> getGamesByUsername(String username,Integer type,int page) throws MatchTypeErrorException {
        int length=64,fromIndex=page-1,toIndex=fromIndex+length;
        List<RecordVO> res=getGamesByUsername(username, type);
        if(fromIndex>=res.size()){
            return new ArrayList<>();
        }
        return res.subList(fromIndex, Math.min(toIndex, res.size()));
    }

    @Override
    public List<RecordVO> getGamesByGameId(Integer gameId) throws GameNotFoundException {
        List<RecordVO> res=new ArrayList<>();
        Games game=gamesMapper.getGameById(gameId);
        if(game==null){
            throw new GameNotFoundException("这局游戏不存在");
        }
        List<Record> records=recordMapper.selectRecordsByGameId(gameId);
        for (Record record : records) {
            RecordVO recordVO=new RecordVO();
            String username=record.getUsername();
            Date gameTime=game.getGameTime();
            int kills=record.getKill();
            int deaths=record.getDeath();
            int assists=record.getAssist();
            double kda=(kills*1.0+assists*0.7)/(deaths!=0?deaths:1);
            long duration=game.getDuration();
            int type=game.getType();
            String typeString="";
            switch (type){
                case 1:typeString="1v1";break;
                case 2:typeString="大乱斗";break;
                default:break;
            }
            boolean isMVP=username.equals(game.getMvpPlayer());
            Integer portrait=userGameMapper.getPortrait(username);
            recordVO.setGameId(gameId);
            recordVO.setUsername(username);
            recordVO.setPortrait(portrait);
            recordVO.setGameTime(gameTime);
            recordVO.setKills(kills);
            recordVO.setDeaths(deaths);
            recordVO.setAssists(assists);
            recordVO.setKda(kda);
            recordVO.setScoreGain(record.getScoreGain());
            recordVO.setDuration(duration);
            recordVO.setType(typeString);
            recordVO.setMVP(isMVP);
            recordVO.setTakeDamage(record.getTakeDamage());
            recordVO.setTakenDamage(record.getTakenDamage());
            res.add(recordVO);
        }
        res.sort((o1, o2) -> {
            if(o1.getGameTime().after(o2.getGameTime())){
                return -1;
            }else if(o1.getGameTime().before(o2.getGameTime())){
                return 1;
            }else{
                return 0;
            }
        });
        return res;
    }

    @Override
    public List<RecordVO> getGamesByGameId(Integer gameId, int page) throws GameNotFoundException {
        int length=64,fromIndex=page-1,toIndex=fromIndex+length;
        List<RecordVO> res=getGamesByGameId(gameId);
        if(fromIndex>=res.size()){
            return new ArrayList<>();
        }
        return res.subList(fromIndex, Math.min(toIndex, res.size()));
    }

    @Override
    public List<RecordVO> getGamesByDate(Date date) {
        List<RecordVO> res=new ArrayList<>();
        List<Games> games=gamesMapper.getGamesByDate(date);
        games.forEach(game -> {
            List<Record> records=recordMapper.selectRecordsByGameId(game.getGameId());
            records.forEach(record -> {
                RecordVO recordVO=new RecordVO();
                String username=record.getUsername();
                Date gameTime=game.getGameTime();
                int kills=record.getKill();
                int deaths=record.getDeath();
                int assists=record.getAssist();
                double kda=(kills*1.0+assists*0.7)/(deaths!=0?deaths:1);
                long duration=game.getDuration();
                int type=game.getType();
                String typeString="";
                switch (type){
                    case 1:typeString="1v1";break;
                    case 2:typeString="大乱斗";break;
                    default:break;
                }
                boolean isMVP=username.equals(game.getMvpPlayer());
                Integer portrait=userGameMapper.getPortrait(username);
                recordVO.setGameId(game.getGameId());
                recordVO.setUsername(username);
                recordVO.setPortrait(portrait);
                recordVO.setGameTime(gameTime);
                recordVO.setKills(kills);
                recordVO.setDeaths(deaths);
                recordVO.setAssists(assists);
                recordVO.setKda(kda);
                recordVO.setScoreGain(record.getScoreGain());
                recordVO.setDuration(duration);
                recordVO.setType(typeString);
                recordVO.setMVP(isMVP);
                recordVO.setTakeDamage(record.getTakeDamage());
                recordVO.setTakenDamage(record.getTakenDamage());
                res.add(recordVO);
            });
        });
        res.sort((o1, o2) -> {
            if(o1.getGameTime().after(o2.getGameTime())){
                return -1;
            }else if(o1.getGameTime().before(o2.getGameTime())){
                return 1;
            }else{
                return 0;
            }
        });
        return res;
    }

}
