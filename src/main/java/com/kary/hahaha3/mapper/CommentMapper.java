package com.kary.hahaha3.mapper;

import com.kary.hahaha3.pojo.Comment;
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
@Tag(name = "CommentMapper")
public interface CommentMapper {
    //TODO 利用Service层判断commentFlag的值
    Integer publishComment(@Param("commentId")Integer commentId,
                           @Param("username")String username,
                           @Param("articleId")Integer articleId,
                           @Param("content")String content,
                            @Param("commentFlag")Integer commentFlag);
    Integer replyComment(@Param("commentId")Integer commentId,
                         @Param("parentCommentUsername") String parentCommentUsername,
                         @Param("username")String username,
                         @Param("parentId")Integer parentId,
                         @Param("articleId")Integer articleId,
                         @Param("content")String content,
                         @Param("commentFlag")Integer commentFlag);
    Integer getNewCommentId();
    Integer deleteComment(@Param("commentId")Integer commentId);
    Comment getCommentById(@Param("commentId")Integer commentId);
    List<Comment> getAllCommentByPage(@Param("page")Integer page,@Param("articleId")Integer articleId);
}
