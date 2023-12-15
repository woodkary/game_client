package com.kary.hahaha3.controller.rankInformation;

import com.kary.hahaha3.controller.loginAndRegister.EmailVerificationController;
import com.kary.hahaha3.service.PersonalReportService;
import com.kary.hahaha3.service.RecordVOService;
import com.kary.hahaha3.service.RecordsService;
import com.kary.hahaha3.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(EmailVerificationController.class)
@AutoConfigureMockMvc

public class UserRankInformationControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private RecordsService recordsService;
    @MockBean
    private PersonalReportService personalReportService;
    @MockBean
    private RecordVOService recordVOService;
    @MockBean
    private UserService userService;

}
