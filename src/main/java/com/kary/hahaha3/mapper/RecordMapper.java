package com.kary.hahaha3.mapper;

import com.kary.hahaha3.pojo.Record;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author:123
 */
@Repository
@Mapper
@Tag(name = "RecordMapper")
public interface RecordMapper {
    List<Record> selectRecordsByUsername(@Param("username")String username);
    List<Record> selectRecordsByGameId(@Param("gameId")Integer gameId);
    Integer addNewRecord(@Param("gameId")Integer gameId,
                      @Param("username")String username,
                      @Param("kill")Integer kill,
                      @Param("death")Integer death,
                      @Param("assist")Integer assist,
                      @Param("scoreGain")Integer scoreGain,
                      @Param("takeDamage")Double takeDamage,
                      @Param("takenDamage")Double takenDamage);
    Integer addGamesCount(@Param("username")String username);
    Integer addGamesCount1v1(@Param("username")String username);
    Integer addGamesCountBrawl(@Param("username")String username);

}
