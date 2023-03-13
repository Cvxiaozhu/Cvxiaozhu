package com.zhu.blog.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.zhu.blog.entity.domain.User;
import com.zhu.blog.entity.ResponseResult;


/**
 * 用户表(User)表服务接口
 *
 * @author makejava
 * @since 2023-03-04 21:16:44
 */
public interface UserService extends IService<User> {

    /**
     * 查询用户的个人信息
     * @return
     */
    ResponseResult userInfo();

    /**
     * 更新个人信息
     * @return
     * @param user
     */
    ResponseResult updateUserInfo(User user);

    /**
     * 用户注册
     * @param user
     * @return
     */
    ResponseResult register(User user);
}
