package com.kary.hahaha3.mapper;

import com.kary.hahaha3.pojo.Record;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

/**
 * @author:123
 */
@Repository
@Mapper
@Tag(name = "RecordMapper")
public interface RecordMapper {
    Record selectGameById(@Param("gameId")Integer gameId);
}
