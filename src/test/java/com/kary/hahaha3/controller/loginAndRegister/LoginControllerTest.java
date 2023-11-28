package com.kary.hahaha3.controller.loginAndRegister;

import com.kary.hahaha3.exceptions.errorInput.ErrorInputException;
import com.kary.hahaha3.mapper.UserMapper;
import com.kary.hahaha3.pojo.JsonResult;
import com.kary.hahaha3.pojo.User;
import com.kary.hahaha3.utils.AESUtil;
import jakarta.servlet.http.HttpSession;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;


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
    public void testLoginSuccess() throws ErrorInputException {
        // Mock dependencies
        User user = new User();
        user.setUsername("kary");
        user.setPwd("wood20040629");
        HttpSession session = mock(HttpSession.class);
        JsonResult result = loginController
                .login(user.getUsername(), user.getPwd(),session);

        myAccount = new User();
        User userInDatabase = userMapper.selectUserByName(user.getUsername());
        myAccount.setUsername(user.getUsername());
        myAccount.setPwd(user.getPwd());
        myAccount.setScoreTotal1v1(userInDatabase.getScoreTotal1v1());
        myAccount.setEmail(userInDatabase.getEmail());
        myAccount.setRegdate(userInDatabase.getRegdate());
        myAccount.setGamesCount(userInDatabase.getGamesCount());
        JsonResult expectedResult=JsonResult.ok(myAccount,"登录成功");
        // Assertions
        assertEquals(expectedResult, result);
        verify(session).setAttribute("myAccount",myAccount);
    }
    //未注册登录
    @Test
    public void testLoginWithoutRegister() throws ErrorInputException {
        // Mock dependencies
        User user = new User();
        user.setUsername("NotRegisteredName");
        user.setPwd("NotRegisterPwd");
        HttpSession session = mock(HttpSession.class);
        JsonResult result = loginController
                .login(user.getUsername(), user.getPwd(), session);
        JsonResult expectedResult=JsonResult.error("用户不存在");

        // Assertions
        assertEquals(expectedResult, result);
        /*verify(model).addAttribute("showPopup","该用户未注册");*/
    }
    //密码错误登录
    @Test
    public void testLoginPwdError() throws ErrorInputException {
        // Mock dependencies
        User user = new User();
        user.setUsername("testLoginSuccessName");
        user.setPwd("testLoginErrorPwd");
        HttpSession session = mock(HttpSession.class);
        JsonResult result = loginController
                .login(user.getUsername(), user.getPwd(),session);
        JsonResult expectedResult=JsonResult.error("密码错误");

        // Assertions
        assertEquals("views/login", result);
        /*verify(model).addAttribute("showPopup","密码错误");*/
    }
}
