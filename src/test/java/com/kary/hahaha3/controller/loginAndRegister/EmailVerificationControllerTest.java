package com.kary.hahaha3.controller.loginAndRegister;

import com.kary.hahaha3.config.AESEncoderConfig;
import com.kary.hahaha3.mapper.UserMapper;
import com.kary.hahaha3.pojo.User;
import com.kary.hahaha3.service.UserService;
import com.kary.hahaha3.utils.AESUtil;
import jakarta.servlet.http.HttpSession;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@WebMvcTest(EmailVerificationController.class)
@AutoConfigureMockMvc
@Import(AESEncoderConfig.class)
//@SpringBootTest
public class EmailVerificationControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;
    @MockBean
    private AESUtil aesEncoder;
    @MockBean
    private UserMapper userMapper;
    private MockHttpSession session;

    @Test
    public void testEmailIsRegistered() throws Exception {
        session = new MockHttpSession();
        when(userService.emailIsRegistered("834479572@qq.com")).thenReturn(true);
        // 模拟发送GET请求到"/sendVeriCode/{operation}"，并验证响应状态码为400，响应内容为JSON格式的对象
        mockMvc.perform(MockMvcRequestBuilders.get("/sendVeriCode/{operation}",1).session(session).param("email","834479572@qq.com"))
                .andExpect(status().is(400))
                .andExpect(content().json("{\"code\":400,\"data\":\"EmailErrorException\",\"message\":\"该邮箱已注册\"}"));

    }
    @Test
    public void testSendVeriCodeSuccess() throws Exception {
        session = new MockHttpSession();
        List<User> users = new ArrayList<>();
        when(userMapper.selectUserByEmail("2452826804@qq.com")).thenReturn(users);
        // 模拟发送GET请求到"/sendVeriCode/{operation}"，并验证响应状态码为200，响应内容为JSON格式的对象
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get("/sendVeriCode/{operation}",1).session(session).param("email","2452826804@qq.com"))
                .andExpect(status().is(200))
                .andExpect(jsonPath("$.message").value("等待输入验证码"))
                .andExpect(jsonPath("code").value(200))
                .andDo(MockMvcResultHandlers.print())
                .andReturn();
        HttpSession httpSession =  result.getRequest().getSession();
        assertEquals("2452826804@qq.com",httpSession.getAttribute("email"));
    }
    @Test
    public void testSendVeriCodeChangePwdOrLogin() throws Exception {
        session = new MockHttpSession();
        List<User>users = new ArrayList<>();
        User user = userMapper.selectUserByName("miximixi");
        users.add(user);
        when(userMapper.selectUserByEmail("czg2012@outlook.com")).thenReturn(users);
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get("/sendVeriCode/{operation}",2).session(session).param("email","czg2012@outlook.com"))
                .andExpect(status().is(200))
                .andExpect(jsonPath("$.message").value("等待输入验证码"))
                .andExpect(jsonPath("code").value(200))
                .andDo(MockMvcResultHandlers.print())
                .andReturn();
        HttpSession httpSession =  result.getRequest().getSession();
        assertEquals(user,httpSession.getAttribute("userGetByEmail"));
    }
    @Test
    public void testTypeVeriCodeError() throws Exception {
        session = new MockHttpSession();
        session.setAttribute("verificationCode","111111");
        mockMvc.perform(MockMvcRequestBuilders.post("/typeVeriCode/{operation}",1)
                        .session(session)
                        .contentType(MediaType.APPLICATION_JSON) // 设置Content-Type为JSON
                        .content("111111"))
                .andExpect(status().is4xxClientError())
                .andExpect(content().json("{\"code\":400,\"data\":\"VerificationCodeErrorException\",\"message\":\"验证码错误，请重试\"}"));
    }
    @Test
    public void testTypeVeriCodeToRegisterSuccess() throws Exception {
        session = new MockHttpSession();
        session.setAttribute("verificationCode","111111");
        when(userService.insertUser((String) session.getAttribute("username"), (String) session.getAttribute("password"), (String) session.getAttribute("email"))).thenReturn(1);
        mockMvc.perform(MockMvcRequestBuilders.post("/typeVeriCode/{operation}",1)
                .session(session)
                .contentType(MediaType.APPLICATION_JSON) // 设置Content-Type为JSON
                .content("111111"))
                .andExpect(status().isOk())
                .andExpect(content().json("{\"code\":200,\"message\":\"注册成功\"}"));
    }
    @Test
    public void testTypeVeriCodeToChangePwdSuccess() throws Exception {
        session = new MockHttpSession();
        session.setAttribute("verificationCode","111111");
        mockMvc.perform(MockMvcRequestBuilders.post("/typeVeriCode/{operation}",2)
                        .session(session)
                        .contentType(MediaType.APPLICATION_JSON) // 设置Content-Type为JSON
                        .content("111111"))
                .andExpect(status().isOk())
                .andExpect(content().json("{\"code\":200,\"message\":\"发送邮箱成功，请准备修改密码\"}"));
    }
    @Test
    public void testTypeVeriCodeToLoginSuccess() throws Exception {
        session = new MockHttpSession();
        User user = new User();
        user.setPwd("123456");
        session.setAttribute("userGetByEmail",user);
        mockMvc.perform(MockMvcRequestBuilders.post("/typeVeriCode/{operation}",2)
                        .session(session)
                        .contentType(MediaType.APPLICATION_JSON) // 设置Content-Type为JSON
                        .content("111111"))
                .andExpect(status().isOk())
                .andExpect(content().json("{\"code\":200,\"message\":\"发送邮箱成功，请准备修改密码\"}"));
    }
}
