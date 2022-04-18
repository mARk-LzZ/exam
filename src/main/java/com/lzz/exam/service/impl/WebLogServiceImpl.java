package com.lzz.exam.service.impl;


import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lzz.exam.aop.WebLog;
import com.lzz.exam.dao.WebLogDao;
import com.lzz.exam.service.WebLogService;
import org.springframework.stereotype.Service;

@Service("webLogService")
public class WebLogServiceImpl extends ServiceImpl<WebLogDao, WebLog> implements WebLogService {
}
