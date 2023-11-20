package com.kary.hahaha3.mapper;

import com.kary.hahaha3.pojo.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import com.kary.hahaha3.mapper.UserMapper;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class UserMapperTest {
    @Qualifier("myAccount")
    private User user;
    @Autowired
    private UserMapper userMapper;
    @Test
    public void selectUserByName(){
        user = new User();
        user.setUsername("test_name");
        user.setPwd("test_pwd");
        user.setEmail("test_email");
        assertEquals(user.getEmail(),userMapper.selectUserByName(user.getUsername()).getEmail());
    }
    @Test
    public void insertUser(){
        user = new User();
        user.setUsername("test1_name");
        user.setPwd("test1_pwd");
        user.setEmail("test1_email");
        userMapper.insertUser(user.getUsername(),user.getPwd(),user.getEmail());
    }
    @Test
    public void updateUserPassword(){
        user = new User();
        user.setUsername("test1_name");
        user.setPwd("test2_pwd");
        user.setEmail("test1_email");
        userMapper.updateUserPassword(user.getUsername(),user.getPwd());
    }
}
