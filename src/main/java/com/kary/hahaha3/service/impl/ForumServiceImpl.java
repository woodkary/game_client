package com.kary.hahaha3.service.impl;

import com.kary.hahaha3.exceptions.connection.DatabaseConnectionException;
import com.kary.hahaha3.exceptions.forum.article.NoSuchArticleException;
import com.kary.hahaha3.exceptions.forum.comment.NoSuchCommentException;
import com.kary.hahaha3.mapper.ArticleMapper;
import com.kary.hahaha3.mapper.CommentMapper;
import com.kary.hahaha3.mapper.ThemeMapper;
import com.kary.hahaha3.pojo.Article;
import com.kary.hahaha3.pojo.Comment;
import com.kary.hahaha3.service.ForumService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

/**
 * @author:123
 */
@Service
@Qualifier("ForumService")
public class ForumServiceImpl implements ForumService {
    @Autowired
    private ThemeMapper themeMapper;
    @Autowired
    private ArticleMapper articleMapper;
    @Autowired
    private CommentMapper commentMapper;
    @Override
    public Integer publishArticle(String username,
                                  String content,
                                  String articleTopic,
                                  String themeName) throws DatabaseConnectionException {
        Integer newArticleId=articleMapper.getNewArticleId();
        Integer num1,num2;
        try{
            num1=articleMapper.publishArticle(newArticleId,username,content,articleTopic,themeName);
        }catch (Exception e){
            articleMapper.deleteArticle(newArticleId);
            throw new DatabaseConnectionException(e);
        }
        try{
            num2= themeMapper.publishArticle(themeName,newArticleId);
        }catch (Exception e){
            articleMapper.deleteArticle(newArticleId);
            throw new DatabaseConnectionException(e);
        }
        return (num1==1&&num2==1)?1:0;
    }

    @Override
    public Integer publishComment(String username, Integer articleId, String content) throws NoSuchArticleException, DatabaseConnectionException {
        Article article=articleMapper.getArticleById(articleId);
        if(article==null){
            throw new NoSuchArticleException("你评论的文章不存在或已删除");
        }
        //查看这个评论是否为文章作者所发
        Integer commentFlag=(article.getUsername().equals(username))?1:0;
        Integer res;
        Integer newCommentId=commentMapper.getNewCommentId();
        try{
            res=commentMapper.publishComment(newCommentId,username,articleId,content,commentFlag);
        }catch (Exception e){
            commentMapper.deleteComment(newCommentId);
            throw new DatabaseConnectionException(e);
        }//测试异常，测试结束后需删除
        return res;
    }
    //TODO 回复评论的代码
    @Override
    public Integer replyComment(String username,String content,Integer parentId) throws NoSuchCommentException, DatabaseConnectionException {
        Comment parentComment=commentMapper.getCommentById(parentId);
        if(parentComment==null){
            throw new NoSuchCommentException("您回复的评论不存在或已被删除");
        }
        //获取回复评论对应的文章的id，以便找出文章作者与commentFlag
        Integer articleId=parentComment.getArticleId();
        Integer commentFlag=0;
        if(articleMapper.getArticleById(articleId).getUsername().equals(username)){
            commentFlag=1;
        };
        Integer res;
        Integer newCommentId=commentMapper.getNewCommentId();
        try{
            res=commentMapper.replyComment(newCommentId,username,parentId,content,commentFlag);
        }catch (Exception e){
            commentMapper.deleteComment(newCommentId);
            throw new DatabaseConnectionException(e);
        }//测试异常，测试结束后需删除
        return res;
    }

    @Override
    public Integer publishTheme(String themeName) {
        return themeMapper.publishTheme(themeName);
    }
}

