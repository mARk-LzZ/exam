package com.lzz.exam.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.lzz.exam.utils.PageUtils;

import com.lzz.exam.entity.UserEntity;

import java.util.Map;

/**
 * 学生信息表
 *
 * @author lzz
 * @email 1399508400@qq.com
 * @date 2022-03-21 15:20:42
 */
public interface UserService extends IService<UserEntity> {

    PageUtils queryPage(Map<String, Object> params);

    Integer login(UserEntity userEntity);

    Integer visitorLogin(String visitorName , String password);

    boolean register(UserEntity user);

    boolean forgetPassword(UserEntity user);
}

