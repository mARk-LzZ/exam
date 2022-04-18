package com.lzz.exam.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;

import com.lzz.exam.entity.SecurityEntity;
import com.lzz.exam.entity.UserEntity;
import com.lzz.exam.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class SpringSecurityUserServiceImpl implements UserDetailsService {
    @Autowired
    private UserService userService;


    @Override
    public SecurityEntity loadUserByUsername(String username) throws UsernameNotFoundException {
        LambdaQueryWrapper<UserEntity> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(UserEntity::getUsername , username);
        UserEntity user=userService.getOne(wrapper);
        if (user == null) {
            throw new UsernameNotFoundException("Invalid account and password");
        }

       return new SecurityEntity(user , getGrantedAuthority(user));
    }

    public List<GrantedAuthority> getGrantedAuthority(UserEntity user) {
        List<GrantedAuthority> auth=new ArrayList<>();
        LambdaQueryWrapper<UserEntity> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(UserEntity::getUsername , user.getUsername());
        UserEntity userEntity = userService.getOne(wrapper);
        if (userEntity != null){
            if (userEntity.getLevel() == 1){
                auth.add(new SimpleGrantedAuthority("ROLE_STUDENT"));
            }else if (userEntity.getLevel()==2){
                auth.add(new SimpleGrantedAuthority("ROLE_TEACHER"));
            }else if (userEntity.getLevel()==3){
                auth.add(new SimpleGrantedAuthority("ROLE_ADMIN"));
            }
            return auth;
        }
        return auth;
    }


}
