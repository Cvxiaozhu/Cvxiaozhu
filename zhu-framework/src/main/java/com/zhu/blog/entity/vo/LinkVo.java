package com.zhu.blog.entity.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


/**
 * @author GH
 * @Description:
 * @date 2023/3/2 20:30
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LinkVo {

    private Long id;

    private String name;

    private String logo;

    private String description;
    //网站地址
    private String address;

    private String status;

}
