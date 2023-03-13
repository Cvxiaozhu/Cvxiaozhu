package com.zhu.blog.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zhu.blog.entity.domain.UserRole;
import org.apache.ibatis.annotations.Param;

import java.util.List;


/**
 * 用户和角色关联表(UserRole)表数据库访问层
 *
 * @author makejava
 * @since 2023-03-12 21:16:58
 */
public interface UserRoleMapper extends BaseMapper<UserRole> {

    void deleteUserRole(@Param("id") Long id, @Param("roleIds") List<Long> roleIds);
}

