package com.lzz.exam.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lzz.exam.dao.ClazzDao;
import com.lzz.exam.entity.AnnouncementEntity;
import com.lzz.exam.entity.ClazzEntity;
import com.lzz.exam.service.ClazzService;
import com.lzz.exam.utils.PageUtils;
import com.lzz.exam.utils.Query;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service("ClazzService")
public class ClazzServiceImpl extends ServiceImpl<ClazzDao, ClazzEntity> implements ClazzService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<ClazzEntity> page = this.page(
                new Query<ClazzEntity>().getPage(params),
                new QueryWrapper<>()
        );

        return new PageUtils(page);
    }
}
