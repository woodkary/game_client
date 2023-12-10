package com.kary.hahaha3.controller.loginAndRegister;

import com.kary.hahaha3.exceptions.connection.VerificationCodeSendingException;
import com.kary.hahaha3.pojo.JsonResult;
import com.kary.hahaha3.service.UserService;
import com.kary.hahaha3.utils.AESUtil;
import com.kary.hahaha3.utils.MailUtil;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.servlet.http.HttpSession;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.web.bind.annotation.GetMapping;

import javax.mail.MessagingException;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class EmailVerificationControllerTest {
    @Autowired
    @Qualifier("AESEncoder")
    private AESUtil aesEncoder;
    @Autowired
    private UserService userService;
    @Autowired
    EmailVerificationController emailVerificationController;
    @Mock
    HttpSession session;
    @SneakyThrows
    @Test
    public void sendVeriCodeSucess(){
        String verificationCode= MailUtil.getRandom6Digit();
        session.setAttribute("email","sucess@qq.com");
        emailVerificationController.sendVeriCode(session);

    }
}
