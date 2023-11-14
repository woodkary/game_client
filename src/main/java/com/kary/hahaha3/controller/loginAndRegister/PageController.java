package com.kary.hahaha3.controller.loginAndRegister;

import com.kary.hahaha3.mapper.UserMapper;
import com.kary.hahaha3.pojo.User;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author:123
 */
@RestController
public class PageController {
    @Autowired
    private UserMapper userMapper;
    @GetMapping("/page{x}")
    @Operation(summary = "根据页数，对数据库分页查询",description = "limit select")
    public String pageX(HttpServletRequest request){
        String value= request.getRequestURI();
        int x=Integer.parseInt(value.substring(5))-1;
        List<User> users=userMapper.selectUserLimit(x*3);
        StringBuilder str=new StringBuilder();
        for (User user : users) {
            str.append(user.toString()+System.lineSeparator());
        }
        return str.toString();
    }
}
