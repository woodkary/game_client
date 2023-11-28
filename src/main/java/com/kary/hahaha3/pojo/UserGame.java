package com.kary.hahaha3.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author:123
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserGame {
    private String username;
    private String scoreTotal1v1;
    private int gameCount;
    private int scoreTotalBrawl;
}
