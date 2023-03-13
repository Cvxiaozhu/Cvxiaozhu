package com.zhu.blog.controller;

import com.zhu.blog.annotation.SystemLog;
import com.zhu.blog.entity.ResponseResult;
import com.zhu.blog.entity.domain.Tag;
import com.zhu.blog.entity.dto.TagListDto;
import com.zhu.blog.entity.vo.PageVo;
import com.zhu.blog.service.TagService;
import com.zhu.blog.utils.BeanCopyUtils;
import com.zhu.blog.utils.WebUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author GH
 * @Description:
 * @date 2023/3/8 19:26
 */
@RestController
@RequestMapping("/content/tag")
@Api(tags = "标签管理")
public class TagController {

    @Autowired
    private TagService tagService;

    @GetMapping("/{id}")
    @SystemLog(businessName = "查询单个标签")
    @ApiOperation(value = "查询单个标签", notes = "查询单个标签接口")
    public ResponseResult getTag(@PathVariable Long id) {
        return tagService.getTag(id);
    }


    @GetMapping("/list")
    @SystemLog(businessName = "查询所有标签")
    @ApiOperation(value = "查看所有的标签", notes = "标签列表接口")
    public ResponseResult<PageVo> getTags(Integer pageNum, Integer pageSize, TagListDto tagListDto) {
        return tagService.getTagsList(pageNum, pageSize, tagListDto);
    }

    @GetMapping("/listAllTag")
    @SystemLog(businessName = "查询所有标签")
    @ApiOperation(value = "查看所有的标签", notes = "标签列表接口")
    public ResponseResult listAllTag() {

        return tagService.listAllTag();
    }

    @PostMapping
    @SystemLog(businessName = "添加标签")
    @ApiOperation(value = "添加标签", notes = "添加标签接口")
    public ResponseResult addTag(@RequestBody TagListDto tagListDto) {
        Tag tag = BeanCopyUtils.copyBean(tagListDto, Tag.class);
        return tagService.addTag(tag);
    }

    @DeleteMapping("/{ids}")
    @SystemLog(businessName = "删除标签")
    @ApiOperation(value = "删除标签", notes = "删除标签接口，也能进行批量删除")
    public ResponseResult deleteTagById(@PathVariable("ids") String ids) {
        List<Long> idStrToList = WebUtils.idStrToList(ids);
        return tagService.deleteTagById(idStrToList);
    }


    @PutMapping
    @SystemLog(businessName = "更新标签")
    @ApiOperation(value = "更新标签", notes = "更新标签接口")
    public ResponseResult updateTag(@RequestBody TagListDto tagListDto) {
        Tag tag = BeanCopyUtils.copyBean(tagListDto, Tag.class);
        return tagService.updateTag(tag);
    }

}
