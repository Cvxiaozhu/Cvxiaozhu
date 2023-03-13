package com.zhu.blog.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zhu.blog.constants.SystemConstants;
import com.zhu.blog.entity.ResponseResult;
import com.zhu.blog.entity.domain.Role;
import com.zhu.blog.entity.domain.RoleMenu;
import com.zhu.blog.entity.dto.RoleDto;
import com.zhu.blog.entity.vo.PageVo;
import com.zhu.blog.entity.vo.RoleVo;
import com.zhu.blog.enums.AppHttpCodeEnum;
import com.zhu.blog.handler.exception.SystemException;
import com.zhu.blog.mapper.RoleMapper;
import com.zhu.blog.mapper.RoleMenuMapper;
import com.zhu.blog.service.RoleMenuService;
import com.zhu.blog.service.RoleService;
import com.zhu.blog.utils.BeanCopyUtils;
import com.zhu.blog.utils.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 角色信息表(Role)表服务实现类
 *
 * @author makejava
 * @since 2023-03-09 20:36:07
 */
@Service("roleService")
public class RoleServiceImpl extends ServiceImpl<RoleMapper, Role> implements RoleService {

    @Autowired
    private RoleMenuService roleMenuService;

    @Autowired
    private RoleMenuMapper roleMenuMapper;

    @Override
    public List<String> selectRolesKeyByUserId(Long userId) {
        //判断是否是管理员 如果是返回集合中只需要有admin
        if (SecurityUtils.isAdmin()) {
            List<String> roleKeys = new ArrayList<>();
            roleKeys.add("admin");
            return roleKeys;
        }
        //否则查询用户所具有的角色信息
        return getBaseMapper().selectRoleKeyByUserId(userId);
    }

    @Override
    public ResponseResult changStatus(Role role) {
        updateById(role);
        return ResponseResult.okResult();
    }

    @Override
    public ResponseResult listRole(Integer pageNum, Integer pageSize, String roleName, String status) {

        LambdaQueryWrapper<Role> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.like(StringUtils.hasText(roleName), Role::getRoleName, roleName);
        queryWrapper.like(StringUtils.hasText(status), Role::getStatus, status);

        Page<Role> page = new Page<>(pageNum, pageSize);
        page(page, queryWrapper);

        PageVo pageVo = new PageVo(page.getRecords(), page.getTotal());
        return ResponseResult.okResult(pageVo);
    }

    @Override
    public ResponseResult addRole(RoleDto roleDto) {
        Role role = BeanCopyUtils.copyBean(roleDto, Role.class);
        save(role);

        List<RoleMenu> roleMenus = roleDto.getMenuIds().stream()
                .map(menuId -> RoleMenu.builder()
                        .roleId(role.getId())
                        .menuId(menuId)
                        .build()).collect(Collectors.toList());
        roleMenuService.saveBatch(roleMenus);

        return ResponseResult.okResult();

    }

    @Override
    public ResponseResult getRole(Long id) {
        Role role = getById(id);
        RoleVo roleVo = BeanCopyUtils.copyBean(role, RoleVo.class);
        return ResponseResult.okResult(roleVo);
    }

    @Override
    public ResponseResult updateRole(RoleDto roleDto) {
        if (roleDto.getId().equals(0L)) {
            throw new SystemException(AppHttpCodeEnum.NOT_OPERATE);
        }
        Role role = BeanCopyUtils.copyBean(roleDto, Role.class);
        updateById(role);

        List<Long> menuIds = roleDto.getMenuIds();
        List<RoleMenu> roleMenus = menuIds.stream()
                .map(menuId -> new RoleMenu(role.getId(), menuId))
                .collect(Collectors.toList());
        // 先删除再更新
        removeRoleMenu(role.getId());
        roleMenuService.saveBatch(roleMenus);
        return ResponseResult.okResult();
    }

    @Override
    public ResponseResult deleteRole(Long[] ids) {
        if (ids.length == 1 && ids[0].equals(0L)) {
            throw new SystemException(AppHttpCodeEnum.NOT_OPERATE);
        }
        List<Long> idList = Arrays.stream(ids).filter(id -> id.equals(0L)).collect(Collectors.toList());
        removeByIds(idList);
        return ResponseResult.okResult();
    }

    @Override
    public ResponseResult listAllRole() {

        LambdaQueryWrapper<Role> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Role::getStatus, SystemConstants.STATUS_NORMAL);
        List<Role> list = list(queryWrapper);

        return ResponseResult.okResult(list);
    }

    /**
     * 根据角色id删除角色对应菜单
     *
     * @param roleId
     */
    private void removeRoleMenu(Long roleId) {
        LambdaQueryWrapper<RoleMenu> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(RoleMenu::getRoleId, roleId);
        List<RoleMenu> roleMenus = roleMenuService.list(queryWrapper);
        List<Long> menuIds = roleMenus.stream().distinct().map(RoleMenu::getMenuId).collect(Collectors.toList());

        roleMenuMapper.deleteRoleMenuList(roleId, menuIds);
    }

}
