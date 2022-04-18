package com.lzz.exam.service.impl;

import com.lzz.exam.utils.PageUtils;
import com.lzz.exam.utils.Query;
import org.springframework.stereotype.Service;
import java.util.Map;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;


import com.lzz.exam.dao.ExamManageDao;
import com.lzz.exam.entity.ExamManageEntity;
import com.lzz.exam.service.ExamManageService;


@Service("examManageService")
public class ExamManageServiceImpl extends ServiceImpl<ExamManageDao, ExamManageEntity> implements ExamManageService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<ExamManageEntity> page = this.page(
                new Query<ExamManageEntity>().getPage(params),
                new QueryWrapper<ExamManageEntity>()
        );

        return new PageUtils(page);
    }

}