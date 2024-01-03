package com.kary.hahaha3.Service;

import com.kary.hahaha3.mapper.GamesMapper;
import com.kary.hahaha3.mapper.RecordMapper;
import com.kary.hahaha3.mapper.UserGameMapper;
import com.kary.hahaha3.pojo.UserGame;
import com.kary.hahaha3.service.GameRecordService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SpringBootTest
public class GameRecordServiceTest {
    @MockBean
    private RecordMapper recordMapper;
    @MockBean
    private GamesMapper gamesMapper;
    @MockBean
    private UserGameMapper userGameMapper;
    @Autowired
    @Qualifier("GameRecordService")
    private GameRecordService gameRecordService;
    @Test
    public void test1V1Break(){
        Integer maxGameId = 1;Long duration = 1L; Integer kill = 1;Integer death = 1;Integer scoreGain = 1;
        Integer assist = 1; Double takeDamage = 1.0;Double takenDamage = 1.0; String mvpPlayer = "";
        Integer gameMode = 1;
        String username = "username";
        UserGame user = new UserGame();
        user.setMaxScore1v1(1);user.setScoreTotal1v1(2);
        when(userGameMapper.addScore(username,gameMode,scoreGain)).thenReturn(1);
        when(userGameMapper.getUserGame(username)).thenReturn(user);
        when(userGameMapper.updateMaxScore(username,gameMode)).thenReturn(1);
        when(recordMapper.addGamesCount(username)).thenReturn(1);
        when(recordMapper.addGamesCount1v1(username)).thenReturn(1);
        when(recordMapper.addNewRecord(maxGameId,username,kill,death,(gameMode==1)?0:assist,scoreGain,takeDamage, takenDamage)).thenReturn(1);
        assertEquals(1,gameRecordService.recordNewMatch(maxGameId,duration,username,kill,death,scoreGain,assist,takeDamage,takenDamage,mvpPlayer,gameMode));
        verify(userGameMapper, Mockito.times(2)).updateMaxScore(username,1);
    }
    @Test
    public void testBrawlBreak(){
        Integer maxGameId = 1;Long duration = 1L; Integer kill = 1;Integer death = 1;Integer scoreGain = 1;
        Integer assist = 1; Double takeDamage = 1.0;Double takenDamage = 1.0; String mvpPlayer = "";
        Integer gameMode = 2;
        String username = "username";
        UserGame user = new UserGame();
        user.setMaxScoreBrawl(1);user.setScoreTotalBrawl(2);
        when(userGameMapper.addScore(username,gameMode,scoreGain)).thenReturn(1);
        when(userGameMapper.getUserGame(username)).thenReturn(user);
        when(userGameMapper.updateMaxScore(username,gameMode)).thenReturn(1);
        when(recordMapper.addGamesCount(username)).thenReturn(1);
        when(recordMapper.addGamesCountBrawl(username)).thenReturn(1);
        when(recordMapper.addNewRecord(maxGameId,username,kill,death,(gameMode==1)?0:assist,scoreGain,takeDamage, takenDamage)).thenReturn(1);
        assertEquals(1,gameRecordService.recordNewMatch(maxGameId,duration,username,kill,death,scoreGain,assist,takeDamage,takenDamage,mvpPlayer,gameMode));
        verify(userGameMapper, Mockito.times(2)).updateMaxScore(username,gameMode);
    }
    @Test
    public void testTypeError(){
        Integer maxGameId = 1;Long duration = 1L; Integer kill = 1;Integer death = 1;Integer scoreGain = 1;
        Integer assist = 1; Double takeDamage = 1.0;Double takenDamage = 1.0; String mvpPlayer = "";
        Integer gameMode = 3;
        String username = "username";
        UserGame user = new UserGame();
        user.setMaxScoreBrawl(1);user.setScoreTotalBrawl(2);
        when(userGameMapper.addScore(username,gameMode,scoreGain)).thenReturn(0);
        when(userGameMapper.getUserGame(username)).thenReturn(user);
        when(userGameMapper.updateMaxScore(username,gameMode)).thenReturn(0);
        when(recordMapper.addGamesCount(username)).thenReturn(1);
        when(recordMapper.addNewRecord(maxGameId,username,kill,death,(gameMode==1)?0:assist,scoreGain,takeDamage, takenDamage)).thenReturn(1);
        assertEquals(0,gameRecordService.recordNewMatch(maxGameId,duration,username,kill,death,scoreGain,assist,takeDamage,takenDamage,mvpPlayer,gameMode));
    }
    @Test
    public void test1V1WithoutBreak(){
        Integer maxGameId = 1;Long duration = 1L; Integer kill = 1;Integer death = 1;Integer scoreGain = 1;
        Integer assist = 1; Double takeDamage = 1.0;Double takenDamage = 1.0; String mvpPlayer = "";
        Integer gameMode = 1;
        String username = "username";
        UserGame user = new UserGame();
        user.setMaxScore1v1(2);user.setScoreTotal1v1(1);
        when(userGameMapper.addScore(username,gameMode,scoreGain)).thenReturn(1);
        when(userGameMapper.getUserGame(username)).thenReturn(user);
        when(userGameMapper.updateMaxScore(username,gameMode)).thenReturn(1);
        when(recordMapper.addGamesCount(username)).thenReturn(1);
        when(recordMapper.addGamesCount1v1(username)).thenReturn(1);
        when(recordMapper.addNewRecord(maxGameId,username,kill,death,(gameMode==1)?0:assist,scoreGain,takeDamage, takenDamage)).thenReturn(1);
        assertEquals(1,gameRecordService.recordNewMatch(maxGameId,duration,username,kill,death,scoreGain,assist,takeDamage,takenDamage,mvpPlayer,gameMode));
        verify(userGameMapper, Mockito.times(1)).updateMaxScore(username,1);
    }
    @Test
    public void testBrawlWithoutBreak(){
        Integer maxGameId = 1;Long duration = 1L; Integer kill = 1;Integer death = 1;Integer scoreGain = 1;
        Integer assist = 1; Double takeDamage = 1.0;Double takenDamage = 1.0; String mvpPlayer = "";
        Integer gameMode = 2;
        String username = "username";
        UserGame user = new UserGame();
        user.setMaxScoreBrawl(2);user.setScoreTotalBrawl(1);
        when(userGameMapper.addScore(username,gameMode,scoreGain)).thenReturn(1);
        when(userGameMapper.getUserGame(username)).thenReturn(user);
        when(userGameMapper.updateMaxScore(username,gameMode)).thenReturn(1);
        when(recordMapper.addGamesCount(username)).thenReturn(1);
        when(recordMapper.addGamesCountBrawl(username)).thenReturn(1);
        when(recordMapper.addNewRecord(maxGameId,username,kill,death,(gameMode==1)?0:assist,scoreGain,takeDamage, takenDamage)).thenReturn(1);
        assertEquals(1,gameRecordService.recordNewMatch(maxGameId,duration,username,kill,death,scoreGain,assist,takeDamage,takenDamage,mvpPlayer,gameMode));
        verify(userGameMapper, Mockito.times(1)).updateMaxScore(username,gameMode);
    }
}
