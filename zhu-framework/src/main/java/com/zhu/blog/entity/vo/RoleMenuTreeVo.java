package com.zhu.blog.entity.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author GH
 * @Description:
 * @date 2023/3/12 19:17
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class RoleMenuTreeVo {

    private List<MenuTreeVo> menus;

    private List<Long> checkedKeys;
}
