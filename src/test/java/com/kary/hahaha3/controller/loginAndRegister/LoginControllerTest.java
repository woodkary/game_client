package com.kary.hahaha3.controller.loginAndRegister;

import com.kary.hahaha3.config.AESEncoderConfig;
import com.kary.hahaha3.config.AccountConfiguration;
import com.kary.hahaha3.mapper.UserMapper;
import com.kary.hahaha3.pojo.User;
import com.kary.hahaha3.utils.AESUtil;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;


/**
 * @author:123
 */
@ExtendWith({SpringExtension.class, MockitoExtension.class})
@WebMvcTest(LoginController.class)
@Import({AESEncoderConfig.class,AccountConfiguration.class})
public class LoginControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private UserMapper userMapper;
    @Autowired
    @Qualifier("AESEncoder")
    private AESUtil aesEncoder;
    @Autowired
    @Qualifier("myAccount")
    private User myAccount;
    @Test
    public void testLoginSuccess() throws Exception {
        String validEncryptedPassword=aesEncoder.encrypt("Wood20040629");
        // Mock the behavior of UserMapper and AESUtil
        Mockito.when(userMapper.selectUserByName("OyoYorupoKun"))
                .thenReturn(new User("OyoYorupoKun",validEncryptedPassword));

        mockMvc.perform(MockMvcRequestBuilders.post("/usr/login")
                        .param("username", "OyoYoyupoKun")
                        .param("password", "Wood20040629"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.view().name("views/loginSuccess"))
                .andExpect(MockMvcResultMatchers.model().attributeExists("myAccount"));
    }

}
