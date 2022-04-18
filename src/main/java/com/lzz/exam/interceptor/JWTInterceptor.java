package com.lzz.exam.interceptor;

import com.alibaba.fastjson.JSON;
import com.auth0.jwt.exceptions.AlgorithmMismatchException;
import com.auth0.jwt.exceptions.SignatureVerificationException;
import com.auth0.jwt.exceptions.TokenExpiredException;
import com.lzz.exam.exception.ApiExceptionCode;
import com.lzz.exam.service.impl.SpringSecurityUserServiceImpl;
import com.lzz.exam.utils.JWTUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;


@Slf4j
@Component
public class JWTInterceptor implements HandlerInterceptor {

    @Autowired
    private RedisTemplate<String, String> redisTemplate;
    @Autowired
    private SpringSecurityUserServiceImpl userDetailsService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        log.info("开始拦截");
        Map<String, Object> returnInfo = new HashMap<>();
        //储存用户信息
        Map<String, String> info;
        String token = request.getHeader("token");
        try {
            //验证token 赋值权限
            JWTUtils.verifyToken(token);
            info = JWTUtils.getInfo(token);
            String account = info.get("username");
            UserDetails userDetails = userDetailsService.loadUserByUsername(account);
            UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
            SecurityContextHolder.getContext().setAuthentication(authentication);
            log.info("auth: {}", authentication);
            //获取操作日志

            return true;
        } catch (SignatureVerificationException e) {
            e.printStackTrace();
            response.setStatus(ApiExceptionCode.INVALID_SIGNATURE.getStatus());
            returnInfo.put("msg", ApiExceptionCode.INVALID_SIGNATURE.getMsg());
        } catch (TokenExpiredException e) {
            e.printStackTrace();
            response.setStatus(ApiExceptionCode.TOKEN_EXPIRED.getStatus());
            returnInfo.put("msg", ApiExceptionCode.TOKEN_EXPIRED.getMsg());
        } catch (AlgorithmMismatchException e) {
            e.printStackTrace();
            response.setStatus(ApiExceptionCode.ALGORITHM_MISMATCH.getStatus());
            returnInfo.put("msg", ApiExceptionCode.ALGORITHM_MISMATCH.getMsg());
        } catch (Exception e) {
            e.printStackTrace();
            response.setStatus(ApiExceptionCode.MISSING_TOKEN.getStatus());
            returnInfo.put("msg", ApiExceptionCode.MISSING_TOKEN.getMsg());
        }
        //返回returnInfo
        String s = JSON.toJSONString(returnInfo);
        response.getWriter().print(s);
        return false;
    }
}
