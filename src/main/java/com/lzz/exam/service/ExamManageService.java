package com.lzz.exam.service;

import com.baomidou.mybatisplus.extension.service.IService;

import com.lzz.exam.entity.ExamManageEntity;
import com.lzz.exam.utils.PageUtils;

import java.util.Map;

/**
 * 考试管理表
 *
 * @author lzz
 * @email 1399508400@qq.com
 * @date 2022-03-21 15:20:42
 */
public interface ExamManageService extends IService<ExamManageEntity> {

    PageUtils queryPage(Map<String, Object> params);
}

