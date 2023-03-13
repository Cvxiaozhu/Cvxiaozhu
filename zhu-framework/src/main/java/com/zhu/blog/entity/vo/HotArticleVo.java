package com.zhu.blog.entity.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author GH
 * @Description: 查询表后有时会返回多余的字段，因此通过Vo来屏蔽掉一些不必要的字段，
 * 也保证了数据库的安全性
 * @date 2023/2/16 16:50
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class HotArticleVo implements Serializable {

    /**
     * 文章id
     */
    private Long id;

    /**
     * 文章标题
     */
    private String title;

    /**
     * 浏览量
     */
    private Long viewCount;
}
