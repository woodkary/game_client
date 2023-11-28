package com.kary.hahaha3.mapper;

import org.apache.ibatis.annotations.Param;

/**
 * @author:123
 */
public interface CommentMapper {
    //TODO 利用Service层判断commentFlag的值
    Integer publishComment(@Param("username")String username,
                           @Param("articleId")Integer articleId,
                           @Param("content")String content,
                            @Param("commentFlag")Integer commentFlag);
}
