package com.zhu.blog.controller;

import com.zhu.blog.annotation.SystemLog;
import com.zhu.blog.entity.domain.User;
import com.zhu.blog.entity.ResponseResult;
import com.zhu.blog.entity.dto.UserDto;
import com.zhu.blog.enums.AppHttpCodeEnum;
import com.zhu.blog.handler.exception.SystemException;
import com.zhu.blog.service.BlogService;
import com.zhu.blog.utils.BeanCopyUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author GH
 * @Description:
 * @date 2023/3/2 20:48
 */
@RestController
@Api(tags = "博客接口", description = "实现用户的登录退出")
public class BlogController {

    @Autowired
    private BlogService blogService;

    @PostMapping("/login")
    @SystemLog(businessName = "用户登录")
    @ApiOperation(value = "用户登录")
    public ResponseResult login(@RequestBody UserDto userDto) {
        User user = BeanCopyUtils.copyBean(userDto, User.class);

        if (!StringUtils.hasText(user.getUserName())) {
            throw new SystemException(AppHttpCodeEnum.REQUIRE_USERNAME);
        }
        return blogService.login(user);
    }

    @PostMapping("/logout")
    @SystemLog(businessName = "退出登录")
    @ApiOperation(value = "退出登录")
    public ResponseResult logout() {
        return blogService.logout();
    }

}
