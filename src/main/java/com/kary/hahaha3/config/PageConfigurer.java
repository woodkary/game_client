package com.kary.hahaha3.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @author:123
 */
@Configuration
public class PageConfigurer implements WebMvcConfigurer {
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new LoginInterceptor())
                .addPathPatterns("/views/**","/level{m}/{n}","/","/index")
                .excludePathPatterns("/views/login.html","/views/register.html","/views/forgetPassword.html");
    }
}