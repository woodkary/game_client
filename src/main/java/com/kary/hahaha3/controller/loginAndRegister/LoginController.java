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

/**
 * @author:123
 */
@Controller
public class LoginController {
    @Autowired
    @Qualifier("AESEncoder")
    AESUtil aesEncoder;
    @Autowired
    @Qualifier("myAccount")
    private User myAccount;
    @Autowired
    private UserMapper userMapper;
    @PostMapping("/usr/login")
    @Operation(summary = "登录", description = "API to handle user login")
    public String login(@RequestParam(value = "username")String username, @RequestParam(value = "password")String password, Model model, HttpSession session){
        //找到数据库中的用户
        User userInDatabase=userMapper.selectUserByName(username);
        //找不到
        if(userInDatabase==null){
            model.addAttribute("showPopup","该用户未注册");
            return "views/login";
        //密码错误
        }else {
            String userPassword=aesEncoder.encrypt(password);
            if(!userInDatabase.getPwd().equals(userPassword)){
                model.addAttribute("showPopup","密码错误");
                return "views/login";
            }else{
                myAccount.setUsername(username);
                myAccount.setPwd(password);
                myAccount.setScoreTotal(userInDatabase.getScoreTotal());
                myAccount.setEmail(userInDatabase.getEmail());
                myAccount.setRegdate(userInDatabase.getRegdate());
                myAccount.setGameCount(userInDatabase.getGameCount());
                myAccount.setGameId(userInDatabase.getGameId());
                model.addAttribute("myAccount",myAccount);
                session.setAttribute("myAccount",myAccount);
                return "views/loginSuccess";
            }
        }
    }
    @GetMapping("/toLogin")
    @Operation(summary = "返回登陆页面", description = "API to redirect to the login page")
    public String toLogin(){
        return "views/login";
    }

}
