package com.kary.hahaha3.service;

import com.kary.hahaha3.pojo.Games;
import com.kary.hahaha3.pojo.vo.Records;

import java.util.List;

/**
 * @author:123
 */
public interface RecordsService {
    Records getAllGame(String username);
    Records getAllGameThisMonth(String username);
}
