package com.kary.hahaha3;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@SpringBootApplication
@MapperScan("com.kary.hahaha3.mapper")
public class HaHaHa3Application {

    public static void main(String[] args) {
        SpringApplication.run(HaHaHa3Application.class, args);
    }

}
