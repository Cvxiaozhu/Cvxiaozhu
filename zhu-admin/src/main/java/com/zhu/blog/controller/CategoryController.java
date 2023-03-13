package com.zhu.blog.controller;

import com.zhu.blog.annotation.SystemLog;
import com.zhu.blog.entity.ResponseResult;
import com.zhu.blog.entity.domain.Category;
import com.zhu.blog.entity.dto.CategoryDto;
import com.zhu.blog.service.CategoryService;
import com.zhu.blog.utils.BeanCopyUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;

/**
 * @author GH
 * @Description:
 * @date 2023/3/11 10:08
 */
@RestController
@RequestMapping("/content/category")
@Api(tags = "分类管理")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;


    @GetMapping("/listAllCategory")
    @SystemLog(businessName = "新增博文处列表查询")
    @ApiOperation(value = "分类列表", notes = "新增博文处列表查询")
    public ResponseResult listCategory() {
        return categoryService.listCategory();
    }

    @GetMapping("/export")
    @SystemLog(businessName = "新增博文处列表查询")
    @PreAuthorize("@ps.hasPermission('content:category:export')")
    @ApiOperation(value = "导出分类", notes = "使用EasyExcel分类导出")
    public void export(HttpServletResponse response) {
        categoryService.export(response);
    }

    @GetMapping("/list")
    @SystemLog(businessName = "列表查询")
    @ApiOperation(value = "分类列表", notes = "分页列表查询")
    public ResponseResult listCategory(Integer pageNum, Integer pageSize, String name, String status) {
        return categoryService.listCategories(pageNum, pageSize, name, status);
    }

    @PostMapping
    @SystemLog(businessName = "添加分类")
    @ApiOperation(value = "添加分类", notes = "添加分类")
    public ResponseResult addCategory(@RequestBody CategoryDto categoryDto) {
        Category category = BeanCopyUtils.copyBean(categoryDto, Category.class);
        return categoryService.addCategory(category);
    }

    @GetMapping("/{id}")
    @SystemLog(businessName = "查询分类")
    @ApiOperation(value = "添加分类", notes = "添加分类")
    public ResponseResult getCategory(@PathVariable("id") Long categoryId) {
        return categoryService.getCategory(categoryId);
    }

    @PutMapping
    @SystemLog(businessName = "更新分类信息")
    @ApiOperation(value = "更新分类信息", notes = "更新分类信息")
    public ResponseResult updateCategory(@RequestBody CategoryDto categoryDto) {
        return categoryService.updateCategory(categoryDto);
    }

    @PutMapping("/changeStatus")
    @SystemLog(businessName = "修改分类状态")
    @ApiOperation(value = "修改分类状态", notes = "修改分类状态")
    public ResponseResult changStatus(@RequestBody CategoryDto categoryDto) {
        Category category = BeanCopyUtils.copyBean(categoryDto, Category.class);
        return categoryService.changStatus(category);
    }

    @DeleteMapping("/{ids}")
    public ResponseResult deleteCategory(@PathVariable("ids") Long[] categoryIds) {
        return categoryService.deleteCategory(categoryIds);
    }

}
