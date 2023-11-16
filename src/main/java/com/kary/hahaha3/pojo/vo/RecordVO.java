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
    private Date gameTime;
    private String opponentUsername;
    private int kills;
    private int deaths;
    private double kd;
    private long duration;
    private boolean win;

}
