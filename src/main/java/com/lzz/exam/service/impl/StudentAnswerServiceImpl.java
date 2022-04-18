package com.lzz.exam.service.impl;

import com.lzz.exam.dto.StudentAnswerDTO;
import com.lzz.exam.dto.StudentAnswerScoreDTO;
import com.lzz.exam.entity.*;
import com.lzz.exam.service.*;
import com.lzz.exam.utils.R;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lzz.exam.utils.PageUtils;
import com.lzz.exam.utils.Query;

import com.lzz.exam.dao.StudentAnswerDao;


@Service("studentAnswerService")
@Slf4j
public class StudentAnswerServiceImpl extends ServiceImpl<StudentAnswerDao, StudentAnswerEntity> implements StudentAnswerService {

    //储存所有未批改试题
    private final List<StudentAnswerEntity> questionsWaitingCorrect = new ArrayList<>();

    @Autowired
    private FillQuestionService fillQuestionService;

    @Autowired
    private JudgeQuestionService judgeQuestionService;

    @Autowired
    private MultiQuestionService multiQuestionService;

    @Autowired
    private SubjectiveQuestionService subjectiveQuestionService;
    @Autowired
    private StudentAnswerDao studentAnswerDao;

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<StudentAnswerEntity> page = this.page(
                new Query<StudentAnswerEntity>().getPage(params),
                new QueryWrapper<StudentAnswerEntity>()
        );

        return new PageUtils(page);
    }

    @Override
    public int questionSubmit(StudentAnswerEntity studentAnswerEntity) {
        if (studentAnswerEntity.getQuestionType() == 1) {
            QueryWrapper<FillQuestionEntity> wrapper = new QueryWrapper<>();
            wrapper.eq("id", studentAnswerEntity.getQuestionid());
            studentAnswerEntity.setRightAnswer(fillQuestionService.getOne(wrapper).getAnswer());
        } else if (studentAnswerEntity.getQuestionType() == 2) {
            QueryWrapper<JudgeQuestionEntity> wrapper = new QueryWrapper<>();
            JudgeQuestionEntity judgeQuestionEntity = judgeQuestionService.getOne(wrapper.eq("id", studentAnswerEntity.getQuestionid()));
            studentAnswerEntity.setRightAnswer(judgeQuestionEntity.getAnswer());
        } else if (studentAnswerEntity.getQuestionType() == 3) {
            QueryWrapper<MultiQuestionEntity> wrapper = new QueryWrapper<>();
            MultiQuestionEntity multiQuestionEntity = multiQuestionService.getOne(wrapper.eq("id", studentAnswerEntity.getQuestionid()));
            studentAnswerEntity.setRightAnswer(multiQuestionEntity.getRightanswer());
        } else if (studentAnswerEntity.getQuestionType() == 4) {
            QueryWrapper<SubjectiveQuestionEntity> wrapper = new QueryWrapper<>();
            wrapper.eq("id", studentAnswerEntity.getQuestionid());
            studentAnswerEntity.setRightAnswer(subjectiveQuestionService.getOne(wrapper).getAnswer());
        }
        studentAnswerEntity.setQuestionStatus(0);
        int insert = studentAnswerDao.insert(studentAnswerEntity);
        log.info("insert: {}" , insert);
        return insert;
    }


    @Override
    public List<StudentAnswerEntity> questionsCorrected(Integer userid, Integer examid) {
        QueryWrapper<StudentAnswerEntity> wrapper = new QueryWrapper<>();
        wrapper.eq("userid", userid)
                .eq("examid", examid)
                .eq("question_status", 1);
        return this.list(wrapper);
    }


    @Override
    public StudentAnswerScoreDTO autoJudge(StudentAnswerScoreDTO scores) {
        List<StudentAnswerDTO> answerDTOS = scores.getAnswerDTOS();
        Integer score = scores.getScore();
        for (StudentAnswerDTO dto : answerDTOS) {
            if (dto.getQuestionType() == 1 || dto.getQuestionType() == 4) {
                continue;
            }
            StudentAnswerEntity studentAnswer = studentAnswerDao.selectOne(new QueryWrapper<StudentAnswerEntity>().eq("id" , dto.getStudentAnswerId()));
            //自动改判断题选择题
            if (dto.getQuestionType() == 2 || dto.getQuestionType() == 3) {
                if (dto.getStudentAnswer().equalsIgnoreCase(dto.getRightAnswer())) {
                    studentAnswer.setScore(dto.getQuestionScore());
                    score += dto.getQuestionScore();
                } else {
                    studentAnswer.setScore(0);
                }

                studentAnswer.setQuestionStatus(1);
                this.updateById(studentAnswer);
            }
        }
        scores.setScore(score);
        return scores;
    }

    @Override
    public StudentAnswerScoreDTO handJudge(StudentAnswerScoreDTO scores) {
        List<StudentAnswerDTO> answerDTOS = scores.getAnswerDTOS();
        Integer score = scores.getScore();
        for (StudentAnswerDTO dto : answerDTOS) {
            StudentAnswerEntity studentAnswer = studentAnswerDao.selectOne(new QueryWrapper<StudentAnswerEntity>().eq("id" , dto.getStudentAnswerId()));
            if (dto.getQuestionType() == 2 || dto.getQuestionType() == 3) {
                continue;
            }
            studentAnswer.setScore(dto.getStudentScore());
            score += dto.getStudentScore();
            studentAnswer.setQuestionStatus(1);
            this.updateById(studentAnswer);
        }
        scores.setScore(score);
        return scores;
    }

}