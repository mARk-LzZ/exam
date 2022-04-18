package com.lzz.exam.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.lzz.exam.entity.PaperEntity;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface PaperDao extends BaseMapper<PaperEntity> {
}
