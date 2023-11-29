package com.kary.hahaha3.service;


import com.kary.hahaha3.exceptions.connection.DatabaseConnectionException;
import com.kary.hahaha3.exceptions.forum.article.NoSuchArticleException;
import com.kary.hahaha3.exceptions.forum.comment.NoSuchCommentException;
import com.kary.hahaha3.exceptions.forum.theme.NoSuchThemeException;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

/**
 * @author:123
 */
public interface ForumService {
    Integer publishArticle(String username,
                           String content,
                           String articleTopic,
                           String themeName) throws NoSuchThemeException;
    Integer publishComment(String username,
                           Integer articleId,
                           String content) throws NoSuchArticleException, DatabaseConnectionException;
    Integer replyComment(String username,String content,Integer parentId) throws NoSuchCommentException;
    Integer publishTheme(String themeName);
}
