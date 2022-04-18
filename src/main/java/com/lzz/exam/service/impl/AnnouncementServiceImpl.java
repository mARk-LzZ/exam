package com.lzz.exam.service.impl;

import com.lzz.exam.utils.PageUtils;
import com.lzz.exam.utils.Query;
import org.springframework.stereotype.Service;
import java.util.Map;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;


import com.lzz.exam.dao.AnnouncementDao;
import com.lzz.exam.entity.AnnouncementEntity;
import com.lzz.exam.service.AnnouncementService;


@Service("announcementService")
public class AnnouncementServiceImpl extends ServiceImpl<AnnouncementDao, AnnouncementEntity> implements AnnouncementService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<AnnouncementEntity> page = this.page(
                new Query<AnnouncementEntity>().getPage(params),
                new QueryWrapper<AnnouncementEntity>()
        );

        return new PageUtils(page);
    }

}