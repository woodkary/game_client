package com.kary.hahaha3.pojo.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author:123
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ReplyCommentJSON {
    private Integer parentId;
    private String content;

}
