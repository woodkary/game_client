package com.kary.hahaha3.controller.loginAndRegister;

import com.kary.hahaha3.exceptions.errorInput.ErrorInputException;
import com.kary.hahaha3.exceptions.errorInput.PasswordErrorException;
import com.kary.hahaha3.exceptions.errorInput.UsernameErrorException;
import com.kary.hahaha3.mapper.UserMapper;
import com.kary.hahaha3.pojo.JsonResult;
import com.kary.hahaha3.pojo.User;
import com.kary.hahaha3.service.UserService;
import com.kary.hahaha3.utils.AESUtil;
import jakarta.servlet.http.HttpSession;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@SpringBootTest
public class LoginControllerTest {
    @Autowired
    UserMapper userMapper;
    @Autowired
    @Qualifier("AESEncoder")
    AESUtil aesEncoder;
    @Autowired
    private UserService userService;
    @Autowired
    private LoginController loginController;


    @Test
    public void testLoginSuccess() throws ErrorInputException {
        User user = new User();
        user.setUsername("kary");
        user.setPwd("wood20040629");
        HttpSession session = mock(HttpSession.class);
        JsonResult result = loginController
                .login(user.getUsername(), user.getPwd(),session);

        User myAccount = new User();
        User userInDatabase = userService.selectUserByName(user.getUsername());
        myAccount.setUsername(user.getUsername());
        myAccount.setPwd(user.getPwd());
        myAccount.setEmail(userInDatabase.getEmail());
        myAccount.setRegdate(userInDatabase.getRegdate());
        JsonResult expectedResult=JsonResult.ok(myAccount,"登录成功");
        // Assertions
        assertEquals(expectedResult, result);
        verify(session).setAttribute("myAccount",myAccount);
    }
    //未注册登录
    @Test
    public void testLoginWithoutRegister() throws ErrorInputException {
        User user = new User();
        user.setUsername("NotRegisteredName");
        user.setPwd("NotRegisterPwd");
        HttpSession session = mock(HttpSession.class);
        assertThrows(new UsernameErrorException("用户不存在").getClass(),()->loginController.login(user.getUsername(), user.getPwd(), session));
    }
    //密码错误登录
    @Test
    public void testLoginPwdError() throws ErrorInputException {
        // Mock dependencies
        User user = new User();
        user.setUsername("kary");
        user.setPwd("testLoginErrorPwd");
        HttpSession session = mock(HttpSession.class);
        assertThrows(new PasswordErrorException("密码错误").getClass(),()->loginController.login(user.getUsername(), user.getPwd(), session));
    }
}
