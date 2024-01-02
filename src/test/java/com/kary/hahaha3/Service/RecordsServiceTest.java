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
public class RecordsServiceTest {
    @MockBean
    private GamesMapper gamesMapper;
    @MockBean
    private RecordMapper recordMapper;
    @Autowired
    @Qualifier("RecordsService")
    private RecordsService recordsService;
    @Test
    public void testRecordEmpty(){
        String username = "username";
        List<Record> recordList = new ArrayList<>();
        when(recordMapper.selectRecordsByUsername(username)).thenReturn(recordList);
        Records res=new Records();
        assertEquals(res,recordsService.getAllGame(username));
    }
    @Test
    public void testAllCase(){
        String username = "username";
        List<Record> recordList = new ArrayList<>();
        Record record1 = new Record();
        Record record2 = new Record();
        record1.setGameId(1);record2.setGameId(2);
        record1.setDeath(1);record1.setKill(2);
        record2.setKill(1);record2.setDeath(2);
        recordList.add(record1);recordList.add(record2);
        Games game1 = new Games();Games game2 = new Games();
        game1.setType(1);game2.setType(1);
        when(recordMapper.selectRecordsByUsername(username)).thenReturn(recordList);
        when(gamesMapper.getGameById(1)).thenReturn(game1);
        when(gamesMapper.getGameById(2)).thenReturn(game2);
        Records res = new Records();
        res.setKda(1.0);res.setWinRate(0.5);res.setTotalKills(3);res.setGameNums(2);
        assertEquals(res,recordsService.getAllGame(username));
    }
}
