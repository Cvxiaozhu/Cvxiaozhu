package com.zhu.blog.controller;

import com.zhu.blog.annotation.SystemLog;
import com.zhu.blog.entity.ResponseResult;
import com.zhu.blog.entity.dto.ArticleDto;
import com.zhu.blog.entity.vo.PageVo;
import com.zhu.blog.service.ArticleService;
import com.zhu.blog.utils.WebUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author GH
 * @Description:
 * @date 2023/3/11 10:04
 */
@RestController
@RequestMapping("/content/article")
@Api(tags = "文章管理")
public class ArticleController {

    @Autowired
    private ArticleService articleService;

    @PostMapping
    @SystemLog(businessName = "新增博文")
    @ApiOperation(value = "新增博文", notes = "添加博文")
    public ResponseResult addArticle(@RequestBody ArticleDto articleDto) {
        return articleService.addArticle(articleDto);
    }

    @GetMapping("/list")
    @SystemLog(businessName = "博文列表查询")
    public ResponseResult<PageVo> listArticle(Integer pageNum, Integer pageSize, String title, String summary) {
        return articleService.listArticle(pageNum, pageSize, title, summary);
    }

    @GetMapping("/{id}")
    @SystemLog(businessName = "博文查询")
    public ResponseResult getArticle(@PathVariable("id") Long id) {
        return articleService.getArticle(id);
    }

    @PutMapping
    @SystemLog(businessName = "更新博文")
    public ResponseResult updateArticle(@RequestBody ArticleDto articleDto) {
        return articleService.updateArticle(articleDto);
    }

    @DeleteMapping("/{ids}")
    @SystemLog(businessName = "删除博文")
    public ResponseResult deleteArticleById(@PathVariable("ids") String ids) {
        List<Long> idStrToList = WebUtils.idStrToList(ids);
        return articleService.deleteArticleById(idStrToList);
    }

}
