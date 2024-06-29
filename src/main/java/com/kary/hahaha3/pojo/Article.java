package com.kary.hahaha3.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * @author:123
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Article {
    private int articleId;
    private String username;
    private Date publishTime;
    private int followerCount;
    private int likeCount;
    private int dislikeCount;
    private int commentCount;
    private String content;
    private String themeName;
}
