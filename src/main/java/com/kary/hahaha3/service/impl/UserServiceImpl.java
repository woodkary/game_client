package com.kary.hahaha3.service.impl;

import com.kary.hahaha3.exceptions.connection.DatabaseConnectionException;
import com.kary.hahaha3.mapper.UserArticleMapper;
import com.kary.hahaha3.mapper.UserGameMapper;
import com.kary.hahaha3.mapper.UserMapper;
import com.kary.hahaha3.pojo.User;
import com.kary.hahaha3.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author:123
 */
@Service
@Qualifier("UserService")
public class UserServiceImpl implements UserService {
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private UserArticleMapper userArticleMapper;
    @Autowired
    private UserGameMapper userGameMapper;
    @Override
    public User selectUserByName(String username) {
        return userMapper.selectUserByName(username);
    }

    @Override
    public Integer insertUser(String username, String pwd, String email) throws DatabaseConnectionException {
        Integer num1,num2,num3;
        try{
            num1= userMapper.insertUser(username,pwd,email);
        }catch (Exception e){
            userMapper.deleteUser(username);
            throw new DatabaseConnectionException(e);
        }
        try {
            num2 = userArticleMapper.insertUser(username);
        }catch (Exception e){
            userArticleMapper.deleteUser(username);
            throw new DatabaseConnectionException(e);
        }
        try {
            num3 = userGameMapper.insertUser(username);
        }catch (Exception e){
            userGameMapper.deleteUser(username);
            throw new DatabaseConnectionException(e);
        }
        if(num1==1&&num2==1&&num3==1){
            return 1;
        }else{
            return 0;
        }
    }

    @Override
    public List<User> selectUserLimit(int x) {
        return userMapper.selectUserLimit(x);
    }

    @Override
    public Integer updateUserPassword(String username, String pwd) {
        return userMapper.updateUserPassword(username,pwd);
    }
}
