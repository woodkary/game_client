package com.kary.hahaha3.pojo.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author:123
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PublishCommentJSON {
    private Integer articleId;
    private String content;

}
