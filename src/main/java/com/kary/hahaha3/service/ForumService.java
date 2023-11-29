package com.kary.hahaha3.service;


import com.kary.hahaha3.exceptions.connection.DatabaseConnectionException;
import com.kary.hahaha3.exceptions.forum.article.NoSuchArticleException;
import com.kary.hahaha3.exceptions.forum.comment.NoSuchCommentException;

/**
 * @author:123
 */
public interface ForumService {
    Integer publishArticle(String username,
                           String content,
                           String articleTopic,
                           String themeName) throws DatabaseConnectionException;
    Integer publishComment(String username,
                           Integer articleId,
                           String content) throws NoSuchArticleException, DatabaseConnectionException;
    Integer replyComment(String username,String content,Integer parentId) throws NoSuchCommentException, DatabaseConnectionException;
    Integer publishTheme(String themeName);
}
