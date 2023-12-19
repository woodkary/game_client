package com.kary.hahaha3.controller.rankInformation;

import com.kary.hahaha3.config.AESEncoderConfig;
import com.kary.hahaha3.mapper.GamesMapper;
import com.kary.hahaha3.mapper.RecordMapper;
import com.kary.hahaha3.mapper.UserMapper;
import com.kary.hahaha3.service.PersonalReportService;
import com.kary.hahaha3.service.RecordVOService;
import com.kary.hahaha3.service.RecordsService;
import com.kary.hahaha3.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(UserRankInformationController.class)
@AutoConfigureMockMvc
@Import(AESEncoderConfig.class)
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
    @MockBean
    private UserMapper userMapper;
    @MockBean
    private RecordMapper recordMapper;
    @MockBean
    private GamesMapper gamesMapper;
    private MockHttpSession httpSession = new MockHttpSession();
    @Test
    public void testGetOthersAllGameError() throws Exception {
        String username = "test";
        when(userService.selectUserByName(username)).thenReturn(null);
        mockMvc.perform(MockMvcRequestBuilders.get("/othersAllRecords").session(httpSession).param("username",username))
                .andExpect((status().is4xxClientError()))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(jsonPath("$.message").value("用户不存在"));
    }
}
