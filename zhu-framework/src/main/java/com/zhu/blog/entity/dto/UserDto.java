package com.zhu.blog.entity.dto;

import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 用户表(User)表实体类
 *
 * @author makejava
 * @since 2023-03-02 20:52:30
 */
@SuppressWarnings("serial")
@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(description = "用户登录dto")
public class UserDto {
    //用户名
    private String userName;

    //密码
    private String password;

}
