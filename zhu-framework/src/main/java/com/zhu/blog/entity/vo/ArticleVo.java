package com.zhu.blog.entity.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

/**
 * @author GH
 * @Description:
 * @date 2023/3/2 19:04
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ArticleVo {
    private Long id;
    //标题
    private String title;
    //文章摘要
    private String summary;
    //所属分类id
    private Long categoryId;

    private String content;

    //缩略图
    private String thumbnail;

    //访问量
    private Long viewCount;

    private Date createTime;

    private String isTop;

    private String status;

    private String isComment;

    private List<Long> tags;

}
