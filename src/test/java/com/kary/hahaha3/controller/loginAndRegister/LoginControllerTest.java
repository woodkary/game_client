/*
package com.kary.hahaha3.controller.loginAndRegister;

import com.kary.hahaha3.controller.loginAndRegister.LoginController;
import com.kary.hahaha3.mapper.UserMapper;
import com.kary.hahaha3.pojo.User;
import com.kary.hahaha3.utils.AESUtil;
import jakarta.servlet.http.HttpSession;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.verification.VerificationMode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.ui.Model;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@SpringBootTest
public class LoginControllerTest {
    @Autowired
    @Qualifier("AESEncoder")
    AESUtil aesEncoder;
    @Autowired
    UserMapper userMapper;
    @Autowired
    private LoginController loginController;
    private User myAccount;
    //登录成功
    @Test
    public void testLoginSuccess() {
        // Mock dependencies
        User user = new User();
        user.setUsername("testLoginSuccessName");
        user.setPwd("testLoginSuccessPwd");
        Model model = mock(Model.class);
        HttpSession session = mock(HttpSession.class);
        String result = loginController
                .login(user.getUsername(), user.getPwd(), model, session);
        // Assertions
        assertEquals("views/loginSuccess", result);
        myAccount = new User();
        User userInDatabase = userMapper.selectUserByName(user.getUsername());
        myAccount.setUsername(user.getUsername());
        myAccount.setPwd(user.getPwd());
        myAccount.setScoreTotal1v1(userInDatabase.getScoreTotal1v1());
        myAccount.setEmail(userInDatabase.getEmail());
        myAccount.setRegdate(userInDatabase.getRegdate());
        myAccount.setGamesCount(userInDatabase.getGamesCount());
        myAccount.setGamesId(userInDatabase.getGamesId());
        verify(model).addAttribute("myAccount", myAccount);
        verify(session).setAttribute("myAccount",myAccount);
    }
    //未注册登录
    @Test
    public void testLoginWithoutRegister(){
        // Mock dependencies
        User user = new User();
        user.setUsername("NotRegisteredName");
        user.setPwd("NotRegisterPwd");
        Model model = mock(Model.class);
        HttpSession session = mock(HttpSession.class);
        String result = loginController
                .login(user.getUsername(), user.getPwd(), model, session);

        // Assertions
        assertEquals("views/login", result);
        verify(model).addAttribute("showPopup","该用户未注册");
    }
    //密码错误登录
    @Test
    public void testLoginPwdError(){
        // Mock dependencies
        User user = new User();
        user.setUsername("testLoginSuccessName");
        user.setPwd("testLoginErrorPwd");
        Model model = mock(Model.class);
        HttpSession session = mock(HttpSession.class);
        String result = loginController
                .login(user.getUsername(), user.getPwd(), model, session);

        // Assertions
        assertEquals("views/login", result);
        verify(model).addAttribute("showPopup","密码错误");
    }
}
*/
