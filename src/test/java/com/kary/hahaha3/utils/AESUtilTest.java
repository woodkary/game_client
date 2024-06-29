package com.kary.hahaha3.utils;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class AESUtilTest {
    @Autowired
    @Qualifier("AESEncoder")
    AESUtil aesUtil;
    @Test
    void test(){
        String pwd = "123456";
        String encpwd = aesUtil.encrypt(pwd);
        assertEquals(pwd,aesUtil.decrypt(encpwd));
    }
}
