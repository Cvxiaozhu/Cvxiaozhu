package com.zhu.blog.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.zhu.blog.entity.domain.Tag;
import com.zhu.blog.entity.ResponseResult;
import com.zhu.blog.entity.dto.TagListDto;
import com.zhu.blog.entity.vo.PageVo;

import java.util.List;


/**
 * 标签(Tag)表服务接口
 *
 * @author makejava
 * @since 2023-03-08 19:28:10
 */
public interface TagService extends IService<Tag> {

    /**
     * 查询所有的标签
     * @return
     * @param pageNum
     * @param pageSize
     * @param tagListDto
     */
    ResponseResult<PageVo> getTagsList(Integer pageNum, Integer pageSize, TagListDto tagListDto);

    /**
     * 添加标签
     * @param tag
     * @return
     */
    ResponseResult addTag(Tag tag);

    /**
     * 根据标签id删除标签
     * @param ids
     * @return
     */
    ResponseResult deleteTagById(List<Long> ids);

    /**
     * 根据列表数组批量删除标签
     * @param ids
     * @return
     */
    ResponseResult deleteTagBatchById(List<Long> ids);

    /**
     * 查询单个标签
     * @param id
     * @return
     */
    ResponseResult getTag(Long id);

    /**
     * 更新单个标签
     * @param tag
     * @return
     */
    ResponseResult updateTag(Tag tag);

    /**
     * 写博文查询多个标签
     * @return
     */
    ResponseResult listAllTag();

}
