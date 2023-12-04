package com.kary.hahaha3.service.impl;

import com.kary.hahaha3.exceptions.connection.DatabaseConnectionException;
import com.kary.hahaha3.exceptions.forum.article.NoSuchArticleException;
import com.kary.hahaha3.exceptions.forum.comment.NoSuchCommentException;
import com.kary.hahaha3.exceptions.forum.theme.NoSuchThemeException;
import com.kary.hahaha3.mapper.ArticleMapper;
import com.kary.hahaha3.mapper.CommentMapper;
import com.kary.hahaha3.mapper.ThemeMapper;
import com.kary.hahaha3.pojo.Article;
import com.kary.hahaha3.pojo.Comment;
import com.kary.hahaha3.pojo.Theme;
import com.kary.hahaha3.service.ForumService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

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
    @Transactional
    public Integer publishArticle(String username,
                                  String content,
                                  String articleTopic,
                                  String themeName) throws NoSuchThemeException {
        Theme theme=themeMapper.getThemeByName(themeName);
        if(theme==null){
            throw new NoSuchThemeException("您发布的主题不存在或已被删除");
        }
        Integer newArticleId=articleMapper.getNewArticleId();
        if(newArticleId==null){
            newArticleId=1;
        }
        Integer num1,num2;
        num1=articleMapper.publishArticle(newArticleId,username,content,articleTopic,themeName);
        num2= themeMapper.publishArticle(themeName,newArticleId);

        return (num1==1&&num2==1)?1:0;
    }

    @Override
    @Transactional
    public Integer publishComment(String username, Integer articleId, String content) throws NoSuchArticleException, DatabaseConnectionException {
        Article article=articleMapper.getArticleById(articleId);
        if(article==null){
            throw new NoSuchArticleException("你评论的文章不存在或已删除");
        }
        Integer commentFlag=0,newCommentId=1;
        //查看这个评论是否为文章作者所发
        commentFlag = (article.getUsername().equals(username)) ? 1 : 0;

        Integer res;
        newCommentId=commentMapper.getNewCommentId();
        if(newCommentId==null){
            newCommentId=1;
        }
        res=commentMapper.publishComment(newCommentId,username,articleId,content,commentFlag);
        return res;
    }
    @Override
    @Transactional
    public Integer replyComment(String username,String content,Integer parentId) throws NoSuchCommentException {
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
        res=commentMapper.replyComment(newCommentId,parentComment.getCommentUsername(),username,parentId,articleId,content,commentFlag);
        return res;
    }

    @Override
    @Transactional
    public Integer publishTheme(String themeName) {
        return themeMapper.publishTheme(themeName);
    }

    @Override
    public List<Theme> getAllThemeByPage(Integer page) {
        page=(page-1)*10;
        return themeMapper.getAllThemeByPage(page);
    }

    @Override
    public List<Article> getAllArticleByPage(Integer page, String themeName) {
        page=(page-1)*10;
        return articleMapper.getAllArticleByPage(page,themeName);
    }

    @Override
    public List<Comment> getAllCommentByPage(Integer page,Integer articleId) {
        page=(page-1)*10;
        return commentMapper.getAllCommentByPage(page,articleId);
    }
}

