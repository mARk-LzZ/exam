package com.lzz.exam.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;

import com.lzz.exam.entity.AnnouncementEntity;
import com.lzz.exam.utils.PageUtils;

import java.util.Map;

/**
 * 留言表
 *
 * @author lzz
 * @email 1399508400@qq.com
 * @date 2022-03-22 16:58:42
 */
public interface AnnouncementService extends IService<AnnouncementEntity> {

    PageUtils queryPage(Map<String, Object> params);

}

