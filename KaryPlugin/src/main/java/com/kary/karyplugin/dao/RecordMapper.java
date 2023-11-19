package com.kary.karyplugin.dao;

import org.apache.ibatis.annotations.Param;

/**
 * @author:123
 */
public interface RecordMapper {
    Integer getMaxGameId();
    void addNewRecord(@Param("game_id")Integer gameId,
                      @Param("duration")Long duration,
                      @Param("username_play1")String usernamePlay1,
                      @Param("username_play2")String usernamePlay2,
                      @Param("kill_play1")Integer killPlay1,
                      @Param("kill_play2")Integer killPlay2,
                      @Param("death_play1")Integer deathPlay1,
                      @Param("death_play2")Integer deathPlay2,
                      @Param("score_gain_play1")Integer scoreGainPlay1,
                      @Param("score_gain_play2")Integer scoreGainPlay2,
                      @Param("score_total_play1")Integer scoreTotalPlay1,
                      @Param("score_total_play2")Integer scoreTotalPlay2);
    void addUserNewGameId(@Param("username")String username,@Param("newGameId")String newGameId);
    Integer getScoreTotal(@Param("username")String username);
    void addScore(@Param("username")String username,@Param("addNum")Integer addNum);

}
