package com.kary.karyplugin.service.impl;

import com.kary.karyplugin.dao.GamesMapper;
import com.kary.karyplugin.dao.RecordMapper;
import com.kary.karyplugin.dao.SqlSessionSettings;
import com.kary.karyplugin.pojo.User;
import com.kary.karyplugin.service.RecordService;
import com.mysql.cj.Session;
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
        /*session=SqlSessionSettings.getSqlSession();
        recordMapper=session.getMapper(RecordMapper.class);
        gamesMapper=session.getMapper(GamesMapper.class);*/
        recordMapper=new RecordMapper() {
            @Override
            public void addNewRecord(Integer gameId, String username, Integer kill, Integer death, Integer assist) {
                Bukkit.getServer().broadcastMessage("新的比赛为："+gameId+","+username+","+kill+","+death+","+assist);
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
            public User selectUserByName(String username) {
                return new User(username,"114514",0,0,"834479572@qq.com",new Date(),0);
            }
        };
    }

    @Override
    public void recordNewMatch(Long duration,
                               String username,
                               Integer kill,
                               Integer death,
                               Integer gameMode,
                               Integer assists) {
        //TODO 应该是gameMapper
        Integer maxGameId=gamesMapper.getMaxGameId();
        maxGameId+=1;
        recordMapper.addNewRecord(maxGameId,username,kill,death,(gameMode==1)?0:assists);
        gamesMapper.addNewGame(gameMode,maxGameId,duration);
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
    public User selectUserByName(String username) {
        return recordMapper.selectUserByName(username);
    }
}
