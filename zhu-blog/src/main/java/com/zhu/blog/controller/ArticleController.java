package com.zhu.blog.controller;

import com.zhu.blog.annotation.SystemLog;
import com.zhu.blog.entity.ResponseResult;
import com.zhu.blog.service.ArticleService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author GH
 * @Description:
 * @date 2023/2/16 16:34
 */
@RestController
@RequestMapping("/article")
@Api(tags = "文章接口", description = "文章相关内容查询")
public class ArticleController {

    @Autowired
    private ArticleService articleService;

    @GetMapping("/hotArticleList")
    @SystemLog(businessName = "热门文章浏览")
    @ApiOperation(value = "热门文章浏览")
    public ResponseResult hotArticleList() {
        return articleService.hotArticleList();
    }

    @GetMapping("/articleList")
    @SystemLog(businessName = "文章列表查询")
    @ApiOperation(value = "文章列表查询")
    public ResponseResult articleList(int pageNum, int pageSize, Long categoryId) {
        return articleService.articleList(pageNum,pageSize,categoryId);
    }

    @GetMapping("/{id}")
    @SystemLog(businessName = "文章详情查询")
    @ApiOperation(value = "文章详情查询")
    @ApiImplicitParam(name = "id", value = "文章Id")
    public ResponseResult getArticleDetail(@PathVariable("id") Long id) {
        return articleService.getArticleDetail(id);
    }

    @PutMapping("/updateViewCount/{id}")
    @SystemLog(businessName = "文章浏览量更新")
    @ApiOperation(value = "文章浏览量更新")
    public ResponseResult updateViewCount(@PathVariable("id") Long id) {
        return articleService.updateViewCount(id);
    }



}
