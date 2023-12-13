package com.kary.karyplugin.dao;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

/**
 * @author:123
 */
public interface GamesMapper {
    @Select("select max(game_id) from games")
    Integer getMaxGameId();
    @Insert("insert into games (type,game_id,game_time,duration,mvp_player) values (#{type},#{gameId},now(),#{duration},#{mvpPlayer})")
    Integer addNewGame(@Param("type")Integer type,
                    @Param("gameId")Integer gameId,
                    @Param("duration")Long duration,
                    @Param("mvpPlayer")String mvpPlayer);
}
