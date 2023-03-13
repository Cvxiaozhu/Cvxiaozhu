package com.zhu.blog.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.zhu.blog.constants.SystemConstants;
import com.zhu.blog.entity.domain.LoginUser;
import com.zhu.blog.entity.domain.User;
import com.zhu.blog.mapper.MenuMapper;
import com.zhu.blog.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Objects;

/**
 * @author GH
 * @Description:
 * @date 2023/3/3 20:11
 */
@Service
public class UserDetailsImpl implements UserDetailsService {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private MenuMapper menuMapper;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        // 查数据库是否存在该用户
        LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(User::getUserName, username);
        User user = userMapper.selectOne(queryWrapper);

        if (Objects.isNull(user)) {
            throw new RuntimeException("用户不存在");
        }
        // 返回用户信息

        // todo 查询权限信息的封装
        if (user.getType().equals(SystemConstants.ADMIN)) {
            return new LoginUser(user, menuMapper.selectPermsByUserId(user.getId()));
        }

        return new LoginUser(user, null);
    }
}
