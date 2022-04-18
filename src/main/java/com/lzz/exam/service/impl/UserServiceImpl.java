package com.lzz.exam.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lzz.exam.dao.UserDao;
import com.lzz.exam.entity.UserEntity;
import com.lzz.exam.exception.ApiExceptionCode;
import com.lzz.exam.service.UserService;
import com.lzz.exam.utils.PageUtils;
import com.lzz.exam.utils.Query;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Map;


@Service("userService")
public class UserServiceImpl extends ServiceImpl<UserDao, UserEntity> implements UserService {

    BCryptPasswordEncoder bCryptPasswordEncoder=new BCryptPasswordEncoder();

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<UserEntity> page=this.page(
                new Query<UserEntity>().getPage(params),
                new QueryWrapper<UserEntity>()
        );

        return new PageUtils(page);
    }

    @Override
    public Integer login(UserEntity userEntity) {
        UserEntity targetUser;
        if (userEntity.getUsername() != null && userEntity.getPassword() != null) {
            QueryWrapper<UserEntity> wrapper=new QueryWrapper<>();
            wrapper.eq("username", userEntity.getUsername());
            targetUser=this.getOne(wrapper);
            if (targetUser != null) {
                if (bCryptPasswordEncoder.matches(userEntity.getPassword(), targetUser.getPassword())) {
                    return 200;
                }
                return ApiExceptionCode.INVALID_PASSWORD.getStatus();
            }
            return ApiExceptionCode.ACCOUNT_DOES_NOT_EXIST.getStatus();
        }
        return ApiExceptionCode.ACCOUNT_DOES_NOT_EXIST.getStatus();
    }

    @Override
    public Integer visitorLogin(String visitorName, String password) {
        LambdaQueryWrapper<UserEntity> wrapper=new LambdaQueryWrapper<>();
        wrapper.eq(UserEntity::getUsername, visitorName);
        UserEntity user=this.getOne(wrapper);
        if (user != null) {
            if (bCryptPasswordEncoder.matches(password, user.getPassword())) {
                return 200;
            }
            return ApiExceptionCode.INVALID_PASSWORD.getStatus();
        }
        return ApiExceptionCode.ACCOUNT_DOES_NOT_EXIST.getStatus();
    }

    @Override
    public boolean register(UserEntity user) {
        String pwd=bCryptPasswordEncoder.encode(user.getPassword());
        user.setPassword(pwd);
        return this.save(user);
    }

    @Override
    public boolean forgetPassword(UserEntity user) {
        QueryWrapper<UserEntity> wrapper=new QueryWrapper<>();
        wrapper.eq("passques", user.getPassques());
        UserEntity checkUser=wrapper.getEntity();
        return checkUser.getPassans().equals(user.getPassans());
    }


}