package com.zhu.blog.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.zhu.blog.entity.ResponseResult;
import com.zhu.blog.entity.domain.Article;
import com.zhu.blog.entity.dto.ArticleDto;
import com.zhu.blog.entity.vo.PageVo;

import java.util.List;


/**
 * 文章表(Article)表服务接口
 *
 * @author makejava
 * @since 2023-02-16 15:48:10
 */
public interface ArticleService extends IService<Article> {

    /**
     * 热门文章查询
     *
     * @return
     */
    ResponseResult hotArticleList();

    /**
     * 文章列表查询
     *
     * @param pageNum
     * @param pageSize
     * @param categoryId
     * @return
     */
    ResponseResult articleList(int pageNum, int pageSize, Long categoryId);


    /**
     * 文章详情查询
     *
     * @param id
     * @return
     */
    ResponseResult getArticleDetail(Long id);

    /**
     * 浏览量更新
     *
     * @param id
     * @return
     */
    ResponseResult updateViewCount(Long id);


    /**
     * 添加博文
     *
     * @param articleDto
     * @return
     */
    ResponseResult addArticle(ArticleDto articleDto);

    /**
     * 后台查询文章列表
     * @param pageNum
     * @param pageSize
     * @param title
     * @param summary
     * @return
     */
    ResponseResult<PageVo> listArticle(Integer pageNum, Integer pageSize, String title, String summary);

    /**
     * 根据id查询篇文章
     * @param id
     * @return
     */
    ResponseResult getArticle(Long id);

    /**
     * 修改文章
     * @param articleDto
     * @return
     */
    ResponseResult updateArticle(ArticleDto articleDto);

    /**
     * 根据id删除文章， 也可批量删除
     * @param idStrToList
     * @return
     */
    ResponseResult deleteArticleById(List<Long> idStrToList);
}
