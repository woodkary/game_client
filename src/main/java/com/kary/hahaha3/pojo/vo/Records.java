package com.kary.hahaha3.pojo.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.*;

import java.io.Serializable;

/**
 * @author:123
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@EqualsAndHashCode
@Tag(name = "Records")
public class Records implements Serializable {
    @Schema(name = "kda",description = "总kda统计",example = "0.145")
    private double kda;
    /*减少的 private String opponentUsername;*/
    @Schema(name = "winRate",description = "胜率",example = "0.3")
    private double winRate;
    @Schema(name = "totalKills",description = "总击杀数",example = "10")
    private int totalKills;
    @Schema(name = "totalDeaths",description = "总死亡数",example = "10")
    private int gameNums;

}
