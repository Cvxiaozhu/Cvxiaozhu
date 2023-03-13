package com.zhu.blog.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.zhu.blog.entity.domain.Link;
import com.zhu.blog.entity.ResponseResult;
import com.zhu.blog.entity.dto.LinkDto;


/**
 * 友链(Link)表服务接口
 *
 * @author makejava
 * @since 2023-03-02 20:21:46
 */
public interface LinkService extends IService<Link> {

    /**
     * 查询所有的友情链接
     * @return
     */
    ResponseResult getAllLink();

    /**
     *
     * @param pageNum
     * @param pageSize
     * @param name
     * @param status
     * @return
     */
    ResponseResult listLink(Integer pageNum, Integer pageSize, String name, String status);

    ResponseResult addLink(Link link);

    ResponseResult getLink(Long linkId);

    ResponseResult updateLink(LinkDto linkDto);

    ResponseResult deleteLink(Long[] linkIds);

    ResponseResult changeLinkStatus(Link link);
}
