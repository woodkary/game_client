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
import java.util.Map;

/**
 * @author:123
 */
//todo 重新输入密码和输入邮箱放一起了，是否需要把他们分开？
@Controller
public class ForgetPasswordController {
    @Autowired
    @Qualifier("AESEncoder")
    private AESUtil aesEncoder;
    @Autowired
    private UserMapper userMapper;
    @PostMapping("/forgetPassword/sendMail")
    public String sendMail(@RequestParam(value = "username")String username,
                           @RequestParam(value = "password")String password,
                           @RequestParam(value = "retypePassword")String retypePassword,
                           @RequestParam(value = "email")String email,
                           HttpSession session,
                           Model model){
        String verificationCode=MailUtil.getRandom6Digit();
        try {
            session.setAttribute("verificationCode",verificationCode);
            MailUtil.sendMail((String) session.getAttribute("email"),verificationCode,"重设密码");
            model.addAttribute("username",username);
            model.addAttribute("password",password);
            model.addAttribute("retypePassword",retypePassword);
            model.addAttribute("email",email);
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
            return "views/forgetPassword";
        }else if(password==null){
            model.addAttribute("showPopup","密码为空");
            return "views/forgetPassword";
        }else if(retypePassword==null){
            model.addAttribute("showPopup","请重输密码");
            return "views/forgetPassword";
        }else if(!password.equals(retypePassword)){
            model.addAttribute("showPopup","请输入一致的密码");
            return "views/forgetPassword";
        }else{
            User userInDatabase=userMapper.selectUserByName(username);
            if(userInDatabase==null){
                model.addAttribute("showPopup","该用户名不存在");
                return "views/forgetPassword";
            }else if(!MailUtil.legalQQMail(email)){
                model.addAttribute("showPopup","请输入合法的邮箱");
                return "views/forgetPassword";
            }else {
                model.addAttribute("username",username);
                model.addAttribute("password",password);
                model.addAttribute("email",email);
                model.addAttribute("retypePassword",retypePassword);
                session.setAttribute("username",username);
                session.setAttribute("password",password);
                session.setAttribute("email",email);
                return "views/forgetPassword";
            }
        }
    }
    @PostMapping("/forgetPassword/typeVeriCode2")
    public String typeVeriCode(@RequestParam(value = "veriCode")String veriCode,HttpSession session,Model model){
        String verificationCode= (String) session.getAttribute("verificationCode");
        if(verificationCode==null){
            model.addAttribute("showPopup","请先发验证码");
            return "views/forgetPassword";
        }else if(!verificationCode.equals(veriCode)){
            model.addAttribute("showPopup","验证码错误");
            return "views/forgetPassword";
        }else{
            String password=(String) session.getAttribute("password");
            password=aesEncoder.encrypt(password);
            userMapper.updateUserPassword((String) session.getAttribute("username"), password);
            //TODO 忘记设修改密码成功的页面了，所以跳到注册页面
            return "views/registerSuccess";
        }
    }
}
