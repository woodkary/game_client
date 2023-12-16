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
import org.springframework.transaction.annotation.Transactional;

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
    public Boolean emailIsRegistered(String email) {
        List<User> u = userMapper.selectUserByEmail(email); //所有以该email为email注册的User对象大于1位
        return !u.isEmpty();
    }

    @Override
    public Integer updateUserPortrait(String username, Integer portrait) {
        return userGameMapper.updatePortrait(username,portrait);
    }

    @Override
    @Transactional
    public Integer insertUser(String username, String pwd, String email) {
        Integer num1,num2,num3;
        num1= userMapper.insertUser(username,pwd,email);
        num2 = userArticleMapper.insertUser(username);
        num3 = userGameMapper.insertUser(username);

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
    @Transactional
    public Integer updateUserPassword(String username, String pwd) {
        return userMapper.updateUserPassword(username,pwd);
    }
}
