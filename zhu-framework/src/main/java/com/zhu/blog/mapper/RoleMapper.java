package com.zhu.blog.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zhu.blog.entity.domain.Role;

import java.util.List;


/**
 * 角色信息表(Role)表数据库访问层
 *
 * @author makejava
 * @since 2023-03-09 20:36:05
 */
public interface RoleMapper extends BaseMapper<Role> {


    List<String> selectRoleKeyByUserId(Long userId);
}

