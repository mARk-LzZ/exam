package com.lzz.exam.service.impl;

import com.lzz.exam.entity.*;
import com.lzz.exam.exception.RRException;
import com.lzz.exam.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lzz.exam.utils.PageUtils;
import com.lzz.exam.utils.Query;
import com.lzz.exam.dao.PaperManageDao;


@Service("paperManageService")
public class PaperManageServiceImpl extends ServiceImpl<PaperManageDao, PaperManageEntity> implements PaperManageService {

    @Autowired
    private FillQuestionService fillQuestionService;

    @Autowired
    private JudgeQuestionService judgeQuestionService;

    @Autowired
    private MultiQuestionService multiQuestionService;

    @Autowired
    private SubjectiveQuestionService subjectiveQuestionService;
    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<PaperManageEntity> page = this.page(
                new Query<PaperManageEntity>().getPage(params),
                new QueryWrapper<PaperManageEntity>()
        );

        return new PageUtils(page);
    }

    @Override
    public Map<String , List<Object>> paperSelect(Integer paperId) throws RRException {
        //map存放试卷
        Map<String , List<Object>> paper = new HashMap<>();
        //填空题列表
        List<FillQuestionEntity> fillQuestionEntities = new ArrayList<>();
        //选择题列表
        List<MultiQuestionEntity> multiQuestionEntities = new ArrayList<>();
        //判断题列表
        List<JudgeQuestionEntity> judgeQuestionEntities = new ArrayList<>();
        //主观题列表
        List<SubjectiveQuestionEntity> subjectiveQuestionEntities = new ArrayList<>();
        QueryWrapper<PaperManageEntity> paperManageEntityQueryWrapper=new QueryWrapper<>();
        paperManageEntityQueryWrapper.eq("paperId" , paperId);
        //存放对应关系
        List<PaperManageEntity> list=this.list(paperManageEntityQueryWrapper);
        for (PaperManageEntity paperManageEntity : list) {
            System.out.println(paperManageEntity);
            //如果问题类型为1 存放填空题
            if (paperManageEntity.getQuestiontype() == 1) {
                QueryWrapper<FillQuestionEntity> fillQuestionEntityQueryWrapper=new QueryWrapper<>();
                fillQuestionEntityQueryWrapper.eq("id", paperManageEntity.getQuestionid());
                fillQuestionEntities.add(fillQuestionService.getOne(fillQuestionEntityQueryWrapper));
                paper.put("fillQuestions", Collections.singletonList(fillQuestionEntities));
                //2判断
            } else if (paperManageEntity.getQuestiontype() == 2) {
                QueryWrapper<JudgeQuestionEntity> judgeQuestionEntityQueryWrapper=new QueryWrapper<>();
                judgeQuestionEntityQueryWrapper.eq("id", paperManageEntity.getQuestionid());
                judgeQuestionEntities.add(judgeQuestionService.getOne(judgeQuestionEntityQueryWrapper));
                paper.put("judgeQuestions", Collections.singletonList(judgeQuestionEntities));
                //3选择
            } else if (paperManageEntity.getQuestiontype() == 3) {
                QueryWrapper<MultiQuestionEntity> multiQuestionEntityQueryWrapper=new QueryWrapper<>();
                multiQuestionEntityQueryWrapper.eq("id", paperManageEntity.getQuestionid());
                multiQuestionEntities.add(multiQuestionService.getOne(multiQuestionEntityQueryWrapper));
                paper.put("multiQuestions", Collections.singletonList(multiQuestionEntities));
            } else if (paperManageEntity.getQuestiontype() == 4) {
                //4简答
                QueryWrapper<SubjectiveQuestionEntity> subjectiveQuestionEntityQueryWrapper=new QueryWrapper<>();
                subjectiveQuestionEntityQueryWrapper.eq("id", paperManageEntity.getQuestionid());
                subjectiveQuestionEntities.add(subjectiveQuestionService.getOne(subjectiveQuestionEntityQueryWrapper));
                paper.put("subjectiveQuestions", Collections.singletonList(subjectiveQuestionEntities));
            }
        }
        return paper;

    }
}