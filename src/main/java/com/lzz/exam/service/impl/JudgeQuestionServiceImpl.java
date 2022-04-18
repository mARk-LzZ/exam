package com.lzz.exam.service.impl;

import org.springframework.stereotype.Service;
import java.util.Map;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lzz.exam.utils.PageUtils;
import com.lzz.exam.utils.Query;

import com.lzz.exam.dao.JudgeQuestionDao;
import com.lzz.exam.entity.JudgeQuestionEntity;
import com.lzz.exam.service.JudgeQuestionService;


@Service("judgeQuestionService")
public class JudgeQuestionServiceImpl extends ServiceImpl<JudgeQuestionDao, JudgeQuestionEntity> implements JudgeQuestionService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<JudgeQuestionEntity> page = this.page(
                new Query<JudgeQuestionEntity>().getPage(params),
                new QueryWrapper<JudgeQuestionEntity>()
        );

        return new PageUtils(page);
    }

}