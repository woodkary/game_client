package com.kary.hahaha3.mapper;

import org.apache.ibatis.annotations.Param;

/**
 * @author:123
 */
public interface ArticleMapper {
    Integer publishArticle(@Param("articleId")Integer articleId,
                           @Param("username")String username,
                           @Param("content")String content,
                           @Param("articleTopic")String articleTopic,
                           @Param("themeName")String themeName);
    Integer getMaxArticleId();
    Integer follow(@Param("articleId")Integer articleId);
}
