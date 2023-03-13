package com.zhu.blog.constants;

/**
 * @author GH
 * @Description: 常量
 * @date 2023/2/16 16:58
 */
public class SystemConstants {
    /**
     * 文章是草稿
     */
    public static final int ARTICLE_STATUS_DRAFT = 1;
    /**
     * 文章是正常发布状态
     */
    public static final String ARTICLE_STATUS_NORMAL = "0";

    /**
     * 链接是正常发布状态
     */
    public static final String LINK_STATUS_NORMAL = "0";

    /**
     * 分类状态 1禁用 0正常
     */
    public static final String STATUS_NORMAL = "0";

    /**
     * 文章根评论
     */
    public static final Integer ROOT_COMMENT = -1;

    /**
     * 文章评论
     */
    public static final String ARTICLE_COMMENT = "0";

    /**
     * 友链评论
     */
    public static final String LINK_COMMENT = "1";

    /**
     * 文章浏览量
     */
    public static final String ARTICLE_VIEW_COUNT = "article:viewCount";

    /**
     * 图片格式
     */
    public static final String PNG_PICTURE = "png";
    public static final String JPEG_PICTURE = "jpeg";
    public static final String JPG_PICTURE = "jpg";

    /**
     * 菜单类型
     */
    public static final String MENU = "C";
    public static final String BUTTON = "F";

    /**
     * 管理员权限
     */
    public static final String ADMIN = "0";
}
