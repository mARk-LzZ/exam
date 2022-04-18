package com.lzz.exam.service.impl;

import org.springframework.stereotype.Service;
import java.util.Map;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lzz.exam.utils.PageUtils;
import com.lzz.exam.utils.Query;
import com.lzz.exam.dao.MultiQuestionDao;
import com.lzz.exam.entity.MultiQuestionEntity;
import com.lzz.exam.service.MultiQuestionService;


@Service("multiQuestionService")
public class MultiQuestionServiceImpl extends ServiceImpl<MultiQuestionDao, MultiQuestionEntity> implements MultiQuestionService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<MultiQuestionEntity> page = this.page(
                new Query<MultiQuestionEntity>().getPage(params),
                new QueryWrapper<MultiQuestionEntity>()
        );

        return new PageUtils(page);
    }

}