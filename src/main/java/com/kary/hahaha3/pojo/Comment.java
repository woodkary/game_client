package com.kary.hahaha3.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * @author:123
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Comment {
    private int commentId;
    private String parentCommentUsername;
    private int parentId;
    private String commentUsername;
    private Date commentDate;
    private int articleId;
    private int commentFlag;
    private String content;
}
