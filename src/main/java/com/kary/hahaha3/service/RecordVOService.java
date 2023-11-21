package com.kary.hahaha3.service;

import com.kary.hahaha3.pojo.vo.RecordVO;

import java.util.List;

/**
 * @author:123
 */
public interface RecordVOService {
    List<RecordVO> getGamesByIds(String username,String gameId);
    List<RecordVO> getGamesByIds(String username,String gameId,int page);
}