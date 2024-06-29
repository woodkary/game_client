package com.kary.hahaha3.pojo.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.*;

import java.util.Date;

/**
 * @author:123
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@EqualsAndHashCode
@Tag(name = "RecordVO")
public class RecordVO {
    @Schema(name = "gameId",description = "游戏id",example = "1")
    private int gameId;
    @Schema(name = "username",description = "用户名",example = "kary")
    private String username;
    @Schema(name = "gameTime",description = "游戏时间",example = "2021/12/24")
    private Date gameTime;
    @Schema(name = "portrait",description = "头像",example = "1")
    private int portrait;
    /*减少的 private String opponentUsername;*/
    @Schema(name = "kills",description = "击杀数",example = "10")
    private int kills;
    @Schema(name = "deaths",description = "死亡数",example = "15")
    private int deaths;
    @Schema(name = "assists",description = "助攻数",example = "20")
    private int assists;
    @Schema(name = "scoreGain",description = "本场比赛得分",example = "15")
    private int scoreGain;
    @Schema(name = "kda",description = "本场比赛kda",example = "2.5")
    private double kda;
    @Schema(name = "duration",description = "比赛持续时间，毫秒",example = "300000")
    private long duration;//持续时间
    @Schema(name = "isMVP",description = "对于名为${username}的玩家，他是否是本场比赛的mvp",example = "true")
    private boolean isMVP;
    @Schema(name = "type",description = "游戏模式",example = "大乱斗")
    private String type;//游戏类型
    @Schema(name = "takeDamage",description = "造成伤害",example = "15.89")
    private double takeDamage;//造成伤害
    @Schema(name = "takenDamage",description = "承受伤害",example = "21.89")
    private double takenDamage;//承受伤害
}
