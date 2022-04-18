package com.lzz.exam.service.impl;

import org.springframework.stereotype.Service;
import java.util.Map;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lzz.exam.utils.PageUtils;
import com.lzz.exam.utils.Query;

import com.lzz.exam.dao.FillQuestionDao;
import com.lzz.exam.entity.FillQuestionEntity;
import com.lzz.exam.service.FillQuestionService;


@Service("fillQuestionService")
public class FillQuestionServiceImpl extends ServiceImpl<FillQuestionDao, FillQuestionEntity> implements FillQuestionService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<FillQuestionEntity> page = this.page(
                new Query<FillQuestionEntity>().getPage(params),
                new QueryWrapper<FillQuestionEntity>()
        );

        return new PageUtils(page);
    }

}