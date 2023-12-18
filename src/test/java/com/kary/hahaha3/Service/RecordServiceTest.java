package com.kary.hahaha3.Service;

import com.kary.hahaha3.mapper.GamesMapper;
import com.kary.hahaha3.mapper.RecordMapper;
import com.kary.hahaha3.pojo.Games;
import com.kary.hahaha3.pojo.Record;
import com.kary.hahaha3.pojo.vo.Records;
import com.kary.hahaha3.service.RecordsService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@SpringBootTest
public class RecordServiceTest {
    @MockBean
    private GamesMapper gamesMapper;
    @MockBean
    private RecordMapper recordMapper;
    @Autowired
    @Qualifier("RecordsService")
    private RecordsService recordsService;
    @Test
    public void testRecordsServiceGetAllGame_1(){
        Record record = new Record();
        record.setGameId(1);
        record.setUsername("test");
        record.setKill(2);
        record.setDeath(1);
        record.setAssist(2);
        record.setScoreGain(20);
        record.setTakeDamage(10);
        List<Record> recordList = new ArrayList<>();
        recordList.add(record);
        when(recordMapper.selectRecordsByUsername("test")).thenReturn(recordList);
        Games games = new Games();
        games.setType(1);
        games.setGameId(1);
        when(gamesMapper.getGameById(1)).thenReturn(games);
        Records res = new Records();
        res.setGameNums(1);
        res.setWinRate(1);
        res.setKda(2);
        res.setTotalKills(2);
        assertEquals(res,recordsService.getAllGame("test"));
    }
    @Test
    public void testRecordsServiceGetAllGame_2(){
        Record record = new Record();
        record.setGameId(1);
        record.setUsername("test");
        record.setKill(2);
        record.setDeath(0);
        record.setAssist(2);
        record.setScoreGain(20);
        record.setTakeDamage(10);
        List<Record> recordList = new ArrayList<>();
        recordList.add(record);
        when(recordMapper.selectRecordsByUsername("test")).thenReturn(recordList);
        Games games = new Games();
        games.setType(1);
        games.setGameId(1);
        when(gamesMapper.getGameById(1)).thenReturn(games);
        Records res = new Records();
        res.setGameNums(1);
        res.setWinRate(1);
        res.setKda(2);
        res.setTotalKills(2);
        assertEquals(res,recordsService.getAllGame("test"));
    }
    @Test
    public void testRecordsServiceGetAllGame_3(){
        Record record = new Record();
        record.setGameId(1);
        record.setUsername("test");
        record.setKill(2);
        record.setDeath(0);
        record.setAssist(2);
        record.setScoreGain(20);
        record.setTakeDamage(10);
        Record record1 = new Record();
        record1.setGameId(1);
        record1.setUsername("test");
        record1.setKill(1);
        record1.setDeath(2);
        record1.setAssist(2);
        record1.setScoreGain(20);
        record1.setTakeDamage(10);
        List<Record> recordList = new ArrayList<>();
        recordList.add(record);
        recordList.add(record1);
        when(recordMapper.selectRecordsByUsername("test")).thenReturn(recordList);
        Games games = new Games();
        games.setType(1);
        games.setGameId(1);
        when(gamesMapper.getGameById(1)).thenReturn(games,games);
        Records res = new Records();
        res.setGameNums(2);
        res.setWinRate(0.5);
        res.setKda(1.5);
        res.setTotalKills(3);
        assertEquals(res,recordsService.getAllGame("test"));
    }
}
