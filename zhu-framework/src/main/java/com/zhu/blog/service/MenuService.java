package com.zhu.blog.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.zhu.blog.entity.ResponseResult;
import com.zhu.blog.entity.domain.Menu;

import java.util.List;


/**
 * 菜单权限表(Menu)表服务接口
 *
 * @author makejava
 * @since 2023-03-09 20:30:16
 */
public interface MenuService extends IService<Menu> {

    /**
     * 根据用户id查询用户的菜单列表
     * @param userId
     * @return
     */
    List<String> selectPermsByUserId(Long userId);

    /**
     * 查询路由地址
     * @param userId
     * @return
     */
    List<Menu> selectRouterMenuTreeByUserId(Long userId);

    /**
     * 查询所有菜单列表
     * @param status
     * @param menuName
     * @return
     */
    ResponseResult listMenu(String status, String menuName);

    ResponseResult addMenu(Menu menu);

    ResponseResult getMenu(Long id);

    ResponseResult updateMenu(Menu menu);

    ResponseResult removeMenu(Long id);

    ResponseResult treeSelect();

    ResponseResult roleMenuTreeselect(Long roleId);
}
