package com.kary.karyplugin.dao;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.jdbc.SQL;

/**
 * @author:123
 */
public class RecordMapperProvider {
    public String addScore(@Param("username")String username,@Param("gameMode")Integer gameMode,@Param("addNum")Integer addNum){
        return new SQL(){{
            UPDATE("user_game");
            if (gameMode == 1){
                SET("score_total_1v1=score_total_1v1+#{addNum}");
            }
            if (gameMode == 2){
                SET("score_total_brawl=score_total_brawl+#{addNum}");
            }
            WHERE("username=#{username}");
        }}.toString();
    }
    public String getScoreTotal(@Param("username")String username,@Param("gameMode")Integer gameMode){
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
    public String updateMaxScore(@Param("username")String username,@Param("gameMode")Integer gameMode){
        return new SQL(){{
            UPDATE("user_game");
            if (gameMode == 1){
                SET("max_score_1v1=score_total_1v1");
            }
            if (gameMode == 2){
                SET("max_score_brawl=score_total_brawl");
            }
            WHERE("username=#{username}");
        }}.toString();
    }
}
