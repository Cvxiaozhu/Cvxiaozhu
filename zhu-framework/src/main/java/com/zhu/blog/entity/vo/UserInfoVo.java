package com.zhu.blog.entity.vo;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @author GH
 * @Description:
 * @date 2023/3/3 20:08
 */
@Data
@Accessors(chain = true)
public class UserInfoVo {
    /**
     * 主键
     */
    private Long id;

    /**
     * 昵称
     */
    private String nickName;

    /**
     * 头像
     */
    private String avatar;

    private String sex;

    private String email;

    private String status;

}