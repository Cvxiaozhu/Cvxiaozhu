package com.zhu.blog.controller;

import com.zhu.blog.annotation.SystemLog;
import com.zhu.blog.entity.ResponseResult;
import com.zhu.blog.entity.domain.Link;
import com.zhu.blog.entity.dto.LinkDto;
import com.zhu.blog.service.LinkService;
import com.zhu.blog.utils.BeanCopyUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author GH
 * @Description:
 * @date 2023/3/12 15:47
 */
@RestController
@RequestMapping("/content/link")
@Api(tags = "友链管理")
public class LinkController {

    @Autowired
    private LinkService linkService;

    @GetMapping("/list")
    @SystemLog(businessName = "友链查询")
    @ApiOperation(value = "友链查询", notes = "友链分页查询")
    public ResponseResult listLink(Integer pageNum, Integer pageSize, String name, String status) {
        return linkService.listLink(pageNum, pageSize, name, status);
    }

    @PostMapping
    @SystemLog(businessName = "添加友链")
    @ApiOperation(value = "添加友链", notes = "添加友链")
    public ResponseResult addLink(@RequestBody LinkDto linkDto) {
        Link link = BeanCopyUtils.copyBean(linkDto, Link.class);
        return linkService.addLink(link);
    }

    @GetMapping("/{id}")
    @SystemLog(businessName = "查询友链")
    @ApiOperation(value = "添加友链", notes = "添加友链")
    public ResponseResult getLink(@PathVariable("id") Long linkId) {
        return linkService.getLink(linkId);
    }

    @PutMapping
    @SystemLog(businessName = "更新友链信息")
    @ApiOperation(value = "更新友链信息", notes = "更新友链信息")
    public ResponseResult updateLink(@RequestBody LinkDto linkDto) {
        return linkService.updateLink(linkDto);
    }

    @PutMapping("/changeLinkStatus")
    @SystemLog(businessName = "修改友链状态")
    @ApiOperation(value = "修改友链状态", notes = "修改友链状态")
    public ResponseResult changeLinkStatus(@RequestBody LinkDto linkDto) {
        Link link = BeanCopyUtils.copyBean(linkDto, Link.class);
        return linkService.changeLinkStatus(link);
    }

    @DeleteMapping("/{ids}")
    public ResponseResult deleteLink(@PathVariable("ids") Long[] linkIds) {
        return linkService.deleteLink(linkIds);
    }
}
