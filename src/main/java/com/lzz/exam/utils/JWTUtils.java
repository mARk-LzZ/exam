package com.lzz.exam.utils;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTCreator;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;

import java.io.UnsupportedEncodingException;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class JWTUtils {

    private final static String SIGNATURE = "123456";

    /**
     * 生成token header.payload.signature
     */
    public static String getToken(Map<String, String> map) throws UnsupportedEncodingException {
        Calendar instance = Calendar.getInstance();
        instance.add(Calendar.HOUR, 24);
        JWTCreator.Builder builder = JWT.create();
        //payload
        map.forEach(builder::withClaim);
        return builder.withExpiresAt(instance.getTime())
                .sign(Algorithm.HMAC256(SIGNATURE));
    }

    /**
     * 验证token合法性
     */
    public static DecodedJWT verifyToken(String token) throws UnsupportedEncodingException {
        return JWT.require(Algorithm.HMAC256(SIGNATURE)).build().verify(token);
    }

    public static Map<String, String> getInfo(String token) throws UnsupportedEncodingException {
        DecodedJWT verify=JWT.require(Algorithm.HMAC256(SIGNATURE)).build().verify(token);
        Map<String, Claim> claims=verify.getClaims();
        Map<String, String> info = new HashMap<>();
        info.put("level" , claims.get("level").asString());
        info.put("username" , claims.get("username").asString());
        info.put("id" , claims.get("id").asString());
        return info;
    }



}
