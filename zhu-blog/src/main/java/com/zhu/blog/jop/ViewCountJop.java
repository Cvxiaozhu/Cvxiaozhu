package com.zhu.blog.jop;

import com.zhu.blog.constants.SystemConstants;
import com.zhu.blog.entity.domain.Article;
import com.zhu.blog.service.ArticleService;
import com.zhu.blog.utils.RedisCache;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author GH
 * @Description:
 * @date 2023/3/6 22:14
 */
@Component
public class ViewCountJop {

    @Autowired
    private RedisCache redisCache;

    @Autowired
    private ArticleService articleService;

    /**
     * cron表达式 每10分钟执行以下代码，更新数据库中的viewCount
     */
    @Scheduled(cron = "0 0/10 * * * ?")
    public void updateViewCount() {
        Map<String, Integer> cacheMap = redisCache.getCacheMap(SystemConstants.ARTICLE_VIEW_COUNT);

        /*
          在实体类中添加@Builer注解
          能够进行如下对象构造
          链式构造

          Article.builder().id(Long.parseLong(entry.getKey())).viewCount(entry.getValue().longValue()).build())
         */
        List<Article> articles = cacheMap.entrySet()
                .stream()
                .map(entry -> Article.builder()
                        .id(Long.parseLong(entry.getKey()))
                        .viewCount(entry.getValue().longValue()).build())
                .collect(Collectors.toList());
        articleService.updateBatchById(articles);

    }
}
