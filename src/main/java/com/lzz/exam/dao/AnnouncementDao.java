package com.lzz.exam.dao;

import com.lzz.exam.entity.AnnouncementEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * 留言表
 * 
 * @author lzz
 * @email 1399508400@qq.com
 * @date 2022-03-22 16:58:42
 */
@Mapper
public interface AnnouncementDao extends BaseMapper<AnnouncementEntity> {
	
}
