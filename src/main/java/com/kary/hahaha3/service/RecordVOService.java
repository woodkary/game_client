package com.kary.hahaha3.service;

import com.kary.hahaha3.exceptions.errorInput.MatchTypeErrorException;
import com.kary.hahaha3.pojo.vo.RecordVO;

import java.util.List;

/**
 * @author:123
 */
public interface RecordVOService {
    List<RecordVO> getGamesByIds(String username,Integer type) throws MatchTypeErrorException;
    List<RecordVO> getGamesByIds(String username,Integer type,int page) throws MatchTypeErrorException;
}
