package com.kary.karyplugin.dao;

import com.kary.karyplugin.pojo.UserGame;
import org.apache.ibatis.annotations.Param;

/**
 * @author:123
 */
public interface RecordMapper {
    void addNewRecord(@Param("game_id")Integer gameId,
                      @Param("username")String username,
                      @Param("kill")Integer kill,
                      @Param("death")Integer death,
                      @Param("assist")Integer assist);
    Integer getScoreTotal(@Param("username")String username,@Param("gameMode")Integer gameMode);
    void addScore(@Param("username")String username,@Param("gameMode")Integer gameMode,@Param("addNum")Integer addNum);
    void addGamesCount(@Param("username")String username);
    void addGamesCount1v1(@Param("username")String username);
    void addGamesCountDrawl(@Param("username")String username);
    UserGame selectUserByName(@Param(value = "username")String username);

}
