package com.zhu.blog.entity.vo;

import com.zhu.blog.entity.domain.Role;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author GH
 * @Description:
 * @date 2023/3/12 21:37
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserRoleVo {

    private UserInfoVo user;

    private List<Role> roles;

    private List<Long> roleIds;
}
