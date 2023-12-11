package com.kary.hahaha3.controller.loginAndRegister;

import com.kary.hahaha3.exceptions.errorInput.UsernameErrorException;
import com.kary.hahaha3.pojo.JsonResult;
import com.kary.hahaha3.pojo.User;
import com.kary.hahaha3.pojo.vo.RegisterJSON;
import com.kary.hahaha3.service.UserService;
import com.kary.hahaha3.utils.AESUtil;
import jakarta.servlet.http.HttpSession;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;

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
    }
    @Test
    void testRegisterUserRegistered(){
        registerJSON.setUsername("kary");
        assertThrows(UsernameErrorException.class,()->registerController.userRegister(registerJSON, httpSession));
    }
    @Test
    void testRegisterSuccess() throws Exception {
        JsonResult result = registerController.userRegister(registerJSON, httpSession);
        assertEquals(new JsonResult().ok("请准备发验证码"), result);
        verify(httpSession).setAttribute("username", registerJSON.getUsername());
        verify(httpSession).setAttribute("password", aesEncoder.encrypt(registerJSON.getPassword()));
    }
}
