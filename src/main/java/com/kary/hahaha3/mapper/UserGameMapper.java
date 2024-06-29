package com.kary.hahaha3.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.kary.hahaha3.pojo.UserGame;
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
@Tag(name = "UserGameMapper")
public interface UserGameMapper extends BaseMapper<UserGame> {
    Integer insertUser(@Param(value = "username")String username);
    Integer deleteUser(@Param(value = "username")String username);
    Integer updatePortrait(@Param(value = "username")String username,@Param("portrait")Integer portrait);
    Integer getPortrait(@Param(value = "username")String username);
    Integer getScoreByType(@Param(value = "username")String username,
                           @Param(value = "type")Integer type);
    Integer addScore(@Param(value = "username")String username,
                           @Param(value = "type")Integer type,
                           @Param(value = "addScore")Integer addScore);
    Integer updateMaxScore(@Param(value = "username")String username,
                        @Param(value = "type")Integer type);
    UserGame getUserGame(@Param(value = "username")String username);
    List<UserGame> getAllUserOrder1v1();
    List<UserGame> getAllUserOrderBrawl();
    List<UserGame> getAllUserOrderTotalScore();
}
