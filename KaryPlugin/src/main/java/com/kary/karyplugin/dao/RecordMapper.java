package com.kary.karyplugin.dao;

import com.kary.karyplugin.pojo.UserGame;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

/**
 * @author:123
 */
public interface RecordMapper {
    @Insert("insert into record(game_id," +
            "                   username," +
            "                   kill," +
            "                   death," +
            "                   assist," +
            "                   score_gain," +
            "                   take_damage," +
            "                   taken_damage" +
            "        )" +
            "        values (" +
            "                #{game_id}," +
            "                #{username}," +
            "                #{kill}," +
            "                #{death}," +
            "                #{assist}," +
            "                #{scoreGain}," +
            "                #{takeDamage}," +
            "                #{takenDamage}" +
            "        )")
    void addNewRecord(@Param("game_id")Integer gameId,
                      @Param("username")String username,
                      @Param("kill")Integer kill,
                      @Param("death")Integer death,
                      @Param("assist")Integer assist,
                      @Param("scoreGain")Integer scoreGain,
                      @Param("takeDamage")Double takeDamage,
                      @Param("takenDamage")Double takenDamage);
    @Select("select" +
            "            <if test='gameMode == 1'>" +
            "                score_total_1v1" +
            "            </if>" +
            "            <if test='gameMode == 2'>" +
            "                score_total_brawl" +
            "            </if>" +
            "        from user_game where username=#{username}")
    Integer getScoreTotal(@Param("username")String username,@Param("gameMode")Integer gameMode);
    @Update("update user_game set" +
            "        <if test='gameMode == 1'>" +
            "            score_total_1v1=score_total_1v1+#{addNum}" +
            "        </if>" +
            "        <if test='gameMode == 2'>" +
            "            score_total_brawl=score_total_brawl+#{addNum}" +
            "        </if>" +
            "        where username=#{username}")
    void addScore(@Param("username")String username,@Param("gameMode")Integer gameMode,@Param("addNum")Integer addNum);
    @Update("update user_game set games_count=games_count+1 where username=#{username}")
    void addGamesCount(@Param("username")String username);
    @Update("update user_game set games_count_1v1=games_count_1v1+1 where username=#{username}")
    void addGamesCount1v1(@Param("username")String username);
    @Update("update user_game set games_count_drawl=games_count_drawl+1 where username=#{username}")
    void addGamesCountDrawl(@Param("username")String username);
    @Select("select * from user_game where username=#{username}")
    UserGame selectUserByName(@Param(value = "username")String username);

}
