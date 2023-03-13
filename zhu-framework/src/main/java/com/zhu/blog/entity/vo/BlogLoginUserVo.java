package com.zhu.blog.entity.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author GH
 * @Description:
 * @date 2023/3/3 20:07
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class BlogLoginUserVo {
    private String token;

    private UserInfoVo userInfo;
}
