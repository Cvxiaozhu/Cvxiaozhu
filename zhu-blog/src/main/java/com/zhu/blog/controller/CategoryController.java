package com.zhu.blog.controller;

import com.zhu.blog.annotation.SystemLog;
import com.zhu.blog.entity.ResponseResult;
import com.zhu.blog.service.CategoryService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author GH
 * @Description:
 * @date 2023/2/16 21:06
 */
@RestController
@RequestMapping("/category")
@Api(tags = "分类接口", description = "获取文章的分类情况")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @GetMapping("/getCategoryList")
    @SystemLog(businessName = "获取分类列表")
    @ApiOperation(value = "获取分类列表")
    public ResponseResult getCategoryList() {
        return categoryService.getCategoryList();
    }
}
