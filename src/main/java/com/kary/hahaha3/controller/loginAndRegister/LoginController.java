package com.kary.hahaha3.controller.loginAndRegister;

import com.kary.hahaha3.exceptions.errorInput.ErrorInputException;
import com.kary.hahaha3.exceptions.errorInput.PasswordErrorException;
import com.kary.hahaha3.exceptions.errorInput.UsernameErrorException;
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
import org.springframework.web.bind.annotation.*;

/**
 * @author:123
 */
@RestController
@Tag(name = "登录")
public class LoginController {
    @Autowired
    @Qualifier("AESEncoder")
    AESUtil aesEncoder;
    @Autowired
    private UserMapper userMapper;
    @PostMapping("/login")
    @Operation(summary = "登录", description = "API to handle user login")
    public JsonResult login(@RequestParam(value = "username")String username,
                            @RequestParam(value = "password")String password,
                            HttpSession session) throws ErrorInputException {
        //找到数据库中的用户
        User userInDatabase=userMapper.selectUserByName(username);
        //找不到
        if(userInDatabase==null){
            throw new UsernameErrorException("用户不存在");
        //密码错误
        }else {
            String userPassword=aesEncoder.encrypt(password);
            if(!userInDatabase.getPwd().equals(userPassword)){
                throw new PasswordErrorException("密码错误");
            }else{
                User myAccount=new User();
                myAccount.setUsername(username);
                myAccount.setPwd(password);
                myAccount.setScoreTotal1v1(userInDatabase.getScoreTotal1v1());
                myAccount.setScoreTotalBrawl(userInDatabase.getScoreTotalBrawl());
                myAccount.setEmail(userInDatabase.getEmail());
                myAccount.setRegdate(userInDatabase.getRegdate());
                myAccount.setGamesCount(userInDatabase.getGamesCount());
                myAccount.setGamesId(userInDatabase.getGamesId());
                myAccount.setAvatar(userInDatabase.getAvatar());
                session.setAttribute("myAccount",myAccount);
                return JsonResult.ok(myAccount,"登录成功");
            }
        }
    }
}
