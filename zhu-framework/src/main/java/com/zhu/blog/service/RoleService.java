package com.zhu.blog.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.zhu.blog.entity.ResponseResult;
import com.zhu.blog.entity.domain.Role;
import com.zhu.blog.entity.dto.RoleDto;

import java.util.List;


/**
 * 角色信息表(Role)表服务接口
 *
 * @author makejava
 * @since 2023-03-09 20:36:07
 */
public interface RoleService extends IService<Role> {

    /**
     * 根据用户Id查询用户的角色信息
     * @param userId
     * @return
     */
    List<String> selectRolesKeyByUserId(Long userId);

    /**
     * 根据角色id修改角色状态
     * @param role
     * @return
     */
    ResponseResult changStatus(Role role);

    /**
     * 查询所有的角色
     * @return
     * @param pageNum
     * @param pageSize
     * @param roleName
     * @param status
     */
    ResponseResult listRole(Integer pageNum, Integer pageSize, String roleName, String status);

    ResponseResult addRole(RoleDto roleDto);

    ResponseResult getRole(Long id);

    ResponseResult updateRole(RoleDto roleDto);

    ResponseResult deleteRole(Long[] ids);

    ResponseResult listAllRole();

}
