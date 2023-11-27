/*
package com.kary.hahaha3.controller.loginAndRegister;

import com.kary.hahaha3.HaHaHa3Application;
import com.kary.hahaha3.mapper.UserMapper;
import com.kary.hahaha3.pojo.User;
import com.kary.hahaha3.utils.AESUtil;
import com.kary.hahaha3.utils.MailUtil;
import jakarta.servlet.http.HttpSession;
import org.junit.Before;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
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
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

@SpringBootTest
public class RegisterControllerTest {
    @Autowired
    @Qualifier("AESEncoder")
    AESUtil aesEncoder;
    @Autowired
    UserMapper userMapper;
    @Autowired
    RegisterController registerController;
    @Mock
    HttpSession httpSession;
    private MockMvc mockMvc;
    @Mock
    Model model;

    private User user;
    @Test
    void testRegisterWithoutUsername(){
        String result = registerController.userRegister(null,"pwd","pwd","email",model, httpSession);
        assertEquals("views/register", result);
        verify(model).addAttribute("showPopup", "用户名为空");
    }
    @Test
    void testRegisterWithoutPassword(){
        String result = registerController.userRegister("username",null,"pwd","email",model,httpSession);
        assertEquals("views/register",result);
        verify(model).addAttribute("showPopup", "密码为空");
    }
    @Test
    void testRegisterWithoutRetypePassword(){
        String result = registerController.userRegister("username","pwd",null,"email",model,httpSession);
        assertEquals("views/register",result);
        verify(model).addAttribute("showPopup", "请重输密码");
    }
    @Test
    void testRegisterPasswordMismatched(){
        String result = registerController.userRegister("username","pwd","mismatched_pwd","email",model,httpSession);
        assertEquals("views/register",result);
        verify(model).addAttribute("showPopup", "请输入一致的密码");
    }
    @Test
    void testRegisterUserRegistered(){
        String result = registerController.userRegister("testRegisteredUser","pwd","pwd","email",model,httpSession);
        assertEquals("views/register",result);
        verify(model).addAttribute("showPopup","该用户已注册");
    }
    @Test
    void testRegisterIllegalQQMail(){
        String result = registerController.userRegister("testRegisteredUser","pwd","pwd","IllegalEmail",model,httpSession);
        assertEquals("views/register",result);
        verify(model).addAttribute("showPopup","请输入合法的邮箱");
    }
    @Test
    void testRegisterSuccess(){
        //陈金萍的邮箱
        String email = "2452826804@qq.com";
        String result = registerController.userRegister("testRegisterUsername","testRegisterPwd","testRegisterPwd",email,model,httpSession);
        assertEquals("views/emailVerification",result);
        verify(httpSession).setAttribute("username","testRegisterUsername");
        verify(httpSession).setAttribute("password","testRegisterPwd");
        verify(httpSession).setAttribute("email",email);
    }
}
*/
