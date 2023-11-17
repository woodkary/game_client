package com.kary.hahaha3.config;

import com.kary.hahaha3.pojo.User;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author:123
 */
@Configuration
public class AccountConfiguration {
    @Bean
    @Qualifier("myAccount")
    public User myAccount(){
        return new User();
    }
    @Bean
    @Qualifier("otherAccount")
    public User otherAccount(){
        return new User();
    }
}
