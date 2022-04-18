package com.lzz.exam.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.lzz.exam.utils.PageUtils;

import com.lzz.exam.entity.JudgeQuestionEntity;

import java.util.Map;

/**
 * 判断题题库表
 *
 * @author lzz
 * @email 1399508400@qq.com
 * @date 2022-03-21 15:20:42
 */
public interface JudgeQuestionService extends IService<JudgeQuestionEntity> {

    PageUtils queryPage(Map<String, Object> params);
}

