package com.kary.hahaha3.mapper;

import com.kary.hahaha3.pojo.Games;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

/**
 * @author:123
 */
@Repository
@Mapper
@Tag(name = "GamesMapper")
public interface GamesMapper {
    Games getGameByIdAndType(@Param("gameId")Integer gameId,@Param("type")Integer type);
    Games getGameById(@Param("gameId")Integer gameId);
    Games getThisMonthGameById(@Param("gameId")Integer gameId);
    Integer getMaxGameId();
Integer addNewGame(@Param("type")Integer type,
                       @Param("gameId")Integer gameId,
                       @Param("duration")Long duration,
                       @Param("mvpPlayer")String mvpPlayer);

}
