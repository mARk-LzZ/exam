package com.lzz.exam.service.impl;

import org.springframework.stereotype.Service;
import java.util.Map;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lzz.exam.utils.PageUtils;
import com.lzz.exam.utils.Query;
import com.lzz.exam.dao.SubjectiveQuestionDao;
import com.lzz.exam.entity.SubjectiveQuestionEntity;
import com.lzz.exam.service.SubjectiveQuestionService;


@Service("subjectiveQuestionService")
public class SubjectiveQuestionServiceImpl extends ServiceImpl<SubjectiveQuestionDao, SubjectiveQuestionEntity> implements SubjectiveQuestionService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<SubjectiveQuestionEntity> page = this.page(
                new Query<SubjectiveQuestionEntity>().getPage(params),
                new QueryWrapper<SubjectiveQuestionEntity>()
        );

        return new PageUtils(page);
    }

}