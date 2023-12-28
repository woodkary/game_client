package com.kary.hahaha3.pojo.vo;

import io.swagger.v3.oas.annotations.media.Schema;
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
    @Schema(name = "type",description = "游戏类型",example = "1")
    private int type;
    @Schema(name = "gameNums",description = "游戏场数",example = "10")
    private int gameNums;
    @Schema(name = "win",description = "胜利场数",example = "10")
    private int win;
    @Schema(name = "lose",description = "失败场数",example = "5")
    private int lose;
    @Schema(name = "winRate",description = "胜率",example = "0.3")
    private double winRate;
    @Schema(name = "averageTakeDamage",description = "场均伤害",example = "23.4")
    private double averageTakeDamage;//场均伤害
    @Schema(name = "averageTakenDamage",description = "场均承伤",example = "41.4")
    private double averageTakenDamage;//场均承伤
    @Schema(name = "averageKill",description = "场均击杀",example = "2.4")
    private double averageKill;//场均击杀
    @Schema(name = "averageDeath",description = "场均死亡",example = "1.5")
    private double averageDeath;//场均死亡
    @Schema(name = "averageAssist",description = "场均助攻",example = "1.5")
    private double averageAssist;//场均助攻
    @Schema(name = "kda",description = "总kda统计",example = "0.145")
    private double kda;//总kda
    @Schema(name = "level",description = "段位",example = "钻石")
    private String level;
    @Schema(name = "score",description = "积分",example = "100")
    private int score;
    @Schema(name = "portrait",description = "头像",example = "1")
    private int portrait;
}

