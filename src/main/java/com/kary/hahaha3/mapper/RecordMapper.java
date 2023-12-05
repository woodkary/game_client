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
}
