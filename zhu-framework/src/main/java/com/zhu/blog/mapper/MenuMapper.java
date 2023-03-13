package com.zhu.blog.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zhu.blog.entity.domain.Menu;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.ResultType;

import java.util.List;


/**
 * 菜单权限表(Menu)表数据库访问层
 *
 * @author makejava
 * @since 2023-03-09 20:30:14
 */
public interface MenuMapper extends BaseMapper<Menu> {

    List<String> selectPermsByUserId(@Param("userId") Long userId);

    List<Menu> selectRouterMenuTreeByUserId(Long userId);

    List<Menu> selectAllRouterMenu();
}

