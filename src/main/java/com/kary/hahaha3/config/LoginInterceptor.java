package com.kary.hahaha3.config;

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
        Object user=request.getSession().getAttribute("myAccount");
        if(user==null){
            String showPopup= "您尚未登录";
            request.setAttribute("showPopup",showPopup);
            request.getRequestDispatcher("/").forward(request,response);
            return false;
        }
        return true;
    }
}
