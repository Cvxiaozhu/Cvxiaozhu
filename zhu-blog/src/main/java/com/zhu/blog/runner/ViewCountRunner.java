package com.zhu.blog.runner;

import com.zhu.blog.constants.SystemConstants;
import com.zhu.blog.entity.domain.Article;
import com.zhu.blog.mapper.ArticleMapper;
import com.zhu.blog.utils.RedisCache;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author GH
 * @Description: 应用启动时执行以下代码
 * @date 2023/3/6 21:09
 */
@Component
public class ViewCountRunner implements CommandLineRunner {

    @Autowired
    private ArticleMapper articleMapper;

    @Autowired
    private RedisCache redisCache;

    /**
     * 自动执行的方法
     */
    @Override
    public void run(String... args) throws Exception {
        // 查询文章信息
        List<Article> articles = articleMapper.selectList(null);

        // stream 流转换 成map集合
        Map<String, Integer> viewCount = articles.stream()
                .collect(Collectors.toMap(article -> article.getId().toString(),
                        article -> article.getViewCount().intValue()));

        // 将数据库中对应key的值更新到 redis 中
        redisCache.setCacheMap(SystemConstants.ARTICLE_VIEW_COUNT, viewCount);
    }
}
