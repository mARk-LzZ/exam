package com.lzz.exam.controller;

import java.io.UnsupportedEncodingException;
import java.util.*;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.PageDto;
import com.lzz.exam.aop.Log;
import com.lzz.exam.exception.ApiExceptionCode;
import com.lzz.exam.exception.RRException;
import com.lzz.exam.utils.JWTUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import com.lzz.exam.entity.UserEntity;
import com.lzz.exam.service.UserService;
import com.lzz.exam.utils.PageUtils;
import com.lzz.exam.utils.R;
import springfox.documentation.annotations.ApiIgnore;

import javax.servlet.http.HttpServletRequest;


/**
 * 学生信息表
 *
 * @author lzz
 * @email 1399508400@qq.com
 * @date 2022-03-21 15:20:42
 */
@RestController
@RequestMapping("exam/user")
@Slf4j
@Api(description = "用户")
public class UserController {
    @Autowired
    private UserService userService;

    /**
     * 列表
     */
    @PostMapping("/info")
    @Log("用户查询")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN' , 'ROLE_TEACHER')")
    public R list(@RequestBody UserEntity userEntity, @ApiIgnore HttpServletRequest request) throws UnsupportedEncodingException {
        String token=request.getHeader("token");
        Map<String, String> info=JWTUtils.getInfo(token);
        String level=info.get("level");
        LambdaQueryWrapper<UserEntity> wrapper=new LambdaQueryWrapper<>();
        if (Integer.parseInt(level) == 2) {
            //查询用户
            wrapper.clear();
            wrapper.eq(UserEntity::getLevel, 1)
                    .like(StringUtils.isNotEmpty(userEntity.getUsername()), UserEntity::getUsername, userEntity.getUsername())
                    .eq(userEntity.getId() != null, UserEntity::getId, userEntity.getId())
                    .between(userEntity.getStartTime() != null && userEntity.getEndTime() != null, UserEntity::getCreateTime,  userEntity.getStartTime(), userEntity.getEndTime());

        } else if (Integer.parseInt(level) == 3) {
            //查询用户和管理员
            wrapper.clear();
            wrapper.eq(UserEntity::getLevel, 1)
                    .like(StringUtils.isNotEmpty(userEntity.getUsername()), UserEntity::getUsername, userEntity.getUsername())
                    .eq(userEntity.getId() != null, UserEntity::getId, userEntity.getId())
                    .between(userEntity.getStartTime() != null && userEntity.getEndTime() != null, UserEntity::getCreateTime, userEntity.getStartTime(), userEntity.getEndTime())
                    .or()
                    .eq(UserEntity::getLevel, 2)
                    .like(StringUtils.isNotEmpty(userEntity.getUsername()), UserEntity::getUsername, userEntity.getUsername())
                    .eq(userEntity.getId() != null, UserEntity::getId, userEntity.getId())
                    .between(userEntity.getStartTime() != null && userEntity.getEndTime() != null, UserEntity::getCreateTime, userEntity.getStartTime(), userEntity.getEndTime());
        }
        PageDto<UserEntity> result=userService.page(new PageDto<>(userEntity.getPage(), userEntity.getLimit()), wrapper);
        return R.ok().put("user", result);
    }

    @PostMapping("/register")
    public R register(@RequestBody UserEntity userEntity) {
        if (userEntity.getPassword() == null || userEntity.getUsername() == null) {
            throw new RRException(HttpStatus.SC_BAD_REQUEST , "must get password account and level");
        }
        if (userEntity.getLevel() <=0 || userEntity.getLevel()>=3){
            return R.error().put("msg" , "level should be 1 or 2");
        }
        LambdaQueryWrapper<UserEntity> wrapper=new LambdaQueryWrapper<>();
        wrapper.eq(UserEntity::getUsername, userEntity.getUsername());
        UserEntity user=userService.getOne(wrapper);
        if (user != null) {
            return R.error().put("msg" , "acouunt has been registered");
        }
        if (userService.register(userEntity)) {
            log.info("register: " + userEntity);
            return R.ok("register success");
        }
        return R.error("fail to register");
    }

    @GetMapping("/getinfobytoken")
    @ApiOperation("从token里获取用户信息")
    public R getInfoByToken(@ApiIgnore HttpServletRequest request) throws UnsupportedEncodingException {
        String token=request.getHeader("token");
        Map<String, String> info=JWTUtils.getInfo(token);
        return R.ok().put("info", info);
    }

    @PostMapping("/login")
    public R login(@RequestBody UserEntity userEntity) throws UnsupportedEncodingException {

        Integer login=userService.login(userEntity);
        if (login.equals(ApiExceptionCode.ACCOUNT_DOES_NOT_EXIST.getStatus())) {
            return R.error("Account does not exist");
        }
        if (login.equals(ApiExceptionCode.INVALID_PASSWORD.getStatus())) {
            return R.error("Invalid password");
        }
        LambdaQueryWrapper<UserEntity> wrapper=new LambdaQueryWrapper<>();
        wrapper.eq(UserEntity::getUsername, userEntity.getUsername());
        Map<String, String> payload=new HashMap<>();
        UserEntity targetUser=userService.getOne(wrapper);
        payload.put("username", targetUser.getUsername());
        payload.put("level", targetUser.getLevel().toString());
        payload.put("id" , targetUser.getId().toString());
        String token=JWTUtils.getToken(payload);
        if (token != null) {
            log.info("login: " + targetUser);
            return Objects.requireNonNull(Objects.requireNonNull(Objects.requireNonNull(R.ok().put("msg", " user success login")).put("token", token)).put("userAccount", targetUser.getUsername())).put("id" ,targetUser.getId()).put("userLevel", targetUser.getLevel());
        }
        return R.error("Login failed");
    }

    /**
     * 修改
     */
    /**
     * 修改
     */
    @PostMapping("/update")
    @Log("用户信息更新")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    public R update(@RequestBody UserEntity user) throws UnsupportedEncodingException {
           userService.updateById(user);
            return R.ok();
    }

    /**
     * 删除
     */
    @DeleteMapping("/delete")
    @Log("删除用户")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    public R delete(@RequestBody Integer[] ids) throws UnsupportedEncodingException {
        userService.removeByIds(Arrays.asList(ids));
        return R.ok();
    }

    @PostMapping("/refresh")
    @ApiOperation("refresh token")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_STUDENT','ROLE_TEACHER')")
    public R refreshToken(@ApiIgnore HttpServletRequest request) throws UnsupportedEncodingException {
        String token=request.getHeader("token");
        Map<String, String> info=JWTUtils.getInfo(token);
        String newToken=JWTUtils.getToken(info);
        return R.ok().put("new Token: " , newToken);
    }

}
