package com.zhu.blog.entity.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author GH
 * @Description:
 * @date 2023/3/11 21:28
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class RoleVo {
    private Long id;

   private String remark;

   private String roleKey;

    //角色名称
    private String roleName;
    //显示顺序
    private Integer roleSort;
    //角色状态（0正常 1停用）
    private String status;
}
