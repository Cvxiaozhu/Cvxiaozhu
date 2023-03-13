package com.zhu.blog.service;

import com.zhu.blog.entity.domain.User;
import com.zhu.blog.entity.ResponseResult;

/**
 * @author GH
 */
public interface BlogService {

    /**
     * 用户登录权限校验
     * @param user
     * @return
     */
    ResponseResult login(User user);

    /**
     * 退出登录
     * @return
     */
    ResponseResult logout();
}
