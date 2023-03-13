package com.zhu.blog.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zhu.blog.constants.SystemConstants;
import com.zhu.blog.entity.domain.Comment;
import com.zhu.blog.entity.ResponseResult;
import com.zhu.blog.entity.vo.CommentVo;
import com.zhu.blog.entity.vo.PageVo;
import com.zhu.blog.enums.AppHttpCodeEnum;
import com.zhu.blog.handler.exception.SystemException;
import com.zhu.blog.mapper.CommentMapper;
import com.zhu.blog.service.CommentService;
import com.zhu.blog.service.UserService;
import com.zhu.blog.utils.BeanCopyUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;

/**
 * 评论表(Comment)表服务实现类
 *
 * @author makejava
 * @since 2023-03-04 21:18:11
 */
@Service("commentService")
public class CommentServiceImpl extends ServiceImpl<CommentMapper, Comment> implements CommentService {

    @Autowired
    private UserService userService;

    @Override
    public ResponseResult addComment(Comment comment) {
        //评论内容不能为空
        if(!StringUtils.hasText(comment.getContent())){
            throw new SystemException(AppHttpCodeEnum.CONTENT_NOT_NULL);
        }
        save(comment);
        return ResponseResult.okResult();
    }

    @Override
    public ResponseResult commentList(String commentType, Long articleId, int pageNum, int pageSize) {

        // 根据id查询文章
        LambdaQueryWrapper<Comment> queryWrapper = new LambdaQueryWrapper<>();

        queryWrapper.eq(commentType.equals(SystemConstants.ARTICLE_COMMENT), Comment::getArticleId, articleId);
        // 查询rootId 为 -1 的根评论
        queryWrapper.eq(Comment::getRootId, SystemConstants.ROOT_COMMENT);

        // 评论类型
        queryWrapper.eq(Comment::getType, commentType);

        // 分页
        Page<Comment> pages = new Page<>(pageNum, pageSize);
        page(pages, queryWrapper);

        List<CommentVo> commentVos = toCommentVoList(pages.getRecords());
        for (CommentVo commentVo : commentVos) {
            List<CommentVo> children = getChildren(commentVo.getId());
            commentVo.setChildren(children);
        }

        PageVo pageVo = new PageVo(commentVos, pages.getTotal());

        return ResponseResult.okResult(pageVo);
    }



    /**
     * 根据根评论id查询子评论的集合
     *
     * @param id 根评论id
     * @return
     */
    private List<CommentVo> getChildren(Long id) {
        LambdaQueryWrapper<Comment> queryWrapper = new LambdaQueryWrapper<>();

        queryWrapper.eq(Comment::getRootId, id);
        queryWrapper.orderByAsc(Comment::getCreateTime);
        List<Comment> comments = list(queryWrapper);

        return toCommentVoList(comments);
    }

    /**
     * 对CommentVo中某些属性赋值
     *
     * @param list
     * @return
     */
    private List<CommentVo> toCommentVoList(List<Comment> list) {
        List<CommentVo> commentVos = BeanCopyUtils.copyBeanList(list, CommentVo.class);
        //遍历vo集合
        for (CommentVo commentVo : commentVos) {
            //通过creatyBy查询用户的昵称并赋值
            String nickName = userService.getById(commentVo.getCreateBy()).getNickName();
            commentVo.setUsername(nickName);
            //通过toCommentUserId查询用户的昵称并赋值
            //如果toCommentUserId不为-1才进行查询
            if (commentVo.getToCommentUserId() != -1) {
                String toCommentUserName = userService.getById(commentVo.getToCommentUserId()).getNickName();
                commentVo.setToCommentUserName(toCommentUserName);
            }
        }
        return commentVos;
    }
}
