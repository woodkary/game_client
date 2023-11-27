package com.kary.hahaha3.pojo.vo;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.*;

import java.io.Serializable;
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
public class RecordVO implements Serializable {
    private Date gameTime;
    /*减少的 private String opponentUsername;*/
    private int kills;
    private int deaths;
    private int assists;//新增的
    private double kd;
    private long duration;
    /*减少的 private boolean win;*/
    private String type;//新增的
}
