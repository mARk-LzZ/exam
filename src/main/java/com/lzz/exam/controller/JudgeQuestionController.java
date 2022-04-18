package com.lzz.exam.controller;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lzz.exam.aop.Log;
import com.lzz.exam.entity.MultiQuestionEntity;
import com.lzz.exam.utils.PageUtils;
import com.lzz.exam.utils.R;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import com.lzz.exam.entity.JudgeQuestionEntity;
import com.lzz.exam.service.JudgeQuestionService;




/**
 * 判断题题库表
 *
 * @author lzz
 * @email 1399508400@qq.com
 * @date 2022-03-21 15:20:42
 */
@RestController
@Api(description ="判断题")
@RequestMapping("exam/judgequestion")
public class JudgeQuestionController {
    @Autowired
    private JudgeQuestionService judgeQuestionService;

    /**
     * 列表
     */
    @PostMapping("/list")
    @Log(value = "判断题库查询")
    public Page<JudgeQuestionEntity> list(@RequestBody JudgeQuestionEntity judgeQuestionEntity){
        QueryWrapper<MultiQuestionEntity> wrapper = new QueryWrapper<>();
        wrapper.eq(judgeQuestionEntity.getQuestion()!=null , "question" , judgeQuestionEntity.getQuestion())
                .eq(judgeQuestionEntity.getSubject()!=null , "subject" , judgeQuestionEntity.getSubject())
                .between(judgeQuestionEntity.getStartTime()!=null && judgeQuestionEntity.getEndTime()!=null , "create_time" , judgeQuestionEntity.getStartTime() , judgeQuestionEntity.getEndTime())
                .eq(judgeQuestionEntity.getId()!=null ,"id" , judgeQuestionEntity.getId());
        return judgeQuestionService.page(new Page<>(judgeQuestionEntity.getPage() , judgeQuestionEntity.getLimit()));
    }

    /**
     * 保存
     */
    @PostMapping("/save")
    @PreAuthorize("hasAnyRole('ROLE_TEACHER' , 'ROLE_ADMIN')")
    @Log(value = "判断题库保存")
    public R save(@RequestBody JudgeQuestionEntity judgeQuestionEntity){
        if (judgeQuestionEntity.getQuestion()!=null &&
                judgeQuestionEntity.getAnswer()!= null &&
                judgeQuestionEntity.getLevel() != null &&
                judgeQuestionEntity.getSubject() != null &&
                judgeQuestionEntity.getScore() !=null){
            judgeQuestionService.save(judgeQuestionEntity);
            List<JudgeQuestionEntity> judgeQuestionEntities=judgeQuestionService.list(new QueryWrapper<JudgeQuestionEntity>()
                    .eq("question", judgeQuestionEntity.getQuestion())
                    .eq("answer", judgeQuestionEntity.getAnswer()));


            return R.ok().put("new JudgeQuestions" , judgeQuestionEntities);
        }
        return R.error("试题信息不完整");
    }

    /**
     * 修改
     */
    @PostMapping("/update")
    @Log(value = "判断题库修改")
    @PreAuthorize("hasAnyRole('ROLE_TEACHER' , 'ROLE_ADMIN')")
    public R update(@RequestBody JudgeQuestionEntity judgeQuestion){
		judgeQuestionService.updateById(judgeQuestion);

        return R.ok();
    }

    /**
     * 删除
     */
    @PostMapping("/delete")
    @Log(value = "判断题库删除")
    @PreAuthorize("hasAnyRole('ROLE_TEACHER' , 'ROLE_ADMIN')")
    public R delete(@RequestBody Integer[] ids){
		judgeQuestionService.removeByIds(Arrays.asList(ids));

        return R.ok();
    }

}
