package com.zhu.blog.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.zhu.blog.entity.domain.Comment;
import com.zhu.blog.entity.ResponseResult;
import org.springframework.stereotype.Service;


/**
 * 评论表(Comment)表服务接口
 *
 * @author makejava
 * @since 2023-03-04 21:18:11
 */
@Service
public interface CommentService extends IService<Comment> {

    /**
     * 查询所有评论
     *
     * @param commentType
     * @param articleId
     * @param pageNum
     * @param pageSize
     * @return
     */
    ResponseResult commentList(String commentType, Long articleId, int pageNum, int pageSize);

    /**
     * 发表评论
     * @return
     * @param comment
     */
    ResponseResult addComment(Comment comment);
}
