package com.kary.hahaha3.mapper;

import com.kary.hahaha3.pojo.Article;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author:123
 */
@Repository
@Mapper
@Tag(name = "ArticleMapper")
public interface ArticleMapper {
    Integer publishArticle(@Param("articleId")Integer articleId,
                           @Param("username")String username,
                           @Param("content")String content,
                           @Param("articleTopic")String articleTopic,
                           @Param("themeName")String themeName);
    Article getArticleById(@Param("articleId")Integer articleId);
    Integer deleteArticle(@Param("articleId")Integer articleId);
    Integer follow(@Param("articleId")Integer articleId);
    Integer like(@Param("articleId")Integer articleId);
    Integer dislike(@Param("articleId")Integer articleId);
    Integer getNewArticleId();
    List<Article> getAllArticleByPage(@Param("page")Integer page,@Param("themeName")String themeName);
}
