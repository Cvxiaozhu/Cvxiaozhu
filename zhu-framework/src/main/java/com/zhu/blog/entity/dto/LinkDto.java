package com.zhu.blog.entity.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author GH
 * @Description:
 * @date 2023/3/13 16:51
 */
@NoArgsConstructor
@Data
public class LinkDto {

    /**
     * address
     */
    private String address;
    /**
     * description
     */
    private String description;
    /**
     * id
     */
    private Long id;
    /**
     * logo
     */
    private String logo;
    /**
     * name
     */
    private String name;
    /**
     * status
     */
    private String status;
}
