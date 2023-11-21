package com.kary.hahaha3.service.impl;

import com.kary.hahaha3.mapper.RecordMapper;
import com.kary.hahaha3.pojo.Record;
import com.kary.hahaha3.pojo.vo.RecordVO;
import com.kary.hahaha3.service.RecordVOService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @author:123
 */
@Service
@Qualifier("RecordVOService")
public class RecordVOServiceImpl implements RecordVOService {
    @Autowired
    private RecordMapper recordMapper;
    @Override
    //输入我自己的名字和比赛列表
    public List<RecordVO> getGamesByIds(String username,String gameId) {
        String[] gameIds=gameId.split(",");
        List<RecordVO> records=new ArrayList<>();
        for (String gid : gameIds) {
            Integer id=Integer.parseInt(gid);
            Record record=recordMapper.selectGameById(id);
            RecordVO recordVO=new RecordVO();
            recordVO.setGameTime(record.getGameTime());
            String usernamePlay1=record.getUsernamePlay1();//玩家一姓名
            String usernamePlay2=record.getUsernamePlay2();//玩家二姓名
            boolean flag=usernamePlay1.equals(username);//我是否是第一位玩家
            String opponentUsername=(flag)?usernamePlay2:usernamePlay1;
            recordVO.setOpponentUsername(opponentUsername);

            int kills=flag?record.getKillPlay1(): record.getKillPlay2();
            recordVO.setKills(kills);
            int deaths=flag?record.getDeathPlay1(): record.getDeathPlay2();
            recordVO.setDeaths(deaths);
            double kd=(kills+0.0)/deaths;
            recordVO.setKd(kd);
            recordVO.setDuration(record.getDuration());
            recordVO.setWin(kills>deaths);
            records.add(recordVO);
        }
        return records;
    }

    @Override
    public List<RecordVO> getGamesByIds(String username, String gameId, int page) {
        int length=10,fromIndex=page-1,toIndex=fromIndex+length;
        List<RecordVO> res=getGamesByIds(username, gameId);
        return res.subList(fromIndex,toIndex< res.size()?toIndex: res.size());
    }
}
