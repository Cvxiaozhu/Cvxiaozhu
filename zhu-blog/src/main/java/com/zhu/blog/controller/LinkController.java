package com.zhu.blog.controller;

import com.zhu.blog.annotation.SystemLog;
import com.zhu.blog.entity.ResponseResult;
import com.zhu.blog.service.LinkService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author GH
 * @Description:
 * @date 2023/3/2 20:23
 */
@RestController
@RequestMapping("/link")
@Api(tags = "友链接口")
public class LinkController {

    @Autowired
    private LinkService linkService;

    @GetMapping("/getAllLink")
    @SystemLog(businessName = "友链获取")
    @ApiOperation(value = "友链获取")
    public ResponseResult getAllLink() {
        return linkService.getAllLink();
    }
}
