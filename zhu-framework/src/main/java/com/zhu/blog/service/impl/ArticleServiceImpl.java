package com.zhu.blog.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zhu.blog.constants.SystemConstants;
import com.zhu.blog.entity.ResponseResult;
import com.zhu.blog.entity.domain.Article;
import com.zhu.blog.entity.domain.ArticleTag;
import com.zhu.blog.entity.domain.Category;
import com.zhu.blog.entity.dto.ArticleDto;
import com.zhu.blog.entity.vo.*;
import com.zhu.blog.mapper.ArticleMapper;
import com.zhu.blog.mapper.ArticleTagMapper;
import com.zhu.blog.service.ArticleService;
import com.zhu.blog.service.ArticleTagService;
import com.zhu.blog.service.CategoryService;
import com.zhu.blog.utils.BeanCopyUtils;
import com.zhu.blog.utils.RedisCache;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * 文章表(Article)表服务实现类
 *
 * @author makejava
 * @since 2023-02-16 15:48:10
 */
@Service("articleService")
@Transactional(
        rollbackFor = Exception.class
)
public class ArticleServiceImpl extends ServiceImpl<ArticleMapper, Article> implements ArticleService {

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private RedisCache redisCache;

    @Autowired
    private ArticleTagService articleTagService;

    @Autowired
    private ArticleTagMapper articleTagMapper;

    @Override
    public ResponseResult hotArticleList() {
        LambdaQueryWrapper<Article> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper
                .eq(Article::getStatus, SystemConstants.ARTICLE_STATUS_NORMAL)
                .orderByDesc(Article::getViewCount);
        Page<Article> pages = new Page<>(1, 10);
        page(pages, lambdaQueryWrapper);
        List<Article> records = pages.getRecords();

        for (Article article : records) {
            String id = article.getId().toString();
            Integer viewCount = redisCache.getCacheMapValue(SystemConstants.ARTICLE_VIEW_COUNT, id);
            article.setViewCount(Long.valueOf(viewCount));
        }
        List<HotArticleVo> hotArticleVos = BeanCopyUtils.copyBeanList(records, HotArticleVo.class);

        return ResponseResult.okResult(hotArticleVos);
    }

    @Override
    public ResponseResult articleList(int pageNum, int pageSize, Long categoryId) {
        LambdaQueryWrapper<Article> queryWrapper = new LambdaQueryWrapper<>();

        queryWrapper.eq(Objects.nonNull(categoryId) && categoryId > 0, Article::getCategoryId, categoryId);
        queryWrapper.eq(Article::getStatus, "0");
        queryWrapper.orderByDesc(Article::getIsTop);

        Page<Article> pages = new Page<>(pageNum, pageSize);

        page(pages, queryWrapper);

        List<Article> articles = pages.getRecords();

        for (Article article : articles) {
            String id = article.getId().toString();
            Integer viewCount = redisCache.getCacheMapValue(SystemConstants.ARTICLE_VIEW_COUNT, id);
            article.setViewCount(Long.valueOf(viewCount));
        }

        List<Article> list = articles.stream()
                .map(article -> article.setCategoryName(categoryService.getById(article.getCategoryId()).getName()))
                .collect(Collectors.toList());
        List<ArticleListVo> articleListVos = BeanCopyUtils.copyBeanList(list, ArticleListVo.class);

        PageVo pageVo = new PageVo(articleListVos, pages.getTotal());
        return ResponseResult.okResult(pageVo);
    }

    @Override
    public ResponseResult getArticleDetail(Long id) {
        Article article = getById(id);
        // 通过redis进行返回浏览量
        Integer viewCount = redisCache.getCacheMapValue(SystemConstants.ARTICLE_VIEW_COUNT, id.toString());

        article.setViewCount(viewCount.longValue());

        ArticleDetailVo articleDetailVo = BeanCopyUtils.copyBean(article, ArticleDetailVo.class);

        Category category = categoryService.getById(articleDetailVo.getCategoryId());
        String name = category.getName();

        articleDetailVo.setCategoryName(name);
        return ResponseResult.okResult(articleDetailVo);
    }

    @Override
    public ResponseResult updateViewCount(Long id) {
        //更新redis中对应 id的浏览量
        redisCache.incrementCacheMapValue(SystemConstants.ARTICLE_VIEW_COUNT, id.toString(), 1);
        return ResponseResult.okResult();
    }

    @Override
    public ResponseResult addArticle(ArticleDto articleDto) {
        // 封装成Article
        Article article = BeanCopyUtils.copyBean(articleDto, Article.class);
        save(article);

        // 将其中的TagId列表映射到ArticleTag表中并保存
        List<ArticleTag> articleTags = articleDto.getTags().stream()
                .map(tagId -> new ArticleTag(article.getId(), tagId))
                .collect(Collectors.toList());

        articleTagService.saveBatch(articleTags);
        return ResponseResult.okResult();
    }

    @Override
    public ResponseResult<PageVo> listArticle(Integer pageNum, Integer pageSize, String title, String summary) {

        // 查询文章
        LambdaQueryWrapper<Article> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.like(StringUtils.hasText(title), Article::getTitle, title);
        queryWrapper.like(StringUtils.hasText(summary), Article::getSummary, summary);

        // 分页查询
        Page page = new Page(pageNum, pageSize);
        page(page, queryWrapper);

        // 封装成ArticleVo类型
        List<ArticleVo> articleVos = BeanCopyUtils.copyBeanList(page.getRecords(), ArticleVo.class);
        return ResponseResult.okResult(new PageVo(articleVos, page.getTotal()));
    }

    @Override
    public ResponseResult getArticle(Long id) {
        // 根据id查询文章
        Article article = getById(id);
        // 查询关联的标签Id
        LambdaQueryWrapper<ArticleTag> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(ArticleTag::getArticleId, id);
        List<ArticleTag> articleTagList = articleTagService.list(queryWrapper);
        List<Long> tagIdList = articleTagList.stream().map(ArticleTag::getTagId).collect(Collectors.toList());

        // 封装成ArticleVo
        ArticleVo articleVo = BeanCopyUtils.copyBean(article, ArticleVo.class);
        articleVo.setTags(tagIdList);

        return ResponseResult.okResult(articleVo);
    }

    @Override
    public ResponseResult updateArticle(ArticleDto articleDto) {
        Article article = BeanCopyUtils.copyBean(articleDto, Article.class);
        updateById(article);
        // 同时更新文章标签表
        removeArticleTag(article.getId());
        List<ArticleTag> articleTagList = articleDto.getTags().stream()
                .map(aLong -> new ArticleTag(article.getId(), aLong)).collect(Collectors.toList());
        articleTagService.saveBatch(articleTagList);
        return ResponseResult.okResult();
    }

    @Override
    public ResponseResult deleteArticleById(List<Long> idStrToList) {
        // 删除文章
        removeByIds(idStrToList);
        // 删除文章id对应的标签 文章和标签的关系为一对多
        // idStrToList.forEach(this::removeArticleTag);
        return ResponseResult.okResult();
    }

    /**
     * 根据文章id删除文章标签对应的关系
     *
     * @param articleId
     */
    private void removeArticleTag(Long articleId) {
        LambdaQueryWrapper<ArticleTag> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(ArticleTag::getArticleId, articleId);
        List<ArticleTag> list = articleTagService.list(queryWrapper);
        List<Long> tags = list.stream()
                .map(ArticleTag::getTagId)
                .collect(Collectors.toList());
        //先删除再插入
        articleTagMapper.deleteArticleAndTag(articleId, tags);
    }
}
