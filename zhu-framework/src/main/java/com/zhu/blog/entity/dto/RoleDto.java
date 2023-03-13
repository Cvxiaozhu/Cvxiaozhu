package com.zhu.blog.entity.dto;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.ibatis.type.Alias;
import org.springframework.core.annotation.AliasFor;

import java.util.List;

/**
 * @author GH
 * @Description:
 * @date 2023/3/11 21:28
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class RoleDto {
    private Long id;

    private String roleName;

    private String roleKey;

    private Integer roleSort;

    private String status;

    private List<Long> menuIds;

    private String remark;
}
