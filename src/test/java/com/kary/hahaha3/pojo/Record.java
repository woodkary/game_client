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
@Tag(name = "Record")
public class Record {
    private int gameId;
    private Date gameTime;
    private long duration;
    private String usernamePlay1;
    private String usernamePlay2;
    private int killPlay1;
    private int killPlay2;
    private int deathPlay1;
    private int deathPlay2;
    private int scoreGainPlay1;
    private int scoreGainPlay2;
    private int scoreTotalPlay1;
    private int scoreTotalPlay2;
}
