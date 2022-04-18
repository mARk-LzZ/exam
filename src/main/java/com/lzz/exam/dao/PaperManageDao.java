package com.lzz.exam.dao;

import com.lzz.exam.entity.PaperManageEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * 试卷管理表
 * 
 * @author lzz
 * @email 1399508400@qq.com
 * @date 2022-03-21 15:20:42
 */
@Mapper
public interface PaperManageDao extends BaseMapper<PaperManageEntity> {
	
}
