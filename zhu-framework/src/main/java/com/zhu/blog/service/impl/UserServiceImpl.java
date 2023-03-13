package com.zhu.blog.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zhu.blog.entity.domain.LoginUser;
import com.zhu.blog.entity.domain.User;
import com.zhu.blog.entity.ResponseResult;
import com.zhu.blog.entity.vo.UserInfoVo;
import com.zhu.blog.enums.AppHttpCodeEnum;
import com.zhu.blog.handler.exception.SystemException;
import com.zhu.blog.mapper.UserMapper;
import com.zhu.blog.service.UserService;
import com.zhu.blog.utils.BeanCopyUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Objects;

/**
 * 用户表(User)表服务实现类
 *
 * @author makejava
 * @since 2023-03-04 21:16:45
 */
@Service("userService")
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public ResponseResult updateUserInfo(User user) {
        LoginUser loginUser = (LoginUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (Objects.isNull(loginUser)) {
            throw new SystemException(AppHttpCodeEnum.SYSTEM_ERROR);
        }
        updateById(user);
        return ResponseResult.okResult();
    }

    @Override
    public ResponseResult userInfo() {

        // 从当前的SecurityContextHolder中获取LoginUser对象
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        LoginUser loginUser = (LoginUser) authentication.getPrincipal();

        User user = getById(loginUser.getUser().getId());
        UserInfoVo userInfoVo = BeanCopyUtils.copyBean(user, UserInfoVo.class);

        return ResponseResult.okResult(userInfoVo);
    }

    @Override
    public ResponseResult register(User user) {

        //对数据进行非空判断
        if(!StringUtils.hasText(user.getUserName())){
            throw new SystemException(AppHttpCodeEnum.USERNAME_NOT_NULL);
        }
        if(!StringUtils.hasText(user.getPassword())){
            throw new SystemException(AppHttpCodeEnum.PASSWORD_NOT_NULL);
        }
        if(!StringUtils.hasText(user.getEmail())){
            throw new SystemException(AppHttpCodeEnum.EMAIL_NOT_NULL);
        }
        if(!StringUtils.hasText(user.getNickName())){
            throw new SystemException(AppHttpCodeEnum.NICKNAME_NOT_NULL);
        }

        // 判断该用户是否已存在
        LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();

        queryWrapper.eq(User::getUserName, user.getUserName());
        User registerUser = getOne(queryWrapper);

        // 用户不存在则抛出异常交给spring管理
        if (Objects.nonNull(registerUser)) {
            throw new SystemException(AppHttpCodeEnum.USERNAME_EXIST);
        }
        String password = passwordEncoder.encode(user.getPassword());
        user.setPassword(password);
        // 保存用户
        save(user);
        return ResponseResult.okResult();
    }
}
