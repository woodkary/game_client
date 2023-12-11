package com.kary.karyplugin.service.impl;

import com.kary.karyplugin.dao.GamesMapper;
import com.kary.karyplugin.dao.RecordMapper;
import com.kary.karyplugin.dao.SqlSessionSettings;
import com.kary.karyplugin.pojo.UserGame;
import com.kary.karyplugin.service.RecordService;
import org.apache.ibatis.session.SqlSession;
import org.bukkit.Bukkit;

import java.util.Date;

/**
 * @author:123
 */
public class RecordServiceImpl implements RecordService {
    SqlSession session;
    RecordMapper recordMapper;
    GamesMapper gamesMapper;

    public RecordServiceImpl() {
        session= SqlSessionSettings.getSqlSession();
        recordMapper=session.getMapper(RecordMapper.class);
        gamesMapper=session.getMapper(GamesMapper.class);
        /*recordMapper=new RecordMapper() {
            @Override
            public void addNewRecord(Integer gameId, String username, Integer kill, Integer death, Integer assist) {
                Bukkit.getServer().broadcastMessage("新的比赛为："+gameId+","+"玩家"+username+","+"击杀"+kill+","+"死亡"+death+","+"助攻"+assist);
            }

            @Override
            public Integer getScoreTotal(String username, Integer gameMode) {
                return (int) (Math.random()*100);
            }

            @Override
            public void addScore(String username, Integer gameMode, Integer addNum) {
                Bukkit.getServer().broadcastMessage(username+"加分:"+addNum);
            }

            @Override
            public void addGamesCount(String username) {
                Bukkit.getServer().broadcastMessage(username+"新增加了一场比赛");
            }

            @Override
            public void addGamesCount1v1(String username) {

            }

            @Override
            public void addGamesCountDrawl(String username) {

            }

            @Override
            public UserGame selectUserByName(String username) {
                return new UserGame(username,0,0,0);
            }
        };
        gamesMapper=new GamesMapper() {
            @Override
            public Integer getMaxGameId() {
                return 100;
            }

            @Override
            public void addNewGame(Integer type, Integer gameId, Long duration,String mvpPlayer) {
                Bukkit.getServer().broadcastMessage("加入新游戏:类型："+type+",游戏id"+gameId+",时长"+duration+",MVP"+mvpPlayer);
            }
        };*/
    }

    @Override
    public void recordNewMatch(Long duration,
                               String username,
                               Integer kill,
                               Integer death,
                               Integer gameMode,
                               Integer assists,
                               String mvpPlayer) {
        Integer maxGameId=gamesMapper.getMaxGameId();
        maxGameId+=1;
        recordMapper.addNewRecord(maxGameId,username,kill,death,(gameMode==1)?0:assists);
        recordMapper.addGamesCount(username);
        if(gameMode==1){
            recordMapper.addGamesCount1v1(username);
        }
        if(gameMode==2){
            recordMapper.addGamesCountDrawl(username);
        }
        gamesMapper.addNewGame(gameMode,maxGameId,duration,mvpPlayer);
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
