package com.lzz.exam.entity;

import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Data
public class SecurityEntity implements UserDetails {

    private String username;

    private String password;

    private UserEntity userEntity;

    private List<? extends GrantedAuthority> userAuth;


    /**
     * 构造函数
     * @param userEntity
     */
    public SecurityEntity(UserEntity userEntity){
        this.username = userEntity.getUsername();
        this.password = userEntity.getPassword();
        this.userEntity = userEntity;
    }


    public SecurityEntity(UserEntity userEntity , List<? extends GrantedAuthority> userAuth){
        this.username = userEntity.getUsername();
        this.password = userEntity.getPassword();
        this.userEntity = userEntity;
        this.userAuth = userAuth;
    }






    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<GrantedAuthority> authorities = new ArrayList<>();
        for (GrantedAuthority u : userAuth) {
            authorities.add(new SimpleGrantedAuthority(u.getAuthority()));
        }
        return authorities;
    }

    @Override
    public String getPassword() {
        return null;
    }

    @Override
    public String getUsername() {
        return null;
    }

    @Override
    public boolean isAccountNonExpired() {
        return false;
    }

    @Override
    public boolean isAccountNonLocked() {
        return false;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return false;
    }

    @Override
    public boolean isEnabled() {
        return false;
    }
}
