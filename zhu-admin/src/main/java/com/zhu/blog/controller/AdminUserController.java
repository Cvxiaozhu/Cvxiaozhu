package com.zhu.blog.controller;

import com.zhu.blog.annotation.SystemLog;
import com.zhu.blog.entity.domain.LoginUser;
import com.zhu.blog.entity.domain.Menu;
import com.zhu.blog.entity.domain.User;
import com.zhu.blog.entity.ResponseResult;
import com.zhu.blog.entity.dto.UserDto;
import com.zhu.blog.entity.vo.AdminUserInfoVo;
import com.zhu.blog.entity.vo.RoutersVo;
import com.zhu.blog.entity.vo.UserInfoVo;
import com.zhu.blog.enums.AppHttpCodeEnum;
import com.zhu.blog.handler.exception.SystemException;
import com.zhu.blog.service.AdminLoginService;
import com.zhu.blog.service.MenuService;
import com.zhu.blog.service.RoleService;
import com.zhu.blog.utils.BeanCopyUtils;
import com.zhu.blog.utils.SecurityUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author GH
 * @Description:
 * @date 2023/3/2 20:48
 */
@RestController
@Api(tags = "博客后台登录", description = "管理员用户的信息获取")
public class AdminUserController {

    @Autowired
    private AdminLoginService adminLoginService;

    @Autowired
    private MenuService menuService;

    @Autowired
    private RoleService roleService;


    @SystemLog(businessName = "管理员用户登录")
    @ApiOperation(value = "管理员用户登录")
    @PostMapping("/user/login")
    public ResponseResult login(@RequestBody UserDto userDto) {
        User user = BeanCopyUtils.copyBean(userDto, User.class);

        if (!StringUtils.hasText(user.getUserName())) {
            throw new SystemException(AppHttpCodeEnum.REQUIRE_USERNAME);
        }
        return adminLoginService.login(user);
    }

    @GetMapping("/getInfo")
    public ResponseResult<AdminUserInfoVo> getInfo() {

        // 获取当前用户
        LoginUser loginUser = SecurityUtils.getLoginUser();
        Long userId = loginUser.getUser().getId();
        // 获取当前用户的权限信息
        List<String> perms = menuService.selectPermsByUserId(userId);
        // 获取当前用户的角色信息
        List<String> rolesKeyList = roleService.selectRolesKeyByUserId(userId);
        // 封装成userinfo对象
        UserInfoVo userInfoVo = BeanCopyUtils.copyBean(loginUser, UserInfoVo.class);
        return ResponseResult.okResult(new AdminUserInfoVo(perms, rolesKeyList, userInfoVo));
    }

    @GetMapping("getRouters")
    public ResponseResult<RoutersVo> getRouters(){
        Long userId = SecurityUtils.getUserId();
        //查询menu 结果是tree的形式
        List<Menu> menus = menuService.selectRouterMenuTreeByUserId(userId);
        //封装数据返回
        return ResponseResult.okResult(new RoutersVo(menus));
    }

    @PostMapping("/user/logout")
    public ResponseResult logout() {
        return adminLoginService.logout();
    }

}
