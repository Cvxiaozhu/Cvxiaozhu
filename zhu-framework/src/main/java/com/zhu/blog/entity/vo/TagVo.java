package com.zhu.blog.entity.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author GH
 * @Description:
 * @date 2023/3/10 20:21
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TagVo {

    private Long id;
    //标签名
    private String name;

    //备注
    private String remark;
}
