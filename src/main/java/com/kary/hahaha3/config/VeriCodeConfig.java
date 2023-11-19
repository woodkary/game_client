package com.kary.hahaha3.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author:123
 */
@Configuration
public class VeriCodeConfig {
    @Bean("userVeriCodeRegister")
    public Map<String,String> getUserVeriCodeRegister(){
        return new ConcurrentHashMap<>();
    }
    @Bean("userVeriCodeResetPassword")
    public Map<String,String> getUserVeriCodeResetPassword(){
        return new ConcurrentHashMap<>();
    }
}
