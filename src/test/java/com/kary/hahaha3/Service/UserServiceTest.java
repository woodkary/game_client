package com.kary.hahaha3.Service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.kary.hahaha3.mapper.UserArticleMapper;
import com.kary.hahaha3.mapper.UserGameMapper;
import com.kary.hahaha3.mapper.UserMapper;
import com.kary.hahaha3.pojo.UserGame;
import com.kary.hahaha3.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.when;

/**
 * @author karywoodOyo
 */
@SpringBootTest
public class UserServiceTest {
    @MockBean
    private UserMapper userMapper;
    @MockBean
    private UserArticleMapper userArticleMapper;
    @MockBean
    private UserGameMapper userGameMapper;
    @Autowired
    @Qualifier("UserService")
    UserService userService;
    @Test
    public void test(){
        when(userGameMapper.selectList(new QueryWrapper<UserGame>().orderByDesc("score_total_1v1+score_total_brawl")))
                .thenReturn(new ArrayList<>(List.of(new UserGame().setScoreTotal1v1(100).setScoreTotalBrawl(200),new UserGame().setScoreTotal1v1(300).setScoreTotalBrawl(400))));
        userService.getAllUserOrderTotalScore().forEach(System.out::println);
    }
}
