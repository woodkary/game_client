package com.kary.hahaha3.config;

import com.kary.hahaha3.pojo.User;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.HandlerInterceptor;

/**
 * @author:123
 */
@Configuration
public class LoginInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        User myAccount= (User) request.getSession().getAttribute("myAccount");
        if(myAccount==null){
            response.sendRedirect("/pages/login.html");
        }
        return true;
    }
}
