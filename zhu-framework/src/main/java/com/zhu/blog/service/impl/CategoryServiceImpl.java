package com.zhu.blog.service.impl;

import com.alibaba.excel.EasyExcel;
import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zhu.blog.constants.SystemConstants;
import com.zhu.blog.entity.domain.Article;
import com.zhu.blog.entity.domain.Category;
import com.zhu.blog.entity.ResponseResult;
import com.zhu.blog.entity.dto.CategoryDto;
import com.zhu.blog.entity.vo.CategoryVo;
import com.zhu.blog.entity.vo.ExcelCategoryVo;
import com.zhu.blog.entity.vo.PageVo;
import com.zhu.blog.enums.AppHttpCodeEnum;
import com.zhu.blog.mapper.CategoryMapper;
import com.zhu.blog.service.ArticleService;
import com.zhu.blog.service.CategoryService;
import com.zhu.blog.utils.BeanCopyUtils;
import com.zhu.blog.utils.WebUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletResponse;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * 分类表(Category)表服务实现类
 *
 * @author makejava
 * @since 2023-02-16 21:05:46
 */
@Service("categoryService")
public class CategoryServiceImpl extends ServiceImpl<CategoryMapper, Category> implements CategoryService {

    @Autowired
    private ArticleService articleService;

    @Override
    public ResponseResult getCategoryList() {

        LambdaQueryWrapper<Article> articleWrapper = new LambdaQueryWrapper<>();
        articleWrapper.eq(Article::getStatus, SystemConstants.ARTICLE_STATUS_NORMAL);
        List<Article> articleList = articleService.list(articleWrapper);
        //获取文章的分类id，并且去重
        Set<Long> categoryIds = articleList.stream()
                .map(Article::getCategoryId)
                .collect(Collectors.toSet());

        //查询分类表
        List<Category> categories = listByIds(categoryIds);
        categories = categories.stream().
                filter(category -> SystemConstants.STATUS_NORMAL.equals(category.getStatus()))
                .collect(Collectors.toList());
        //封装vo
        List<CategoryVo> categoryVos = BeanCopyUtils.copyBeanList(categories, CategoryVo.class);

        return ResponseResult.okResult(categoryVos);
    }

    @Override
    public ResponseResult listCategory() {
        LambdaQueryWrapper<Category> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Category::getStatus, SystemConstants.STATUS_NORMAL);
        List<Category> categories = list(queryWrapper);
        List<CategoryVo> categoryVos = BeanCopyUtils.copyBeanList(categories, CategoryVo.class);
        return ResponseResult.okResult(categoryVos);
    }

    @Override
    public void export(HttpServletResponse response) {
        try {
            WebUtils.setDownLoadHeader("分类.xlsx", response);
            List<ExcelCategoryVo> excelCategoryVos = BeanCopyUtils.copyBeanList(list(), ExcelCategoryVo.class);
            EasyExcel.write(response.getOutputStream(), ExcelCategoryVo.class)
                    .autoCloseStream(Boolean.FALSE)
                    .sheet("分类导出")
                    .doWrite(excelCategoryVos);

        } catch (Exception e) {
            ResponseResult result = ResponseResult.errorResult(AppHttpCodeEnum.SYSTEM_ERROR);
            WebUtils.renderString(response, JSON.toJSONString(result));
        }
    }

    @Override
    public ResponseResult listCategories(Integer pageNum, Integer pageSize, String categoryName, String status) {

        LambdaQueryWrapper<Category> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.like(StringUtils.hasText(categoryName), Category::getName, categoryName);
        queryWrapper.like(StringUtils.hasText(status), Category::getStatus, status);

        Page<Category> page = new Page<>(pageNum, pageSize);
        page(page, queryWrapper);

        PageVo pageVo = new PageVo(page.getRecords(), page.getTotal());

        return ResponseResult.okResult(pageVo);
    }

    @Override
    public ResponseResult addCategory(Category category) {
        save(category);
        return ResponseResult.okResult();
    }

    @Override
    public ResponseResult getCategory(Long categoryId) {
        Category category = getById(categoryId);
        CategoryVo categoryVo = BeanCopyUtils.copyBean(category, CategoryVo.class);
        return ResponseResult.okResult(categoryVo);
    }

    @Override
    public ResponseResult updateCategory(CategoryDto categoryDto) {
        Category category = BeanCopyUtils.copyBean(categoryDto, Category.class);
        updateById(category);
        return ResponseResult.okResult();
    }

    @Override
    public ResponseResult changStatus(Category category) {
        updateById(category);
        return ResponseResult.okResult();
    }

    @Override
    public ResponseResult deleteCategory(Long[] categoryIds) {
        List<Long> ids = Arrays.stream(categoryIds).collect(Collectors.toList());
        removeByIds(ids);
        return ResponseResult.okResult();
    }
}
