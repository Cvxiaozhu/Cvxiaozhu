package com.zhu.blog.controller;

import com.zhu.blog.annotation.SystemLog;
import com.zhu.blog.entity.ResponseResult;
import com.zhu.blog.entity.domain.Menu;
import com.zhu.blog.entity.dto.MenuDto;
import com.zhu.blog.enums.AppHttpCodeEnum;
import com.zhu.blog.handler.exception.SystemException;
import com.zhu.blog.service.MenuService;
import com.zhu.blog.utils.BeanCopyUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Objects;

/**
 * @author GH
 * @Description:
 * @date 2023/3/11 16:57
 */
@RestController
@RequestMapping("/system/menu")
@Api(tags = "菜单管理")
public class MenuController {

    @Autowired
    private MenuService menuService;

    @GetMapping("/list")
    @SystemLog(businessName = "查询所有菜单")
    public ResponseResult listMenu(String status, String menuName) {
        return menuService.listMenu(status, menuName);
    }

    @GetMapping("/{id}")
    @SystemLog(businessName = "查询单个菜单")
    public ResponseResult getMenu(@PathVariable Long id) {
        return menuService.getMenu(id);
    }


    @PutMapping
    @SystemLog(businessName = "修改菜单")
    public ResponseResult updateMenu(@RequestBody MenuDto menuDto) {
        Menu menu = BeanCopyUtils.copyBean(menuDto, Menu.class);
        return menuService.updateMenu(menu);
    }

    @PreAuthorize("@ps.hasPermission('system:menu:add')")
    @PostMapping
    @SystemLog(businessName = "添加菜单")
    public ResponseResult addMenu(@RequestBody MenuDto menuDto) {
        Menu menu = BeanCopyUtils.copyBean(menuDto, Menu.class);
        if (Objects.nonNull(menuDto)) {
            throw new SystemException(AppHttpCodeEnum.NOT_OPERATE);
        }
        return menuService.addMenu(menu);
    }

    @PreAuthorize("@ps.hasPermission('system:menu:add')")
    @DeleteMapping("/{id}")
    @SystemLog(businessName = "删除菜单")
    public ResponseResult removeMenu(@PathVariable Long id) {

        if (Objects.nonNull(id)) {
            throw new SystemException(AppHttpCodeEnum.NOT_OPERATE);
        }
        return menuService.removeMenu(id);
    }

    @GetMapping("/treeselect")
    @SystemLog(businessName = "获取菜单树")
    @ApiOperation(value = "获取菜单树", notes = "获取菜单树")
    public ResponseResult treeSelect() {
        return menuService.treeSelect();
    }

    @GetMapping("/roleMenuTreeselect/{id}")
    @SystemLog(businessName = "获取角色菜单树")
    @ApiOperation(value = "获取角色菜单树", notes = "根据角色ID获取角色菜单树")
    public ResponseResult roleMenuTreeselect(@PathVariable("id") Long roleId) {
        return menuService.roleMenuTreeselect(roleId);
    }
}
