package com.kary.hahaha3.pojo.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * @author:123
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class NewGameRecordJSON {
    Integer maxGameId;
    Long duration;
    String username;
    Integer kill;
    Integer death;
    Integer scoreGain;
    Integer assist;
    Double takeDamage;
    Double takenDamage;
    String mvpPlayer;
    Integer gameMode;
}
