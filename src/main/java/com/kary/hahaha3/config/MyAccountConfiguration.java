package com.kary.hahaha3.config;

import com.kary.hahaha3.pojo.User;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


/**
 * @author:123
 */
@Configuration
public class MyAccountConfiguration {
    @Bean("myAccount")
    public User getMyAccount(){
        return new User();
    }
    @Bean("otherAccount")
    public User getOtherAccount() { return new User();}
}
