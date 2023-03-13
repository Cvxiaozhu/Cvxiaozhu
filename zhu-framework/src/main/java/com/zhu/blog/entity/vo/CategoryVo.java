package com.zhu.blog.entity.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author GH
 * @Description:
 * @date 2023/2/16 22:42
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CategoryVo {

    private Long id;

    private String name;

    private String description;

    private String status;

}
