package com.kary.hahaha3.controller.forum;

import com.kary.hahaha3.controller.BaseController;
import com.kary.hahaha3.exceptions.DatabaseUpdateException;
import com.kary.hahaha3.exceptions.connection.DatabaseConnectionException;
import com.kary.hahaha3.exceptions.expired.SessionExpireException;
import com.kary.hahaha3.exceptions.forum.article.NoSuchArticleException;
import com.kary.hahaha3.exceptions.forum.comment.NoSuchCommentException;
import com.kary.hahaha3.pojo.JsonResult;
import com.kary.hahaha3.pojo.User;
import com.kary.hahaha3.pojo.vo.PublishArticleJSON;
import com.kary.hahaha3.pojo.vo.PublishCommentJSON;
import com.kary.hahaha3.pojo.vo.ReplyCommentJSON;
import com.kary.hahaha3.service.ForumService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.*;

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
    public JsonResult publishArticle(@RequestBody PublishArticleJSON publishArticleJSON,
                                     HttpSession session) throws Exception {
        User myAccount= (User) session.getAttribute("myAccount");
        String articleTopic=publishArticleJSON.getArticleTopic();
        String themeName=publishArticleJSON.getThemeName();
        String content=publishArticleJSON.getContent();
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
    public JsonResult publishTheme(@RequestBody String themeName) throws DatabaseUpdateException {
        Integer res=forumService.publishTheme(themeName);
        if(res==1){
            return JsonResult.ok(res,"发布主题成功");
        }else{throw new DatabaseUpdateException("发布主题出现问题");

        }
    }
    @PostMapping("/publishComment")
    @Operation(summary = "发布评论。要求前端：在页面中存储每条文章的id，即articleId。这个id在展示所有文章时会由后端给出", description = "API to handle theme publish")
    public JsonResult publishComment(@RequestBody PublishCommentJSON publishCommentJSON,
                                     HttpSession session) throws SessionExpireException, NoSuchArticleException, DatabaseUpdateException, DatabaseConnectionException {
        User myAccount= (User) session.getAttribute("myAccount");
        Integer articleId=publishCommentJSON.getArticleId();
        String content= publishCommentJSON.getContent();
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
    public JsonResult replyComment(@RequestBody ReplyCommentJSON replyCommentJSON,
                                   HttpSession session) throws SessionExpireException, DatabaseUpdateException, NoSuchCommentException {
        User myAccount= (User) session.getAttribute("myAccount");
        Integer parentId= replyCommentJSON.getParentId();
        String content=replyCommentJSON.getContent();
        if(myAccount==null){
            throw new SessionExpireException("您尚未登陆，或登录信息已过期");
        }Integer res=forumService.replyComment(myAccount.getUsername(),content,parentId);
        if(res==1){
            return JsonResult.ok(res,"回复评论成功");
        }else{
            throw new DatabaseUpdateException("回复评论失败，可能是评论已经删除");
        }
    }
    @GetMapping("/themes/{page}")
    @Operation(summary = "分页展现所有主题")
    public JsonResult getAllThemeByPage(@PathVariable Integer page){
        return JsonResult.ok(forumService.getAllThemeByPage(page),"这是所有的主题");
    }
    @GetMapping("/articles/{page}")
    @Operation(summary = "分页展现所有文章，要求前端给我主题名themeName")
    public JsonResult getAllArticleByPage(@PathVariable Integer page,@RequestParam("themeName")String themeName){
        return JsonResult.ok(forumService.getAllArticleByPage(page,themeName),"这是所有的文章");
    }
    @GetMapping("/comments/{page}")
    @Operation(summary = "分页展现所有评论，要求前端给我文章id即articleId")
    public JsonResult getAllCommentByPage(@PathVariable Integer page,@RequestParam("articleId")Integer articleId){
        return JsonResult.ok(forumService.getAllCommentByPage(page,articleId),"这是所有的评论");
    }
    @PostMapping("/articles/{articleId}/{operation}")
    @Operation(summary = "点赞或点踩文章, operation为1代表点赞，0代表点踩")
    public Boolean likeOrDislikeArticle(@PathVariable Integer articleId,@PathVariable int operation){
        if(operation==1){
            return forumService.likeArticle(articleId)==1;
        }else{
            return forumService.dislikeArticle(articleId)==1;
        }
    }
    @PostMapping("/articles/{commentId}/{operation}")
    @Operation(summary = "点赞或点踩评论, operation为1代表点赞，0代表点踩")
    public Boolean likeOrDislikeComment(@PathVariable Integer commentId,@PathVariable int operation){
        if(operation==1){
            return forumService.likeComment(commentId)==1;
        }else{
            return forumService.dislikeComment(commentId)==1;
        }
    }
}
