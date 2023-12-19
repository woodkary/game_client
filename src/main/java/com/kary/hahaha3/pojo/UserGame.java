package com.kary.hahaha3.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author:123
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserGame {
    private String username;
    private int scoreTotal1v1;
    private int gamesCount;
    private int gamesCount1v1;
    private int gamesCountBrawl;
    private int scoreTotalBrawl;
    private int portrait;
    private int maxScore1v1;
    private int maxScoreBrawl;
}
