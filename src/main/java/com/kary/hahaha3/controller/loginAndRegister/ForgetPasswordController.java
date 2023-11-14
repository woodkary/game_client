package com.kary.hahaha3.controller.loginAndRegister;

import com.kary.hahaha3.mapper.UserMapper;
import com.kary.hahaha3.pojo.User;
import com.kary.hahaha3.utils.AESUtil;
import com.kary.hahaha3.utils.MailUtil;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.mail.MessagingException;

/**
 * @author:123
 */
@Controller
public class ForgetPasswordController {
    @Autowired
    @Qualifier("AESEncoder")
    private AESUtil aesEncoder;
    @Autowired
    private UserMapper userMapper;
    private String verificationCode;
    @PostMapping("/forgetPassword/sendMail")
    public String sendMail(Model model,HttpSession session){
        verificationCode=MailUtil.getRandom6Digit();
        try {
            MailUtil.sendMail((String) session.getAttribute("email"),verificationCode,"重设密码");
            model.addAttribute("username",model.getAttribute("username"));
            model.addAttribute("password",model.getAttribute("password"));
            model.addAttribute("email",model.getAttribute("email"));
            return "views/forgetPassword";
        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
    }
    @GetMapping("/forgetPassword")
    public String forgetPassword(){
        return "/views/forgetPassword";
    }
    @PostMapping("/usr/resetPassword")
    @Operation(summary = "重置密码",description = "reset password")
    public String resetPassword(@RequestParam(value = "username")String username,
                                @RequestParam(value = "password")String password,
                                @RequestParam(value = "retypePassword")String retypePassword,
                                @RequestParam(value = "email")String email,
                                HttpSession session,
                                Model model) {
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
        }else{
            User userInDatabase=userMapper.selectUserByName(username);
            if(userInDatabase==null){
                model.addAttribute("showPopup","该用户名不存在");
                return "index";
            }else if(!MailUtil.legalQQMail(email)){
                model.addAttribute("showPopup","请输入合法的邮箱");
                return "index";
            }else {
                MailUtil.setAuthorCode(userInDatabase.getCode());
                model.addAttribute("username",username);
                model.addAttribute("password",password);
                model.addAttribute("email",email);
                session.setAttribute("username",username);
                session.setAttribute("password",password);
                return "views/forgetPassword";
            }
        }
    }
    @PostMapping("/forgetPassword/typeVeriCode")
    public String typeVeriCode(@RequestParam(value = "veriCode")String veriCode,HttpSession session,Model model){
        if(verificationCode==null){
            model.addAttribute("showPopup","请先发验证码");
            return "views/forgetPassword";
        }else if(!verificationCode.equals(veriCode)){
            model.addAttribute("showPopup","验证码错误");
            return "views/forgetPassword";
        }else{
            userMapper.updateUserPassword((String) session.getAttribute("username"), (String) session.getAttribute("password"));
            return "views/registerSuccess";
        }
    }
}
