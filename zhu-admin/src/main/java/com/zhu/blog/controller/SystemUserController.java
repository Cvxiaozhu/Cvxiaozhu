package com.zhu.blog.controller;

import com.zhu.blog.annotation.SystemLog;
import com.zhu.blog.entity.ResponseResult;
import com.zhu.blog.entity.dto.SystemUserDto;
import com.zhu.blog.entity.dto.UserDto;
import com.zhu.blog.service.AdminLoginService;
import com.zhu.blog.service.SystemUserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

/**
 * @author GH
 * @Description:
 * @date 2023/3/12 15:52
 */
@RestController
@RequestMapping("/system/user")
@Api(tags = "用户管理")
public class SystemUserController {

    @Autowired
    private SystemUserService systemUserService;

    @GetMapping("/list")
    @SystemLog(businessName = "管理员用户查询")
    @ApiOperation(value = "管理员用户查询", notes = "管理员用户分页查询")
    public ResponseResult listSystemUser(Integer pageNum, Integer pageSize, String userName, String phonenumber, String status) {
        return systemUserService.listSystemUser(pageNum, pageSize, userName, phonenumber, status);
    }

    @PreAuthorize("@ps.hasPermission('system:user:add')")
    @PostMapping
    @SystemLog(businessName = "添加管理员用户")
    @ApiOperation(value = "添加管理员用户", notes = "添加管理员用户")
    public ResponseResult addSystemUser(@RequestBody SystemUserDto systemUserDto) {
        return systemUserService.addSystemUser(systemUserDto);
    }

    @GetMapping("/{id}")
    @SystemLog(businessName = "根据id查询信息")
    @ApiOperation(value = "根据id查询信息", notes = "根据id查询信接口")
    public ResponseResult getSystemUser(@PathVariable("id") Long id) {
        return systemUserService.getSystemUser(id);
    }


    @PutMapping
    @SystemLog(businessName = "更新管理员用户")
    @ApiOperation(value = "更新管理员用户", notes = "更新管理员用户")
    public ResponseResult updateSystemUser(@RequestBody SystemUserDto systemUserDto) {
        return systemUserService.updateSystemUser(systemUserDto);
    }

    @PreAuthorize("@ps.hasPermission('system:user:remove')")
    @DeleteMapping("/{ids}")
    public ResponseResult deleteSystemUser(@PathVariable("ids") Long[] systemUserIds) {
        return systemUserService.deleteSystemUser(systemUserIds);
    }


}
