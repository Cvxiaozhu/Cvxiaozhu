package com.zhu.blog.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zhu.blog.entity.domain.RoleMenu;
import org.apache.ibatis.annotations.Param;

import java.util.List;


/**
 * 角色和菜单关联表(RoleMenu)表数据库访问层
 *
 * @author makejava
 * @since 2023-03-11 22:50:23
 */
public interface RoleMenuMapper extends BaseMapper<RoleMenu> {

    /**
     * 删除角色菜单关联
     * @param roleId
     * @param menuIds
     * @return
     */
    boolean deleteRoleMenuList(@Param("roleId") Long roleId, @Param("menuIds") List<Long> menuIds);

}

