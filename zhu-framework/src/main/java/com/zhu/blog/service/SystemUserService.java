package com.zhu.blog.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.zhu.blog.entity.ResponseResult;
import com.zhu.blog.entity.domain.User;
import com.zhu.blog.entity.dto.SystemUserDto;

/**
 * @author GH
 */
public interface SystemUserService extends IService<User> {

    /**
     * 查询用户列表
     * @param pageNum
     * @param pageSize
     * @param userName
     * @param phonenumber
     * @param status
     * @return
     */
    ResponseResult listSystemUser(Integer pageNum, Integer pageSize, String userName, String phonenumber, String status);

    ResponseResult addSystemUser(SystemUserDto systemUserDto);

    ResponseResult getSystemUser(Long id);

    ResponseResult updateSystemUser(SystemUserDto systemUserDto);

    ResponseResult deleteSystemUser(Long[] systemUserIds);
}
