package com.zhu.blog.service;

import com.zhu.blog.entity.domain.User;
import com.zhu.blog.entity.ResponseResult;

/**
 * @author GH
 */
public interface AdminLoginService {

    /**
     * 用户登录权限校验
     * @param user
     * @return
     */
    ResponseResult login(User user);

    /**
     * 管理员用户退出登录操作
     * @return
     */
    ResponseResult logout();


}
