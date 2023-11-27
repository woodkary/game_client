package com.kary.hahaha3.controller.loginAndRegister;

import com.kary.hahaha3.exceptions.emptyInput.PasswordEmptyException;
import com.kary.hahaha3.exceptions.emptyInput.UsernameEmptyException;
import com.kary.hahaha3.exceptions.errorInput.EmailErrorException;
import com.kary.hahaha3.exceptions.errorInput.PasswordErrorException;
import com.kary.hahaha3.exceptions.errorInput.UsernameErrorException;
import com.kary.hahaha3.mapper.UserMapper;
import com.kary.hahaha3.pojo.JsonResult;
import com.kary.hahaha3.pojo.User;
import com.kary.hahaha3.utils.AESUtil;
import com.kary.hahaha3.utils.MailUtil;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.mail.MessagingException;

/**
 * @author:123
 */
//todo 重新输入密码和输入邮箱放一起了，是否需要把他们分开？
@RestController
public class ForgetPasswordController {
    @Autowired
    @Qualifier("AESEncoder")
    AESUtil aesEncoder;
    @Autowired
    private UserMapper userMapper;

    @PostMapping("/resetPassword")
    @Operation(summary = "重置密码",description = "reset password")
    public JsonResult resetPassword(@RequestParam(value = "username")String username,
                                @RequestParam(value = "password")String password,
                                @RequestParam(value = "retypePassword")String retypePassword,
                                @RequestParam(value = "email")String email,
                                HttpSession session) throws Exception {
        if(username==null){
            throw new UsernameEmptyException("用户名为空");
        }else if(password==null){
            throw new PasswordEmptyException("密码为空");
        }else if(retypePassword==null){
            throw new PasswordEmptyException("请重输密码");
        }else if(!password.equals(retypePassword)){
            throw new PasswordErrorException("请输入一致的密码");
        }else{
            User userInDatabase=userMapper.selectUserByName(username);
            if(userInDatabase==null){
                throw new UsernameErrorException("该用户不存在");
            }else if(!MailUtil.legalQQMail(email)){
                throw new EmailErrorException("请输入合法的邮箱");
            }else {
                password= aesEncoder.encrypt(password);
                session.setAttribute("username",username);
                session.setAttribute("password",password);
                session.setAttribute("email",email);
                return JsonResult.ok("请准备输入邮箱");
            }
        }
    }
}
