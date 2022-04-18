package com.lzz.exam.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.lzz.exam.entity.ClazzEntity;
import com.lzz.exam.utils.PageUtils;

import java.util.Map;

/**
 * 班级表
 */
public interface ClazzService extends IService<ClazzEntity> {
    PageUtils queryPage(Map<String, Object> params);

}
