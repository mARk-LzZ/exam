package com.lzz.exam.controller;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lzz.exam.aop.Log;
import com.lzz.exam.entity.MultiQuestionEntity;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import com.lzz.exam.entity.SubjectiveQuestionEntity;
import com.lzz.exam.service.SubjectiveQuestionService;
import com.lzz.exam.utils.PageUtils;
import com.lzz.exam.utils.R;


/**
 * 判断题题库表
 *
 * @author lzz
 * @email 1399508400@qq.com
 * @date 2022-03-21 15:20:42
 */
@RestController
@Api(description ="主观题")
@RequestMapping("exam/subjectivequestion")
public class SubjectiveQuestionController {
    @Autowired
    private SubjectiveQuestionService subjectiveQuestionService;

    /**
     * 列表
     */
    @PostMapping("/list")
    @Log("主管题库查询")
    public Page<SubjectiveQuestionEntity> list(@RequestBody SubjectiveQuestionEntity subjectiveQuestion){
        LambdaQueryWrapper<SubjectiveQuestionEntity> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(subjectiveQuestion.getQuestion()!=null , SubjectiveQuestionEntity::getQuestion , subjectiveQuestion.getQuestion())
                .eq(subjectiveQuestion.getSubject()!=null , SubjectiveQuestionEntity::getSubject , subjectiveQuestion.getSubject())
                .between(subjectiveQuestion.getStartTime()!=null && subjectiveQuestion.getEndTime()!=null , SubjectiveQuestionEntity::getCreateTime , subjectiveQuestion.getStartTime() , subjectiveQuestion.getEndTime())
                .eq(subjectiveQuestion.getId()!=null , SubjectiveQuestionEntity::getId , subjectiveQuestion.getId());
        return subjectiveQuestionService.page(new Page<>(subjectiveQuestion.getPage() , subjectiveQuestion.getLimit()));
    }

    /**
     * 保存
     */
    @PostMapping("/save")
    @Log("主管题库保存")
    @PreAuthorize("hasAnyRole('ROLE_TEACHER' , 'ROLE_ADMIN')")
    public R save(@RequestBody SubjectiveQuestionEntity subjectiveQuestionEntity){
        if (subjectiveQuestionEntity.getQuestion()!=null &&
                subjectiveQuestionEntity.getAnswer()!= null &&
                subjectiveQuestionEntity.getLevel() != null &&
                subjectiveQuestionEntity.getSubject() != null &&
                subjectiveQuestionEntity.getScore() !=null){
            subjectiveQuestionService.save(subjectiveQuestionEntity);
            List<SubjectiveQuestionEntity> subjectiveQuestionEntities=subjectiveQuestionService.list(new QueryWrapper<SubjectiveQuestionEntity>()
                    .eq("question", subjectiveQuestionEntity.getQuestion())
                    .eq("answer", subjectiveQuestionEntity.getAnswer()));


            return R.ok().put("new multiQuestions" , subjectiveQuestionEntities);
        }
        return R.error("题库信息不全");
    }

    /**
     * 修改
     */
    @PostMapping("/update")
    @Log("主管题库更新")
    @PreAuthorize("hasAnyRole('ROLE_TEACHER' , 'ROLE_ADMIN')")
    public R update(@RequestBody SubjectiveQuestionEntity subjectiveQuestion){
		subjectiveQuestionService.updateById(subjectiveQuestion);

        return R.ok();
    }

    /**
     * 删除
     */
    @PostMapping("/delete")
    @Log("主管题库删除")
    @PreAuthorize("hasAnyRole('ROLE_TEACHER' , 'ROLE_ADMIN')")
    public R delete(@RequestBody Integer[] ids){
		subjectiveQuestionService.removeByIds(Arrays.asList(ids));

        return R.ok();
    }

}
