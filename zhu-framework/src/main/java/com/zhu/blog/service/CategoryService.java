package com.zhu.blog.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.zhu.blog.entity.domain.Category;
import com.zhu.blog.entity.ResponseResult;
import com.zhu.blog.entity.dto.CategoryDto;

import javax.servlet.http.HttpServletResponse;


/**
 * 分类表(Category)表服务接口
 *
 * @author makejava
 * @since 2023-02-16 21:05:46
 */
public interface CategoryService extends IService<Category> {

    /**
     * 分类列表查询
     * @return
     */
    ResponseResult getCategoryList();

    /**
     * 分类查询
     * @return
     */
    ResponseResult listCategory();

    /**
     * 导出分类
     * @param response
     */
    void export(HttpServletResponse response);

    ResponseResult listCategories(Integer pageNum, Integer pageSize, String categoryName, String status);

    ResponseResult addCategory(Category category);


    ResponseResult getCategory(Long categoryId);

    ResponseResult updateCategory(CategoryDto categoryDto);

    ResponseResult changStatus(Category category);

    ResponseResult deleteCategory(Long[] categoryIds);
}
