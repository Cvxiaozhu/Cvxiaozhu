package com.zhu.blog.controller;

import com.zhu.blog.annotation.SystemLog;
import com.zhu.blog.constants.SystemConstants;
import com.zhu.blog.entity.domain.Comment;
import com.zhu.blog.entity.ResponseResult;
import com.zhu.blog.entity.dto.CommentDto;
import com.zhu.blog.service.CommentService;
import com.zhu.blog.utils.BeanCopyUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author GH
 * @Description:
 * @date 2023/3/4 21:23
 */
@RestController
@RequestMapping("/comment")
@Api(tags = "评论接口", description = "查看文章以及友链的相关评论")
public class CommentController {

    @Autowired
    private CommentService commentService;

    @GetMapping("/commentList")
    @SystemLog(businessName = "查询文章评论列表")
    @ApiOperation(value = "查询文章评论列表")
    public ResponseResult commentList(Long articleId, int pageNum, int pageSize) {
        return commentService.commentList(SystemConstants.ARTICLE_COMMENT, articleId, pageNum, pageSize);
    }

    @GetMapping("/linkCommentList")
    @SystemLog(businessName = "友链评论查询")
    @ApiOperation(value = "友链评论查询")
    public ResponseResult linkCommentList(Integer pageNum,Integer pageSize){
        return commentService.commentList(SystemConstants.LINK_COMMENT,null,pageNum,pageSize);
    }

    @PostMapping
    @SystemLog(businessName = "发表评论")
    @ApiOperation(value = "发表评论")
    public ResponseResult addComment(@RequestBody CommentDto commentDto) {
        Comment comment = BeanCopyUtils.copyBean(commentDto, Comment.class);
        return commentService.addComment(comment);
    }

}
