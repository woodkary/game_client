package com.kary.hahaha3.mapper;

import io.swagger.v3.oas.annotations.tags.Tag;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

/**
 * @author:123
 */
@Repository
@Mapper
@Tag(name = "UserArticleMapper")
public interface UserArticleMapper {
    Integer insertUser(@Param(value = "username")String username);
    Integer deleteUser(@Param(value = "username")String username);
}
