package com.kary.hahaha3;

import com.kary.hahaha3.utils.AESUtil;
import com.kary.hahaha3.utils.OpenAIAPI;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

@SpringBootTest
public class HaHaHa3ApplicationTests {
    @Autowired
    @Qualifier("AESEncoder")
    private AESUtil aesUtil;

    @Test
    public void test1(){
        System.out.println(aesUtil.decrypt("I1IEvx5lvhh5nuoXlmUU1hGEHlBN1bYeiGW/K4wt2OI="));
    }
    @Test
    public void test2(){
        List<String> username_list = new ArrayList<>();
        // 获取数据库用户名
        username_list.add("Tony");
        username_list.add("Scott");
        username_list.add("Mary");
        username_list.add("Lucy");

        String txt = new String("L");
        System.out.println(OpenAIAPI.chat(username_list, txt));
    }

}
