package com.kary.karyplugin.dao;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.jdbc.SQL;

/**
 * @author:123
 */
public class RecordMapperProvider {
    public String addScore(String username, Integer gameMode, Integer addNum){
        return new SQL(){{
            UPDATE("user_game");
            if (gameMode == 1){
                SET("score_total_1v1=score_total_1v1+#{addNum}");
            }
            if (gameMode == 2){
                SET("score_total_brawl=score_total_brawl+#{addNum}");
            }
        }}.toString();
    }
    public String getScoreTotal(String username, Integer gameMode){
        return new SQL(){{
            if(gameMode==1){
                SELECT("score_total_1v1");
            }
            if(gameMode==2){
                SELECT("score_total_brawl");
            }
            FROM("user_game");
            WHERE("username=#{username}");
        }}.toString();
    }
}
