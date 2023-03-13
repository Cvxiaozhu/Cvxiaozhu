package com.zhu.blog.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zhu.blog.entity.domain.Article;
import org.springframework.stereotype.Repository;


/**
 * 文章表(Article)表数据库访问层
 *
 * @author makejava
 * @since 2023-02-16 15:48:05
 */
@Repository
public interface ArticleMapper extends BaseMapper<Article> {

}

