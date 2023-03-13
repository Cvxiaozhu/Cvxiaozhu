package com.zhu.blog.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zhu.blog.entity.domain.ArticleTag;
import org.apache.ibatis.annotations.Param;

import java.util.List;


/**
 * 文章标签关联表(ArticleTag)表数据库访问层
 *
 * @author makejava
 * @since 2023-03-11 10:31:48
 */
public interface ArticleTagMapper extends BaseMapper<ArticleTag> {

    boolean deleteArticleAndTag(@Param("articleId") Long articleId, @Param("tags") List<Long> tags);
}

