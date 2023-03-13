package com.zhu.blog.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zhu.blog.entity.domain.Link;
import com.zhu.blog.entity.domain.LoginUser;
import com.zhu.blog.entity.domain.User;
import com.zhu.blog.entity.ResponseResult;
import com.zhu.blog.entity.vo.PageVo;
import com.zhu.blog.service.AdminLoginService;
import com.zhu.blog.service.UserService;
import com.zhu.blog.utils.JwtUtil;
import com.zhu.blog.utils.RedisCache;
import com.zhu.blog.utils.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.security.Security;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;


/**
 * @author GH
 * @Description:
 * @date 2023/3/3 19:56
 */
@Service
public class AdminLoginServiceImpl implements AdminLoginService {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private RedisCache redisCache;


    @Override
    public ResponseResult logout() {

        Long userId = SecurityUtils.getUserId();
        redisCache.deleteObject("login:"+ userId);

        return ResponseResult.okResult();
    }

    @Override
    public ResponseResult login(User user) {

        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(user.getUserName(), user.getPassword());
        Authentication authenticate = authenticationManager.authenticate(authenticationToken);

        if (Objects.isNull(authenticate)) {
            throw new RuntimeException("用户名或密码错误");
        }

        LoginUser loginUser = (LoginUser) authenticate.getPrincipal();
        String userId = loginUser.getUser().getId().toString();

        //   生成token
        String jwt = JwtUtil.createJWT(userId);

        // 存入redis
        redisCache.setCacheObject("login:" + userId, loginUser);

        // 封装成userInfo对象
        Map<String,String> map = new HashMap<>();
        map.put("token",jwt);
        return ResponseResult.okResult(map);
    }
}
