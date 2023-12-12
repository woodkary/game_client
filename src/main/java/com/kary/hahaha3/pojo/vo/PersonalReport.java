package com.kary.hahaha3.pojo.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author:123
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PersonalReport {
    private int type;
    private int gameNums;
    private int win;
    private int lose;
    private double winRate;
    private double averageTakeDamage;
    private double averageTakenDamage;
    private String level;
}

