package com.kary.hahaha3.Service;

import com.kary.hahaha3.exceptions.errorInput.MatchTypeErrorException;
import com.kary.hahaha3.exceptions.errorInput.UsernameErrorException;
import com.kary.hahaha3.mapper.GamesMapper;
import com.kary.hahaha3.mapper.RecordMapper;
import com.kary.hahaha3.mapper.UserGameMapper;
import com.kary.hahaha3.mapper.UserMapper;
import com.kary.hahaha3.pojo.Games;
import com.kary.hahaha3.pojo.Record;
import com.kary.hahaha3.pojo.User;
import com.kary.hahaha3.pojo.vo.PersonalReport;
import com.kary.hahaha3.service.PersonalReportService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@SpringBootTest
public class PersonalReportServiceTest {
    @MockBean
    private UserMapper userMapper;
    @MockBean
    private UserGameMapper userGameMapper;
    @MockBean
    private GamesMapper gamesMapper;
    @MockBean
    private RecordMapper recordMapper;
    @Autowired
    @Qualifier("PersonalReportService")
    private PersonalReportService personalReportService;
    @Test
    public void testTypeError(){
        assertThrows(MatchTypeErrorException.class,
                ()->personalReportService.getPersonalReport("username",3));
    }
    @Test
    public void testUsernameError(){
        String username = "username";
        when(userMapper.selectUserByName(username)).thenReturn(null);
        assertThrows(UsernameErrorException.class,()->personalReportService.getPersonalReport(username,1));
    }
    @Test
    public void testRecordEmpty() throws UsernameErrorException, MatchTypeErrorException {
        String username = "username";
        int type = 1;
        User user = new User();
        user.setUsername("username");
        when(userMapper.selectUserByName(username)).thenReturn(user);
        when(userGameMapper.getPortrait(username)).thenReturn(1);
        when(userGameMapper.getScoreByType(username,type)).thenReturn(0);
        List<Record> recordList = new ArrayList<>();
        when(recordMapper.selectRecordsByUsername(username)).thenReturn(recordList);
        PersonalReport res = new PersonalReport();
        res.setPortrait(1);
        res.setLevel("青铜");
        res.setType(1);
        assertEquals(res,personalReportService.getPersonalReport(username,type));
    }
    @Test
    public void testRecordType_2() throws UsernameErrorException, MatchTypeErrorException {
        String username = "username";
        int type = 2;
        User user = new User();
        user.setUsername("username");
        when(userMapper.selectUserByName(username)).thenReturn(user);
        when(userGameMapper.getPortrait(username)).thenReturn(1);
        when(userGameMapper.getScoreByType(username,type)).thenReturn(0);
        List<Record> recordList = new ArrayList<>();
        Record record1 = new Record();Record record2 = new Record();Record record3 = new Record();
        record1.setGameId(1);record2.setGameId(2);record3.setGameId(3);
        recordList.add(record1);recordList.add(record2);recordList.add(record3);
        Games game1 = new Games();game1.setMvpPlayer(username);
        Games game2 = new Games();game2.setMvpPlayer("user");
        when(recordMapper.selectRecordsByUsername(username)).thenReturn(recordList);
        when(gamesMapper.getGameByIdAndType(1, type)).thenReturn(null);
        when(gamesMapper.getGameByIdAndType(2,type)).thenReturn(game1);
        when(gamesMapper.getGameByIdAndType(3,type)).thenReturn(game2);
        PersonalReport res = new PersonalReport();
        res.setPortrait(1);
        res.setLevel("青铜");
        res.setType(2);
        res.setGameNums(2);
        res.setWin(1);
        res.setWinRate(0.5);
        assertEquals(res,personalReportService.getPersonalReport(username,type));
    }
    @Test
    public void testRecordType_1() throws UsernameErrorException, MatchTypeErrorException {
        String username = "username";
        int type = 1;
        User user = new User();
        user.setUsername("username");
        when(userMapper.selectUserByName(username)).thenReturn(user);
        when(userGameMapper.getPortrait(username)).thenReturn(1);
        when(userGameMapper.getScoreByType(username,type)).thenReturn(0);
        List<Record> recordList = new ArrayList<>();
        Record record1 = new Record();Record record2 = new Record();
        record1.setGameId(1);record2.setGameId(2);
        record1.setKill(1);record1.setDeath(0);record2.setDeath(1);record2.setKill(0);
        recordList.add(record1);recordList.add(record2);
        Games game1 = new Games();game1.setMvpPlayer(username);
        Games game2 = new Games();game2.setMvpPlayer("user");
        when(recordMapper.selectRecordsByUsername(username)).thenReturn(recordList);
        when(gamesMapper.getGameByIdAndType(1, type)).thenReturn(game1);
        when(gamesMapper.getGameByIdAndType(2,type)).thenReturn(game2);
        PersonalReport res = new PersonalReport();
        res.setPortrait(1);
        res.setLevel("青铜");
        res.setType(1);
        res.setGameNums(2);
        res.setWin(1);
        res.setLose(1);
        res.setAverageKill(0.5);
        res.setAverageDeath(0.5);
        res.setWinRate(0.5);
        res.setKda(1.0);
        assertEquals(res,personalReportService.getPersonalReport(username,type));
    }
}
