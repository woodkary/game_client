package com.kary.hahaha3.mapper;

import com.kary.hahaha3.pojo.User;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author:123
 */
@Repository
@Mapper
@Tag(name = "UserMapper")
public interface UserMapper {
    User selectUserByName(@Param(value = "username")String username);
    void insertUser(@Param(value = "username")String username,@Param(value = "pwd")String pwd,@Param(value = "email")String email);
    List<User> selectUserLimit(@Param(value = "x")int x);
    void updateUserPassword(@Param(value = "username")String username,@Param(value="pwd")String pwd);
    void updateUserAvatar(@Param(value = "username")String username,@Param(value="avatar")String avatar);
}
