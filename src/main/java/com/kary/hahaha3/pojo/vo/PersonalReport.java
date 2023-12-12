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
    private double averageTakeDamage;//场均伤害
    private double averageTakenDamage;//场均承伤
    private double averageKill;//场均击杀
    private double averageDeath;//场均死亡
    private double averageAssist;//场均助攻
    private double kda;//总kda
    private String level;
}

