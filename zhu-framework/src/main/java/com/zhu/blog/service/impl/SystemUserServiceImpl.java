package com.zhu.blog.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zhu.blog.entity.ResponseResult;
import com.zhu.blog.entity.domain.Role;
import com.zhu.blog.entity.domain.User;
import com.zhu.blog.entity.domain.UserRole;
import com.zhu.blog.entity.dto.SystemUserDto;
import com.zhu.blog.entity.vo.PageVo;
import com.zhu.blog.entity.vo.UserInfoVo;
import com.zhu.blog.entity.vo.UserRoleVo;
import com.zhu.blog.enums.AppHttpCodeEnum;
import com.zhu.blog.handler.exception.SystemException;
import com.zhu.blog.mapper.UserMapper;
import com.zhu.blog.mapper.UserRoleMapper;
import com.zhu.blog.service.RoleService;
import com.zhu.blog.service.SystemUserService;
import com.zhu.blog.service.UserRoleService;
import com.zhu.blog.service.UserService;
import com.zhu.blog.utils.BeanCopyUtils;
import com.zhu.blog.utils.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author GH
 * @Description:
 * @date 2023/3/12 16:25
 */
@Service("systemUserService")
public class SystemUserServiceImpl extends ServiceImpl<UserMapper, User> implements SystemUserService {

    @Autowired
    private UserService userService;

    @Autowired
    private UserRoleService userRoleService;

    @Autowired
    private RoleService roleService;

    @Autowired
    private UserRoleMapper userRoleMapper;

    @Override
    public ResponseResult listSystemUser(Integer pageNum, Integer pageSize, String userName, String phonenumber, String status) {
        LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.like(StringUtils.hasText(userName), User::getUserName, userName);
        queryWrapper.like(StringUtils.hasText(status), User::getStatus, status);
        queryWrapper.like(StringUtils.hasText(phonenumber), User::getPhonenumber, phonenumber);

        Page<User> page = new Page<>(pageNum, pageSize);
        userService.page(page, queryWrapper);

        PageVo pageVo = new PageVo(page.getRecords(), page.getTotal());

        return ResponseResult.okResult(pageVo);
    }

    @Override
    public ResponseResult addSystemUser(SystemUserDto systemUserDto) {
        User user = BeanCopyUtils.copyBean(systemUserDto, User.class);
        save(user);

        List<UserRole> userRoleList = systemUserDto.getRoleIds().stream()
                .map(roleId -> new UserRole(user.getId(), roleId))
                .collect(Collectors.toList());

        userRoleService.saveBatch(userRoleList);
        return ResponseResult.okResult();
    }

    @Override
    public ResponseResult getSystemUser(Long id) {
        // 查询用户
        User user = userService.getById(id);
        UserInfoVo userInfoVo = BeanCopyUtils.copyBean(user, UserInfoVo.class);
        // 查询用户关联角色信息
        LambdaQueryWrapper<UserRole> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(UserRole::getUserId, user.getId());
        List<UserRole> userRoles = userRoleService.list(queryWrapper);
        List<Long> roleIds = userRoles.stream().map(UserRole::getRoleId).collect(Collectors.toList());

        // 查询所角色列表
        List<Role> roles = roleService.list();

        // 封装成UserRoleVo返回
        UserRoleVo userRoleVo = new UserRoleVo(userInfoVo, roles, roleIds);
        return ResponseResult.okResult(userRoleVo);
    }

    @Override
    public ResponseResult updateSystemUser(SystemUserDto systemUserDto) {
        User user = BeanCopyUtils.copyBean(systemUserDto, User.class);
        save(user);

        LambdaQueryWrapper<UserRole> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(UserRole::getUserId, user.getId());
        List<UserRole> userRoles = userRoleService.list(queryWrapper);
        List<Long> roleIds = userRoles.stream().map(UserRole::getRoleId).collect(Collectors.toList());

        // 删除原来的关联
        userRoleMapper.deleteUserRole(user.getId(), roleIds);

        List<UserRole> userRoleList = systemUserDto.getRoleIds().stream().map(roleId -> new UserRole(user.getId(), roleId)).collect(Collectors.toList());
        userRoleService.saveBatch(userRoleList);
        return ResponseResult.okResult();
    }

    @Override
    public ResponseResult deleteSystemUser(Long[] systemUserIds) {

        if (systemUserIds.length == 1 && systemUserIds[0].equals(SecurityUtils.getUserId())) {
            throw new SystemException(AppHttpCodeEnum.USER_NOT_DELETE);
        }

        List<Long> collect = Arrays.stream(systemUserIds).filter(userId -> !userId.equals(SecurityUtils.getUserId())).collect(Collectors.toList());

        userService.removeByIds(collect);

        return ResponseResult.okResult();
    }
}
