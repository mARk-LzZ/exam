package com.lzz.exam.filter;


import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;


@Component
public class filter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        System.out.println("初始化过滤器");
    }


    @Override
    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws IOException, ServletException {
        HttpServletResponse response = (HttpServletResponse) res;
        String[]  allowDomain= {"http://localhost:9090","http://8.141.56.170:9090","http://www.leaony.xyz","http://47.106.114.12:9090"};
        Set<String> allowedOrigins= new HashSet<String>(Arrays.asList(allowDomain));
        String originHeader=((HttpServletRequest)req).getHeader("Origin");
        if (allowedOrigins.contains(originHeader)){
            response.setHeader("Access-Control-Allow-Origin", originHeader);
            response.setHeader("Access-Control-Allow-Methods", "POST, GET, OPTIONS, DELETE, PUT");
            response.setHeader("Access-Control-Max-Age", "3600");
            response.setHeader("Access-Control-Allow-Headers", "Authorization, Origin, X-Requested-With, Content-Type, Accept, token");
            response.setHeader("Access-Control-Allow-Credentials","true");
            if ("OPTIONS".equals(((HttpServletRequest) req).getMethod())){
                response.setStatus(HttpServletResponse.SC_OK);
                return;
            }
        }
        chain.doFilter(req,res);

    }

    @Override
    public void destroy() {
        System.out.println("销毁过滤器");
    }

}
