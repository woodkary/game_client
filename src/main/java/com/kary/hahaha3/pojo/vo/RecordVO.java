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
    private int assists;//新增的
    private double kda;
    private long duration;
    private boolean isMVP;
    private String type;//新增的
    private double takeDamage;
    private double takenDamage;
}
