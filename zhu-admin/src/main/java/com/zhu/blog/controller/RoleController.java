package com.zhu.blog.controller;

import com.zhu.blog.annotation.SystemLog;
import com.zhu.blog.entity.ResponseResult;
import com.zhu.blog.entity.domain.Role;
import com.zhu.blog.entity.dto.RoleDto;
import com.zhu.blog.service.RoleService;
import com.zhu.blog.utils.BeanCopyUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.models.auth.In;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

/**
 * @author GH
 * @Description:
 * @date 2023/3/11 21:26
 */
@RestController
@RequestMapping("/system/role")
@Api(tags = "角色管理")
public class RoleController {

    @Autowired
    private RoleService roleService;

    @PutMapping("/changeStatus")
    @SystemLog(businessName = "修改角色状态")
    @ApiOperation(value = "修改角色状态", notes = "修改角色状态")
    public ResponseResult changStatus(@RequestBody RoleDto roleDto) {
        Role role = BeanCopyUtils.copyBean(roleDto, Role.class);
        return roleService.changStatus(role);
    }

    @GetMapping("/list")
    @SystemLog(businessName = "查询所有角色")
    @ApiOperation(value = "查询所有角色", notes = "查询所有角色")
    public ResponseResult listRole(Integer pageNum, Integer pageSize, String roleName, String status) {
        return roleService.listRole(pageNum, pageSize, roleName, status);
    }

    @PreAuthorize("@ps.hasPermission('system:user:add')")
    @GetMapping("/listAllRole")
    @SystemLog(businessName = "查询所有角色")
    @ApiOperation(value = "查询所有角色", notes = "查询所有角色,添加用户时调用")
    public ResponseResult listAllRole() {
        return roleService.listAllRole();
    }

    @PreAuthorize("@ps.hasPermission('system:role:add')")
    @PostMapping
    @SystemLog(businessName = "添加角色")
    @ApiOperation(value = "添加角色", notes = "添加角色")
    public ResponseResult addRole(@RequestBody  RoleDto roleDto) {
        return roleService.addRole(roleDto);
    }

    @GetMapping("/{id}")
    @SystemLog(businessName = "查询角色")
    @ApiOperation(value = "查询角色", notes = "根据id查询角色")
    public ResponseResult getRole(@PathVariable("id") Long id) {
        return roleService.getRole(id);
    }


    @PutMapping
    @SystemLog(businessName = "更新角色信息")
    @ApiOperation(value = "更新角色信息", notes = "更新角色信息")
    public ResponseResult updateRole(@RequestBody RoleDto roleDto) {
        return roleService.updateRole(roleDto);
    }

    @PreAuthorize("@ps.hasPermission('system:role:remove')")
    @DeleteMapping("/{ids}")
    @SystemLog(businessName = "删除角色信息")
    @ApiOperation(value = "删除角色信息", notes = "超级管理员角色不能删除角")
    public ResponseResult deleteRole(@PathVariable("ids") Long[] ids) {
        return roleService.deleteRole(ids);
    }



}
