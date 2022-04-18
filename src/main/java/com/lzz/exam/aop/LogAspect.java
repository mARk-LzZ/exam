package com.lzz.exam.aop;



import cn.hutool.json.JSONUtil;
import com.lzz.exam.dao.WebLogDao;
import com.lzz.exam.utils.JWTUtils;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.util.Map;

@Aspect
@Component
@Slf4j
public class LogAspect {

    @Autowired
    private WebLogDao webLogDao;


    @Pointcut("@annotation(com.lzz.exam.aop.Log)")
    public void logPointCut() {
    }

    @Around("logPointCut()")
    public Object around(ProceedingJoinPoint point) throws Throwable, InterruptedException {
        long startTime = System.currentTimeMillis();
        Object result = point.proceed();
        MethodSignature signature = (MethodSignature) point.getSignature();
        Method method = signature.getMethod();
        long endTime = System.currentTimeMillis();
        String MethodName = method.getName();
        Log logs = method.getAnnotation(Log.class);
        //获取当前请求对象
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();
        String token = request.getHeader("token");
        Map<String, String> info = JWTUtils.getInfo(token);
        String id = info.get("id");
        String username = info.get("username");
        String level = info.get("level");
        WebLog webLog = new WebLog();
        webLog.setUsername(username);
        webLog.setUserid(Integer.valueOf(id));
        webLog.setUserLevel(Integer.valueOf(level));
        webLog.setOperation(logs.value());
        webLog.setMethod(MethodName);
        webLog.setOperationTime(startTime);
        webLog.setSpendTime((int) (endTime - startTime));
        log.info("{}", JSONUtil.parse(webLog));
        webLogDao.insert(webLog);
        return result;
    }
}
