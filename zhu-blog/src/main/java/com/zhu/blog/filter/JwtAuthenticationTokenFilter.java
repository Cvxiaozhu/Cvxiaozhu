package com.zhu.blog.filter;

import com.alibaba.fastjson.JSON;
import com.zhu.blog.entity.domain.LoginUser;
import com.zhu.blog.entity.ResponseResult;
import com.zhu.blog.enums.AppHttpCodeEnum;
import com.zhu.blog.utils.JwtUtil;
import com.zhu.blog.utils.RedisCache;
import com.zhu.blog.utils.WebUtils;
import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Objects;

/**
 * @author GH
 * @Description:
 * @date 2023/3/3 20:40
 */
@Component
public class JwtAuthenticationTokenFilter extends OncePerRequestFilter {

    @Autowired
    private RedisCache redisCache;

    @Override
    protected void doFilterInternal(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, FilterChain filterChain) throws ServletException, IOException {
        // 获取请求头中的token
        String token = httpServletRequest.getHeader("token");

        // 判断请求头中token是否为空
        // todo 如果字符串里面的值为null， ""， "  "，那么返回值为false；否则为true
        if (!StringUtils.hasText(token)) {
            filterChain.doFilter(httpServletRequest, httpServletResponse);
            return;
        }

        // 解析获取的userid
        Claims claims;
        try {
            claims = JwtUtil.parseJWT(token);
        } catch (Exception e) {
            e.printStackTrace();
            // token 无效
            ResponseResult responseResult = ResponseResult.errorResult(AppHttpCodeEnum.NEED_LOGIN);
            WebUtils.renderString(httpServletResponse, JSON.toJSONString(responseResult));
            return;
        }

        String userId = claims.getSubject();
        // 查redis缓存
        LoginUser loginUser = redisCache.getCacheObject("bloglogin:" + userId);

        if (Objects.isNull(loginUser)) {
            //说明登录过期  提示重新登录
            ResponseResult result = ResponseResult.errorResult(AppHttpCodeEnum.NEED_LOGIN);
            WebUtils.renderString(httpServletResponse, JSON.toJSONString(result));
            return;
        }

        // 存入SecurityContextHolder
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(loginUser, null, null);
        SecurityContextHolder.getContext().setAuthentication(authenticationToken);

        filterChain.doFilter(httpServletRequest, httpServletResponse);

    }
}
