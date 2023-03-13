package com.zhu.blog.service.impl;

import com.zhu.blog.entity.domain.LoginUser;
import com.zhu.blog.entity.domain.User;
import com.zhu.blog.entity.ResponseResult;
import com.zhu.blog.entity.vo.BlogLoginUserVo;
import com.zhu.blog.entity.vo.UserInfoVo;
import com.zhu.blog.service.BlogService;
import com.zhu.blog.utils.BeanCopyUtils;
import com.zhu.blog.utils.JwtUtil;
import com.zhu.blog.utils.RedisCache;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Objects;


/**
 * @author GH
 * @Description:
 * @date 2023/3/3 19:56
 */
@Service
public class BlogServiceImpl implements BlogService {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private RedisCache redisCache;

    @Override
    public ResponseResult logout() {
        // 获取当前登录用户
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        LoginUser loginUser = (LoginUser) authentication.getPrincipal();
        // 获取用户Id
        Long userId = loginUser.getUser().getId();
        // 删除redis中的用户信息
        redisCache.deleteObject("bloglogin:" + userId);
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
        redisCache.setCacheObject("bloglogin:" + userId, loginUser);

        // 封装成userInfo对象
        UserInfoVo userInfoVo = BeanCopyUtils.copyBean(loginUser.getUser(), UserInfoVo.class);
        BlogLoginUserVo blogLoginUserVo = new BlogLoginUserVo(jwt, userInfoVo);

        return ResponseResult.okResult(blogLoginUserVo);
    }
}
