package com.kary.hahaha3.pojo;

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
public class UserGame {
    @Schema(name = "username",description = "用户名",example = "kary")
    private String username;
    @Schema(name = "scoreTotal1v1",description = "1v1比分",example = "10")
    private int scoreTotal1v1;
    @Schema(name = "gamesCount",description = "总场数",example = "14")
    private int gamesCount;
    @Schema(name = "gamesCount1v1",description = "1v1场数",example = "15")
    private int gamesCount1v1;
    @Schema(name = "gamesCountBrawl",description = "乱斗场数",example = "12")
    private int gamesCountBrawl;
    @Schema(name = "scoreTotalBrawl",description = "乱斗比分",example = "10")
    private int scoreTotalBrawl;
    @Schema(name = "portrait",description = "头像",example = "3")
    private int portrait;
    @Schema(name = "maxScore1v1",description = "1v1最高分",example = "156")
    private int maxScore1v1;
    @Schema(name = "maxScoreBrawl",description = "乱斗最高分",example = "255")
    private int maxScoreBrawl;
    @Schema(name = "onMatch",description = "正在进行的比赛，可以分为0:不在线，1:在线但不在比赛，2:在1v1比赛，3:在乱斗比赛",example = "2")
    private int onMatch;
}
