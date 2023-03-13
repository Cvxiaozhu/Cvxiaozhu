package com.zhu.blog.entity.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.util.List;

/**
 * @author GH
 */
@Data
@Accessors(chain = true)
@AllArgsConstructor
@NoArgsConstructor
public class AdminUserInfoVo {

    /**
     * 权限信息
     */
    private List<String> permissions;

    /**
     * 角色信息
     */
    private List<String> roles;

    /**
     * 用户信息
     */
    private UserInfoVo user;
}