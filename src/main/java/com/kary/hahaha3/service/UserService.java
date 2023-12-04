package com.kary.hahaha3.service;

import com.kary.hahaha3.exceptions.connection.DatabaseConnectionException;
import com.kary.hahaha3.pojo.User;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author:123
 */
public interface UserService {
    User selectUserByName(String username);
    Integer insertUser(String username,String pwd,String email);
    List<User> selectUserLimit(int x);
    Integer updateUserPassword(String username,String pwd);
    Boolean emailIsRegistered(String email);
}
