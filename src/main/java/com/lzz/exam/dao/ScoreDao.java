package com.lzz.exam.dao;

import com.lzz.exam.entity.ScoreEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * 成绩管理表
 * 
 * @author lzz
 * @email 1399508400@qq.com
 * @date 2022-03-21 15:20:42
 */
@Mapper
public interface ScoreDao extends BaseMapper<ScoreEntity> {
	
}
