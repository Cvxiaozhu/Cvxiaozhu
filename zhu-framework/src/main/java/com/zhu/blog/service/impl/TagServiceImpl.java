package com.zhu.blog.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zhu.blog.entity.ResponseResult;
import com.zhu.blog.entity.domain.Tag;
import com.zhu.blog.entity.dto.TagListDto;
import com.zhu.blog.entity.vo.PageVo;
import com.zhu.blog.entity.vo.TagVo;
import com.zhu.blog.mapper.TagMapper;
import com.zhu.blog.service.TagService;
import com.zhu.blog.utils.BeanCopyUtils;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 标签(Tag)表服务实现类
 *
 * @author makejava
 * @since 2023-03-08 19:28:11
 */
@Service("tagService")
public class TagServiceImpl extends ServiceImpl<TagMapper, Tag> implements TagService {
    @Override
    public ResponseResult deleteTagBatchById(List<Long> ids) {
        removeByIds(ids);
        return ResponseResult.okResult();
    }

    @Override
    public ResponseResult getTag(Long id) {
        Tag tag = getById(id);
        return ResponseResult.okResult(tag);
    }

    @Override
    public ResponseResult updateTag(Tag tag) {
        updateById(tag);
        return ResponseResult.okResult();
    }

    @Override
    public ResponseResult listAllTag() {
        LambdaQueryWrapper<Tag> queryWrapper = new LambdaQueryWrapper<Tag>();
        queryWrapper.select(Tag::getId,Tag::getName);
        List<Tag> list = list(queryWrapper);
        List<TagVo> tagVos = BeanCopyUtils.copyBeanList(list, TagVo.class);
        return ResponseResult.okResult(tagVos);
    }

    @Override
    public ResponseResult deleteTagById(List<Long> ids) {
        removeByIds(ids);
        return ResponseResult.okResult();
    }

    @Override
    public ResponseResult addTag(Tag tag) {
        save(tag);
        return ResponseResult.okResult();
    }

    @Override
    public ResponseResult<PageVo> getTagsList(Integer pageNum, Integer pageSize, TagListDto tagListDto) {
        LambdaQueryWrapper<Tag> queryWrapper = new LambdaQueryWrapper<>();

        queryWrapper.like(Tag::getName, tagListDto.getName());
        queryWrapper.like(Tag::getRemark, tagListDto.getRemark());

        Page<Tag> page = new Page<>(pageNum, pageSize);
        page(page, queryWrapper);
        List<TagVo> tagVos = BeanCopyUtils.copyBeanList(page.getRecords(), TagVo.class);
        PageVo pagevo = new PageVo(tagVos, page.getTotal());

        return ResponseResult.okResult(pagevo);
    }
}
