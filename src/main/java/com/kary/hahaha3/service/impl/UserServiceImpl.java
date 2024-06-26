package com.kary.hahaha3.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.kary.hahaha3.exceptions.connection.DatabaseConnectionException;
import com.kary.hahaha3.mapper.UserArticleMapper;
import com.kary.hahaha3.mapper.UserGameMapper;
import com.kary.hahaha3.mapper.UserMapper;
import com.kary.hahaha3.pojo.User;
import com.kary.hahaha3.pojo.UserGame;
import com.kary.hahaha3.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
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
    public Integer addScore(String username, Integer type, Integer addScore) {
        Integer num1=userGameMapper.addScore(username,type,addScore);
        Integer num2=userGameMapper.updateMaxScore(username,type);
        return num1==1&&num2==1?1:0;
    }

    @Override
    @Transactional
    public Integer insertUser(String username, String pwd, String email) {
        Integer num1,num2,num3;
        //根据用户名、密码和邮箱插入用户
        num1= userMapper.insert(new User(username,pwd,email,new Date(),""));
        //根据用户名插入用户文章
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
    @Override
    public List<UserGame> getAllUserOrder1v1(){
        return userGameMapper.selectList(new QueryWrapper<UserGame>().orderByDesc("score_total_1v1"));
    }
    @Override
    public List<UserGame> getAllUserOrderBrawl(){
        return userGameMapper.selectList(new QueryWrapper<UserGame>().orderByDesc("score_total_brawl"));
    }
    @Override
    public List<UserGame> getAllUserOrderTotalScore(){
        return userGameMapper.selectList(new QueryWrapper<UserGame>().orderByDesc("score_total_1v1+score_total_brawl"));
    }
}
