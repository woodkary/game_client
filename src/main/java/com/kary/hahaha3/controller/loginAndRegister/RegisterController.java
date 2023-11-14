package com.kary.hahaha3.controller.loginAndRegister;

import com.kary.hahaha3.mapper.UserMapper;
import com.kary.hahaha3.utils.AESUtil;
import com.kary.hahaha3.utils.MailUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @author:123
 */
@Controller
//注册邮箱仅支持qq邮箱
public class RegisterController {
    @Autowired
    @Qualifier("AESEncoder")
    AESUtil aesEncoder;
    @Autowired
    UserMapper userMapper;
    @RequestMapping("/register")
    public String register(){
        return "views/register";
    }
    @PostMapping("/usr/register")
    @Operation(summary = "用户注册",description = "user register")
    public String userRegister(@RequestParam(value = "username")String username,
                               @RequestParam(value = "password")String password,
                               @RequestParam(value = "retypePassword")String retypePassword,
                               @RequestParam(value = "email")String email,
                               Model model, HttpSession session){
        if(username==null){
            model.addAttribute("showPopup","用户名为空");
            return "index";
        }else if(password==null){
            model.addAttribute("showPopup","密码为空");
            return "index";
        }else if(retypePassword==null){
            model.addAttribute("showPopup","请重输密码");
            return "index";
        }else if(!password.equals(retypePassword)){
            model.addAttribute("showPopup","请输入一致的密码");
            return "index";
        }else if (userMapper.selectUserByName(username)!=null) {
            model.addAttribute("showPopup","该用户已注册");
            return "index";
        }else if(!MailUtil.legalQQMail(email)){
            model.addAttribute("showPopup","请输入合法的邮箱");
            return "index";
        }else{
                password=aesEncoder.encrypt(password);
                session.setAttribute("username",username);
                session.setAttribute("password",password);
                session.setAttribute("email",email);
                MailUtil.setToEmail(email);
                return "views/emailVerification";
        }
    }
}

