package com.kary.hahaha3.pojo.vo;

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
    private double kda;
    /*减少的 private String opponentUsername;*/
    private double winRate;
    private int totalKills;
    private int gameNums;

}
