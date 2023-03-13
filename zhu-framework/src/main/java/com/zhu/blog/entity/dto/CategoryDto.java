package com.zhu.blog.entity.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author GH
 * @Description:
 * @date 2023/3/13 15:47
 */
@NoArgsConstructor
@Data
@AllArgsConstructor
public class CategoryDto {


    /**
     * description
     */
    private String description;
    /**
     * id
     */
    private String id;
    /**
     * name
     */
    private String name;
    /**
     * status
     */
    private String status;
}
