package com.kary.hahaha3.controller.forum;

import com.kary.hahaha3.controller.BaseController;
import com.kary.hahaha3.exceptions.DatabaseUpdateException;
import com.kary.hahaha3.exceptions.connection.DatabaseConnectionException;
import com.kary.hahaha3.exceptions.expired.SessionExpireException;
import com.kary.hahaha3.exceptions.forum.article.NoSuchArticleException;
import com.kary.hahaha3.exceptions.forum.comment.NoSuchCommentException;
import com.kary.hahaha3.pojo.JsonResult;
import com.kary.hahaha3.pojo.User;
import com.kary.hahaha3.service.ForumService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author:123
 */
@RestController
@RequestMapping("/forum")
@Tag(name = "论坛控制器")
public class ForumController extends BaseController {
    @Autowired
    @Qualifier("ForumService")
    private ForumService forumService;
    @PostMapping("/publishArticle")
    @Operation(summary = "发布文章", description = "API to handle article publish")
    public JsonResult publishArticle(@RequestParam("content")String content,
                                     @RequestParam("articleTopic")String articleTopic,
                                     @RequestParam("themeName")String themeName,
                                     HttpSession session) throws Exception {
        User myAccount= (User) session.getAttribute("myAccount");
        if(myAccount==null){
            throw new SessionExpireException("您尚未登陆，或登录信息已过期");
        }
        Integer res=forumService.publishArticle(myAccount.getUsername(),content,articleTopic,themeName);
        if(res==1){
            return JsonResult.ok(res,"发布文章成功");
        }else{
            throw new DatabaseUpdateException("发布文章出现问题，可能是主题已经删除");
        }
    }
    @PostMapping("/publishTheme")
    @Operation(summary = "发布主题", description = "API to handle theme publish")
    public JsonResult publishTheme(@RequestParam("themeName")String themeName) throws DatabaseUpdateException {
        Integer res=forumService.publishTheme(themeName);
        if(res==1){
            return JsonResult.ok(res,"发布主题成功");
        }else{throw new DatabaseUpdateException("发布主题出现问题");

        }
    }
    @PostMapping("/publishComment")
    @Operation(summary = "发布评论。要求前端：在页面中存储每条文章的id，即articleId。这个id在展示所有文章时会由后端给出", description = "API to handle theme publish")
    public JsonResult publishComment(@RequestParam("articleId")Integer articleId,
                                     @RequestParam("content")String content,
                                     HttpSession session) throws SessionExpireException, NoSuchArticleException, DatabaseUpdateException, DatabaseConnectionException {
        User myAccount= (User) session.getAttribute("myAccount");
        if(myAccount==null){
            throw new SessionExpireException("您尚未登陆，或登录信息已过期");
        }
        Integer res=forumService.publishComment(myAccount.getUsername(),articleId,content);
        if(res==1){
            return JsonResult.ok(res,"发布评论成功");
        }else{
            throw new DatabaseUpdateException("发布评论失败，可能是文章已经删除");
        }
    }
    @PostMapping("/replyComment")
    @Operation(summary = "回复评论。要求前端：在页面中存储每条评论的id，即commentId。这个id在展示所有评论时会由后端给出", description = "API to handle theme publish")
    public JsonResult replyComment(@RequestParam("commentId")Integer parentId,
                                   @RequestParam("content")String content,
                                   HttpSession session) throws SessionExpireException, DatabaseUpdateException, NoSuchCommentException {
        User myAccount= (User) session.getAttribute("myAccount");
        if(myAccount==null){
            throw new SessionExpireException("您尚未登陆，或登录信息已过期");
        }Integer res=forumService.replyComment(myAccount.getUsername(),content,parentId);
        if(res==1){
            return JsonResult.ok(res,"回复评论成功");
        }else{
            throw new DatabaseUpdateException("回复评论失败，可能是评论已经删除");
        }

    }
}
