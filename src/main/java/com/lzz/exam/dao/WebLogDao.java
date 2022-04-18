package com.lzz.exam.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.lzz.exam.aop.WebLog;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface WebLogDao extends BaseMapper<WebLog> {
}
