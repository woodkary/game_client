package com.kary.hahaha3.controller.loginAndRegister;

import com.kary.hahaha3.mapper.UserMapper;
import com.kary.hahaha3.utils.MailUtil;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.mail.MessagingException;


@Controller
public class EmailVerificationController {
    @Autowired
    private UserMapper userMapper;
    private String verificationCode;
    //mode有username password email,现在要得到code
    //输入授权码
    @PostMapping("/getAuthorCode")
    @Operation(summary = "输入授权码")
    public String getAuthorCode(@RequestParam(value="authorCode")String authorCode, HttpSession session,Model model){
        if(MailUtil.getAuthorCode()==null){
            MailUtil.setAuthorCode(authorCode);
            session.setAttribute("authorCode",authorCode);
            model.addAttribute("authorCode",authorCode);
        }
        return "views/emailVerification";
    }
    //准备发送验证码
    @PostMapping("/sendVeriCode")
    @Operation(summary = "发送验证码")
    public String sendVeriCode(Model model){
        //先生成验证码
        verificationCode=MailUtil.getRandom6Digit();
        //发送验证码
        try {
            MailUtil.sendMail(MailUtil.getToEmail(),verificationCode,"验证码");
            model.addAttribute("authorCode",model.getAttribute("authorCode"));
        } catch (MessagingException e) {
            model.addAttribute("showPopup","网络错误，请重发");
        }
        return "views/emailVerification";
    }
    @PostMapping("/typeVeriCodeToRegister")
    @Operation(summary = "验证发送的验证码")
    public String typeVeriCodeToRegister(@RequestParam(value="veriCode")String veriCode, HttpSession session){
        if(verificationCode.equals(veriCode)){
            userMapper.insertUser((String) session.getAttribute("username"), (String) session.getAttribute("password"), (String) session.getAttribute("email"),MailUtil.getAuthorCode());
            return "views/registerSuccess";
        }else{
            return "views/emailVerification";
        }
    }
}
