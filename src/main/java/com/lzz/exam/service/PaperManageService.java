package com.lzz.exam.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.lzz.exam.utils.PageUtils;

import com.lzz.exam.entity.PaperManageEntity;

import java.util.List;
import java.util.Map;

/**
 * 试卷管理表
 *
 * @author lzz
 * @email 1399508400@qq.com
 * @date 2022-03-21 15:20:42
 */
public interface PaperManageService extends IService<PaperManageEntity> {

    PageUtils queryPage(Map<String, Object> params);

    //查看试卷
    Map<String , List<Object>> paperSelect(Integer paperId);
}

