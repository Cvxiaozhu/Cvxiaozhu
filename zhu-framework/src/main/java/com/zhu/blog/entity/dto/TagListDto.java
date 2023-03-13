package com.zhu.blog.entity.dto;

import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

/**
 * @author GH
 * @Description:
 * @date 2023/3/10 20:12
 */
@Data
@Builder
@AllArgsConstructor
@ApiModel(description = "标签查询dto")
public class TagListDto {
    public TagListDto() {
        this.name = "";
        this.remark = "";
    }

    private Long id;

    private String name;

    private String remark;
}
