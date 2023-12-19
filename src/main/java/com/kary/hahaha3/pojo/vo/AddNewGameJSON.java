package com.kary.hahaha3.pojo.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * @author:123
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class AddNewGameJSON {
    Integer type;
    Integer gameId;
    Long duration;
    String mvpPlayer;
}
