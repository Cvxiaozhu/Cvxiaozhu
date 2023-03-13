package com.zhu.blog.entity.domain;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Builder;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

/**
 * 用户和角色关联表(UserRole)表实体类
 *
 * @author makejava
 * @since 2023-03-12 21:16:58
 */
@SuppressWarnings("serial")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@TableName("sys_user_role")
public class UserRole  {

    //用户ID
    private Long userId;
    //角色ID
    private Long roleId;

}
