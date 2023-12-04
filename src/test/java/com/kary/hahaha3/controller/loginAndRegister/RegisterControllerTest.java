package com.kary.hahaha3.controller.loginAndRegister;

import com.kary.hahaha3.HaHaHa3Application;
import com.kary.hahaha3.exceptions.JsonException;
import com.kary.hahaha3.exceptions.emptyInput.PasswordEmptyException;
import com.kary.hahaha3.exceptions.emptyInput.UsernameEmptyException;
import com.kary.hahaha3.exceptions.errorInput.EmailErrorException;
import com.kary.hahaha3.exceptions.errorInput.PasswordErrorException;
import com.kary.hahaha3.exceptions.errorInput.UsernameErrorException;
import com.kary.hahaha3.mapper.UserMapper;
import com.kary.hahaha3.pojo.JsonResult;
import com.kary.hahaha3.pojo.User;
import com.kary.hahaha3.pojo.vo.RegisterJSON;
import com.kary.hahaha3.service.UserService;
import com.kary.hahaha3.utils.AESUtil;
import com.kary.hahaha3.utils.MailUtil;
import jakarta.servlet.http.HttpSession;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

@SpringBootTest
public class RegisterControllerTest {
    @Autowired
    @Qualifier("AESEncoder")
    AESUtil aesEncoder;
    @Autowired
    UserService userService;
    @Autowired
    RegisterController registerController;
    @Mock
    HttpSession httpSession;



    private User user;
    private RegisterJSON registerJSON;
    @BeforeEach
    void Before(){
        registerJSON = new RegisterJSON();
        registerJSON.setUsername("Username");
        registerJSON.setPassword("Pwd");
        registerJSON.setRetypePassword("Pwd");
        registerJSON.setEmail("2452826804@qq.com");

    }
    @Test
    void testRegisterWithoutUsername(){
        registerJSON.setUsername(null);
        assertThrows(new UsernameEmptyException("用户名为空").getClass(),()->registerController.userRegister(registerJSON, httpSession));
    }
    @Test
    void testRegisterWithoutPassword(){
        registerJSON.setPassword(null);
        assertThrows(new PasswordEmptyException("密码为空").getClass(),()->registerController.userRegister(registerJSON, httpSession));
    }
    @Test
    void testRegisterWithoutRetypePassword(){
        registerJSON.setRetypePassword(null);
        assertThrows(new PasswordEmptyException("请重输密码").getClass(),()->registerController.userRegister(registerJSON, httpSession));
    }
    @Test
    void testRegisterPasswordMismatched(){
        registerJSON.setRetypePassword("newPwd");
        assertThrows(new PasswordErrorException("请输入一致的密码").getClass(),()->registerController.userRegister(registerJSON, httpSession));
    }
    @Test
    void testRegisterUserRegistered(){
        registerJSON.setUsername("testLoginSuccessName");
        assertThrows(new UsernameErrorException("用户已注册").getClass(),()->registerController.userRegister(registerJSON, httpSession));
    }
    @Test
    void testRegisterIllegalQQMail(){
        registerJSON.setEmail("illegalMail");
        assertThrows(new EmailErrorException("请输入合法邮箱").getClass(),()->registerController.userRegister(registerJSON, httpSession));
    }
    @Test
    void testRegisterSuccess() throws Exception {
        JsonResult result = registerController.userRegister(registerJSON, httpSession);
        assertEquals(new JsonResult().ok("请准备发验证码"), result);
        verify(httpSession).setAttribute("username", registerJSON.getUsername());
        verify(httpSession).setAttribute("password", aesEncoder.encrypt(registerJSON.getPassword()));
        verify(httpSession).setAttribute("email", registerJSON.getEmail());
    }
}
