package com.zhu.blog.entity.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author GH
 * @Description:
 * @date 2023/3/12 21:12
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class SystemUserDto {
    private Long id;

    private String userName;

    private String password;

    private String email;

    private String nickName;

    private String phoneNumber;

    private String status;

    private String sex;

    private List<Long> roleIds;
}
