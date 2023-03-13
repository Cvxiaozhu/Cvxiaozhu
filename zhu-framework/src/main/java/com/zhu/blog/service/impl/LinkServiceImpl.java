package com.zhu.blog.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zhu.blog.constants.SystemConstants;
import com.zhu.blog.entity.domain.Category;
import com.zhu.blog.entity.domain.Link;
import com.zhu.blog.entity.ResponseResult;
import com.zhu.blog.entity.dto.LinkDto;
import com.zhu.blog.entity.vo.LinkVo;
import com.zhu.blog.entity.vo.PageVo;
import com.zhu.blog.mapper.LinkMapper;
import com.zhu.blog.service.LinkService;
import com.zhu.blog.utils.BeanCopyUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 友链(Link)表服务实现类
 *
 * @author makejava
 * @since 2023-03-02 20:21:47
 */
@Service("linkService")
public class LinkServiceImpl extends ServiceImpl<LinkMapper, Link> implements LinkService {

    @Override
    public ResponseResult getAllLink() {

        LambdaQueryWrapper<Link> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Link::getStatus, SystemConstants.LINK_STATUS_NORMAL);
        List<Link> list = list(queryWrapper);
        List<LinkVo> linkVos = BeanCopyUtils.copyBeanList(list, LinkVo.class);

        return ResponseResult.okResult(linkVos);
    }

    @Override
    public ResponseResult listLink(Integer pageNum, Integer pageSize, String name, String status) {

        LambdaQueryWrapper<Link> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.like(StringUtils.hasText(name), Link::getName, name);
        queryWrapper.like(StringUtils.hasText(status), Link::getStatus, status);

        Page<Link> page = new Page<>(pageNum, pageSize);
        page(page, queryWrapper);

        PageVo pageVo = new PageVo(page.getRecords(), page.getTotal());

        return ResponseResult.okResult(pageVo);
    }

    @Override
    public ResponseResult addLink(Link link) {
        save(link);
        return ResponseResult.okResult();
    }

    @Override
    public ResponseResult getLink(Long linkId) {

        Link link = getById(linkId);
        LinkVo linkVo = BeanCopyUtils.copyBean(link, LinkVo.class);
        return ResponseResult.okResult(linkVo);
    }

    @Override
    public ResponseResult updateLink(LinkDto linkDto) {
        Link link = BeanCopyUtils.copyBean(linkDto, Link.class);
        updateById(link);
        return ResponseResult.okResult();
    }

    @Override
    public ResponseResult deleteLink(Long[] linkIds) {

        List<Long> collect = Arrays.stream(linkIds).collect(Collectors.toList());
        removeByIds(collect);
        return ResponseResult.okResult();
    }

    @Override
    public ResponseResult changeLinkStatus(Link link) {
        // 获取友链的状态和id
        Long id = link.getId();
        String status = link.getStatus();
        // 如果status 为 0 代表通过 为 1则不通过 2为未审核
        updateById(link);
        return ResponseResult.okResult();

    }
}
