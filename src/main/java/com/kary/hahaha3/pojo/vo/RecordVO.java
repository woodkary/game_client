package com.kary.hahaha3.pojo.vo;

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
    private String username;
    private Date gameTime;
    /*减少的 private String opponentUsername;*/
    private int kills;
    private int deaths;
    private int assists;
    private int scoreGain;
    private double kda;
    private long duration;//持续时间
    private boolean isMVP;
    private String type;//游戏类型
    private double takeDamage;//造成伤害
    private double takenDamage;//承受伤害
}
