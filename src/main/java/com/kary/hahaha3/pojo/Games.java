package com.kary.hahaha3.pojo;

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
@Tag(name = "Games")
public class Games {
    private int type;
    private int gameId;
    private Date gameTime;
    private long duration;
    private String mvpPlayer;
}
