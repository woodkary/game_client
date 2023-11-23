package com.kary.karyplugin.dao;

import org.apache.ibatis.annotations.Param;

/**
 * @author:123
 */
public interface GamesMapper {
    Integer getMaxGameId();
    void addNewGame(@Param("type")Integer type,
                    @Param("gameId")Integer gameId,
                    @Param("duration")Long duration);
}
