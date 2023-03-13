package com.zhu.blog.entity.vo;

import com.zhu.blog.entity.domain.Menu;
import lombok.*;
import lombok.experimental.Accessors;

import java.util.ArrayList;
import java.util.List;

/**
 * @author GH
 * @Description:
 * @date 2023/3/11 21:55
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Accessors(chain = true)
public class MenuTreeVo {

    private String label;

    private Long id;

    private Long parentId;

    private List<MenuTreeVo> children;
}
