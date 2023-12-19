package com.kary.karyplugin.dao;

import com.kary.karyplugin.pojo.UserGame;
import org.apache.ibatis.annotations.*;

/**
 * @author:123
 */
public interface RecordMapper {
    @Insert("insert into record (game_id," +
            "username," +
            "`kill`," +
            "death," +
            "assist," +
            "score_gain," +
            "take_damage," +
            "taken_damage" +
            ") " +
            "values (" +
            "#{gameId}," +
            "#{username}," +
            "#{kill}," +
            "#{death}," +
            "#{assist}," +
            "#{scoreGain}," +
            "#{takeDamage}," +
            "#{takenDamage}" +
            ")")
    void addNewRecord(@Param("gameId")Integer gameId,
                      @Param("username")String username,
                      @Param("kill")Integer kill,
                      @Param("death")Integer death,
                      @Param("assist")Integer assist,
                      @Param("scoreGain")Integer scoreGain,
                      @Param("takeDamage")Double takeDamage,
                      @Param("takenDamage")Double takenDamage);
    @SelectProvider(type = RecordMapperProvider.class, method = "getScoreTotal")
    Integer getScoreTotal(@Param("username")String username,@Param("gameMode")Integer gameMode);
    @UpdateProvider(type = RecordMapperProvider.class, method = "addScore")
    void addScore(@Param("username")String username,@Param("gameMode")Integer gameMode,@Param("addNum")Integer addNum);
    @Update("update user_game set games_count=games_count+1 where username=#{username}")
    void addGamesCount(@Param("username")String username);
    @Update("update user_game set games_count_1v1=games_count_1v1+1 where username=#{username}")
    void addGamesCount1v1(@Param("username")String username);
    @Update("update user_game set games_count_brawl=games_count_brawl+1 where username=#{username}")
    void addGamesCountBrawl(@Param("username")String username);
    @Select("select * from user_game where username=#{username}")
    UserGame selectUserByName(@Param(value = "username")String username);

}
