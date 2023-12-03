package com.kary.hahaha3;

import com.kary.hahaha3.utils.AESUtil;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class HaHaHa3ApplicationTests {
    @Autowired
    @Qualifier("AESEncoder")
    private AESUtil aesUtil;
    @Test
    public void test1(){
        System.out.println(aesUtil.decrypt("I1IEvx5lvhh5nuoXlmUU1hGEHlBN1bYeiGW/K4wt2OI="));
    }
}
