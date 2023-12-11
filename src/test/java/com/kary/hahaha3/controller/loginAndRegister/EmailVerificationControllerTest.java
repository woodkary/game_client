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
}
