package com.kary.hahaha3.controller.loginAndRegister;

import com.kary.hahaha3.mapper.UserMapper;
import com.kary.hahaha3.utils.MailUtil;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.mail.MessagingException;
import java.util.Map;


@Controller
public class EmailVerificationController {
    @Autowired
    private UserMapper userMapper;
    //mode有username password email,现在要得到code
    //输入授权码
    //准备发送验证码
    @PostMapping("/sendVeriCode")
    @Operation(summary = "发送验证码")
    public String sendVeriCode(Model model,HttpSession session){
        //先生成验证码
        String verificationCode=MailUtil.getRandom6Digit();
        session.setAttribute("verificationCode",verificationCode);
        //发送验证码
        try {
            MailUtil.sendMail(MailUtil.getToEmail(),verificationCode,"验证码");
        } catch (MessagingException e) {
            model.addAttribute("showPopup","网络错误，请重发");
        }
        return "views/emailVerification";
    }
    @PostMapping("/typeVeriCodeToRegister")
    @Operation(summary = "验证发送的验证码")
    public String typeVeriCodeToRegister(@RequestParam(value="veriCode")String veriCode, HttpSession session,Model model){
        String verificationCode= (String) session.getAttribute("verificationCode");
        if(verificationCode.equals(veriCode)){
            userMapper.insertUser((String) session.getAttribute("username"), (String) session.getAttribute("password"), (String) session.getAttribute("email"));
            return "views/registerSuccess";
        }else{
            model.addAttribute("showPopup","验证码错误，请重试");
            return "views/emailVerification";
        }
    }
}
