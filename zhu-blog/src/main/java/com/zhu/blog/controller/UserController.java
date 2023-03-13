package com.zhu.blog.controller;

import com.zhu.blog.annotation.SystemLog;
import com.zhu.blog.entity.domain.User;
import com.zhu.blog.entity.ResponseResult;
import com.zhu.blog.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


/**
 * @author GH
 * @Description:
 * @date 2023/3/4 22:27
 */
@RestController
@RequestMapping("/user")
@Api(tags = "用户接口", description = "用户登录注册操作接口")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/userInfo")
    @SystemLog(businessName = "查询用户个人信息")
    @ApiOperation(value = "查询用户个人信息")
    public ResponseResult userInfo() {
        return userService.userInfo();
    }

    @PutMapping("/userInfo")
    @SystemLog(businessName = "更新用户信息")
    @ApiOperation(value = "更新用户信息")
    public ResponseResult userInfo(@RequestBody User user) {
        return userService.updateUserInfo(user);
    }

    @PostMapping("/register")
    @SystemLog(businessName = "用户注册")
    @ApiOperation(value = "用户注册")
    public ResponseResult register(@RequestBody User user) {
        return userService.register(user);
    }
}
