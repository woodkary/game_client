package com.kary.hahaha3.controller.loginAndRegister;

import com.kary.hahaha3.exceptions.connection.DatabaseConnectionException;
import com.kary.hahaha3.exceptions.connection.VerificationCodeSendingException;
import com.kary.hahaha3.exceptions.emptyInput.VerificationCodeEmptyException;
import com.kary.hahaha3.exceptions.errorInput.VerificationCodeErrorException;
import com.kary.hahaha3.exceptions.expired.VerificationCodeExpireException;
import com.kary.hahaha3.mapper.UserMapper;
import com.kary.hahaha3.pojo.JsonResult;
import com.kary.hahaha3.pojo.User;
import com.kary.hahaha3.utils.AESUtil;
import com.kary.hahaha3.utils.MailUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.mail.MessagingException;


@RestController
@Tag(name = "邮箱验证")
public class EmailVerificationController {
    @Autowired
    @Qualifier("AESEncoder")
    private AESUtil aesEncoder;
    @Autowired
    private UserMapper userMapper;
    //mode有username password email,现在要得到code
    //输入授权码
    //准备发送验证码
    @PostMapping("/sendVeriCode")
    @Operation(summary = "发送验证码")
    public JsonResult sendVeriCode(HttpSession session) throws VerificationCodeSendingException {
        //先生成验证码
        String verificationCode=MailUtil.getRandom6Digit();
        session.setAttribute("verificationCode",verificationCode);
        //发送验证码
        try {
            MailUtil.sendMail((String) session.getAttribute("email"),verificationCode,"验证码");
        } catch (MessagingException e) {
            throw new VerificationCodeSendingException("发送验证码错误",e);
        }
        return JsonResult.ok("等待输入验证码");
    }
    @PostMapping("/typeVeriCode/{operation}")
    @Operation(summary = "验证发送的验证码")
    public JsonResult typeVeriCodeToRegister(@RequestParam(value="veriCode")String veriCode, @PathVariable Integer operation, HttpSession session) throws Exception {
        String verificationCode= (String) session.getAttribute("verificationCode");
        if(veriCode==null){
            throw new VerificationCodeEmptyException("请输入验证码");
        }
        if(verificationCode==null){
            throw new VerificationCodeExpireException("验证码过期，请重新发送");
        }
        if(verificationCode.equals(veriCode)){
            Integer flag=2;
            switch (operation){
                case 1:{//1是注册
                    flag=userMapper.insertUser((String) session.getAttribute("username"), (String) session.getAttribute("password"), (String) session.getAttribute("email"));
                    break;
                }
                case 2:{//2是改密码
                    String password=(String) session.getAttribute("password");
                    password=aesEncoder.encrypt(password);
                    flag=userMapper.updateUserPassword((String) session.getAttribute("username"), password);
                    break;
                }
            }
            if(flag>1){
                throw new DatabaseConnectionException("更新数据库出错");
            }else {
                return JsonResult.ok(1,"注册成功");
            }
        }else{
            throw new VerificationCodeErrorException("验证码错误，请重试");
        }
    }
}
