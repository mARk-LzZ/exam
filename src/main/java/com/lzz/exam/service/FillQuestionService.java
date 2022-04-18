package com.lzz.exam.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.lzz.exam.utils.PageUtils;

import com.lzz.exam.entity.FillQuestionEntity;

import java.util.Map;

/**
 * 填空题题库
 *
 * @author lzz
 * @email 1399508400@qq.com
 * @date 2022-03-21 15:20:42
 */
public interface FillQuestionService extends IService<FillQuestionEntity> {

    PageUtils queryPage(Map<String, Object> params);
}

