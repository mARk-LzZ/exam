package com.lzz.exam.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.lzz.exam.dto.StudentAnswerDTO;
import com.lzz.exam.dto.StudentAnswerScoreDTO;
import com.lzz.exam.utils.PageUtils;

import com.lzz.exam.entity.StudentAnswerEntity;
import com.lzz.exam.utils.R;

import java.util.List;
import java.util.Map;

/**
 * 
 *
 * @author lzz
 * @email 1399508400@qq.com
 * @date 2022-03-21 15:20:42
 */
public interface StudentAnswerService extends IService<StudentAnswerEntity> {

    PageUtils queryPage(Map<String, Object> params);

    int questionSubmit(StudentAnswerEntity studentAnswerEntity);

    StudentAnswerScoreDTO autoJudge(StudentAnswerScoreDTO scores);

    //学生查看学生已批改的试题
    List<StudentAnswerEntity> questionsCorrected(Integer studentId, Integer paperId);


    //手动批改填空和主观题
    StudentAnswerScoreDTO handJudge(StudentAnswerScoreDTO scores);


}

