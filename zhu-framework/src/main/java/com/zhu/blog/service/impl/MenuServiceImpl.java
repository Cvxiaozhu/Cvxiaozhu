package com.zhu.blog.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zhu.blog.constants.SystemConstants;
import com.zhu.blog.entity.ResponseResult;
import com.zhu.blog.entity.domain.Menu;
import com.zhu.blog.entity.domain.RoleMenu;
import com.zhu.blog.entity.vo.MenuTreeVo;
import com.zhu.blog.entity.vo.MenuVo;
import com.zhu.blog.entity.vo.RoleMenuTreeVo;
import com.zhu.blog.enums.AppHttpCodeEnum;
import com.zhu.blog.handler.exception.SystemException;
import com.zhu.blog.mapper.MenuMapper;
import com.zhu.blog.mapper.RoleMapper;
import com.zhu.blog.mapper.RoleMenuMapper;
import com.zhu.blog.service.MenuService;
import com.zhu.blog.service.RoleMenuService;
import com.zhu.blog.utils.BeanCopyUtils;
import com.zhu.blog.utils.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * 菜单权限表(Menu)表服务实现类
 *
 * @author makejava
 * @since 2023-03-09 20:30:16
 */
@Service("menuService")
public class MenuServiceImpl extends ServiceImpl<MenuMapper, Menu> implements MenuService {
    
    
    @Autowired
    private MenuMapper menuMapper;

    @Autowired
    private RoleMapper roleMapper;

    @Autowired
    private RoleMenuService roleMenuService;
    
    @Override
    public List<String> selectPermsByUserId(Long userId) {
        //如果是管理员，返回所有的权限
        if(SecurityUtils.isAdmin()){
            LambdaQueryWrapper<Menu> wrapper = new LambdaQueryWrapper<>();
            wrapper.in(Menu::getMenuType, SystemConstants.MENU,SystemConstants.BUTTON);
            wrapper.eq(Menu::getStatus,SystemConstants.STATUS_NORMAL);
            List<Menu> menus = list(wrapper);
            return menus.stream()
                    .map(Menu::getPerms)
                    .collect(Collectors.toList());
        }
        //否则返回所具有的权限
        return getBaseMapper().selectPermsByUserId(userId);
    }

    @Override
    public List<Menu> selectRouterMenuTreeByUserId(Long userId) {
        List<Menu> menus;
        //判断是否是管理员
        if(SecurityUtils.isAdmin()){
            //如果是 获取所有符合要求的Menu
            menus = menuMapper.selectAllRouterMenu();
        }else{
            //否则  获取当前用户所具有的Menu
            menus = menuMapper.selectRouterMenuTreeByUserId(userId);
        }
        //构建tree
        //先找出第一层的菜单  然后去找他们的子菜单设置到children属性中
        return builderMenuTree(menus,0L);
    }


    @Override
    public ResponseResult treeSelect() {
        
        Long userId = SecurityUtils.getUserId();
        List<Menu> menus;
        //判断是否是管理员
        if(SecurityUtils.isAdmin()){
            //如果是 获取所有符合要求的Menu
            menus = menuMapper.selectAllRouterMenu();
        }else{
            //否则  获取当前用户所具有的Menu
            menus = menuMapper.selectRouterMenuTreeByUserId(userId);
        }
        List<MenuTreeVo> menuTreeVos = menus.stream()
                .map(menu -> MenuTreeVo.builder()
                        .id(menu.getId())
                        .label(menu.getMenuName())
                        .parentId(menu.getParentId())
                        .build())
                .collect(Collectors.toList());
        List<MenuTreeVo> treeVos = builderMenuTree1(menuTreeVos);
        return ResponseResult.okResult(treeVos);
    }

    @Override
    public ResponseResult roleMenuTreeselect(Long roleId) {
        List<Menu> menus;
        List<Long> checkedKeys;
        if (SecurityUtils.isAdmin()) {
            menus = menuMapper.selectAllRouterMenu();
            checkedKeys = roleMenuService.list().stream().distinct().map(RoleMenu::getMenuId).collect(Collectors.toList());
        } else  {
            // 根据角色id查询角色对应的菜单
            menus = menuMapper.selectRouterMenuTreeByUserId(roleId);
            checkedKeys = getMenuId(roleId);
        }

        List<MenuTreeVo> menuTreeVos = menus.stream()
                .map(menu -> MenuTreeVo.builder()
                        .id(menu.getId())
                        .label(menu.getMenuName())
                        .parentId(menu.getParentId())
                        .build())
                .collect(Collectors.toList());
        List<MenuTreeVo> treeVos = builderMenuTree1(menuTreeVos);

        RoleMenuTreeVo roleMenuTreeVo = new RoleMenuTreeVo(treeVos, checkedKeys);

        return ResponseResult.okResult(roleMenuTreeVo);
    }

    /**
     * 根据角色Id查询菜单列表
     * @param roleId
     * @return
     */
    private List<Long> getMenuId(Long roleId) {
        LambdaQueryWrapper<RoleMenu> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(RoleMenu::getRoleId, roleId);

        return roleMenuService.list(queryWrapper).stream().map(RoleMenu::getMenuId).collect(Collectors.toList());
    }

    /**
     * 重定义了一个层级菜单
     * @param menus
     * @return
     */
    private List<MenuTreeVo> builderMenuTree1(List<MenuTreeVo> menus) {
        return menus.stream()
                // 过滤掉一级菜单
                .filter(menu -> menu.getParentId().equals(0L))
                .map(menu -> menu.setChildren(getChildren1(menu, menus)))
                .collect(Collectors.toList());
    }

    /**
     * 重定义了一个层级菜单
     * @param menu
     * @param menus
     * @return
     */
    private List<MenuTreeVo> getChildren1(MenuTreeVo menu, List<MenuTreeVo> menus) {
        List<MenuTreeVo> list = new ArrayList<>();
        for (MenuTreeVo m : menus) {
            if (m.getParentId().equals(menu.getId())) {
                MenuTreeVo setChildren = m.setChildren(getChildren1(m, menus));
                list.add(setChildren);
            }
        }
        return list;
    }



    /**
     * 构建层级菜单
     * @param menus
     * @param parentId
     * @return
     */
    private List<Menu> builderMenuTree(List<Menu> menus, Long parentId) {
        return menus.stream()
                .filter(menu -> menu.getParentId().equals(parentId))
                .map(menu -> menu.setChildren(getChildren(menu, menus)))
                .collect(Collectors.toList());
    }

    /**
     * 获取存入参数的 子Menu集合
     * @param menu
     * @param menus
     * @return
     */
    private List<Menu> getChildren(Menu menu, List<Menu> menus) {
        List<Menu> list = new ArrayList<>();
        for (Menu m : menus) {
            if (m.getParentId().equals(menu.getId())) {
                Menu setChildren = m.setChildren(getChildren(m, menus));
                list.add(setChildren);
            }
        }
        return list;
    }

    @Override
    public ResponseResult listMenu(String status, String menuName) {
        LambdaQueryWrapper<Menu> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.like(StringUtils.hasText(status), Menu::getStatus, status);
        queryWrapper.like(StringUtils.hasText(menuName), Menu::getMenuName, menuName);
        List<Menu> list = list(queryWrapper);
        List<MenuVo> menuVos = BeanCopyUtils.copyBeanList(list, MenuVo.class);
        return ResponseResult.okResult(menuVos);
    }

    @Override
    public ResponseResult addMenu(Menu menu) {
        save(menu);
        return ResponseResult.okResult();
    }

    @Override
    public ResponseResult getMenu(Long id) {
        Menu menu = getById(id);
        MenuVo menuVo = BeanCopyUtils.copyBean(menu, MenuVo.class);
        return ResponseResult.okResult(menuVo);
    }

    @Override
    public ResponseResult updateMenu(Menu menu) {

        if (menu.getParentId().equals(menu.getId())) {
            throw new SystemException(AppHttpCodeEnum.UPDATE_MENU_ERROR);
        }
        updateById(menu);
        return ResponseResult.okResult();
    }

    @Override
    public ResponseResult removeMenu(Long id) {

        LambdaQueryWrapper<Menu> queryWrapper = new LambdaQueryWrapper<>();

        queryWrapper.eq(Menu::getParentId, id);
        queryWrapper.ne(Menu::getParentId, 0L);
        List<Menu> menus = list(queryWrapper);

        if (menus.size() > 0) {
            throw new SystemException(AppHttpCodeEnum.HAVE_CHILDREN);
        }

        removeById(id);
        return ResponseResult.okResult();
    }
}
