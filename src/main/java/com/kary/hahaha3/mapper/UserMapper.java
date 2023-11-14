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
    void insertUser(@Param(value = "username")String username,@Param(value = "password")String password,@Param(value = "email")String email,@Param(value = "code")String code);
    List<User> selectUserLimit(@Param(value = "x")int x);
    void updateUserPassword(@Param(value = "username")String username,@Param(value="password")String password);
    List<User> selectUserByCode(@Param(value = "code")String code);

}
