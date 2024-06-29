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
public class Theme {
    private String themeName;
    private String description;
    private int articleCount;
    private int followerCount;
    private String articlesId;
}
