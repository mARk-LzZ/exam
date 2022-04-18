package com.lzz.exam.controller;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lzz.exam.aop.Log;
import com.lzz.exam.entity.MultiQuestionEntity;
import com.lzz.exam.exception.RRException;
import com.lzz.exam.utils.PageUtils;
import com.lzz.exam.utils.R;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import com.lzz.exam.entity.FillQuestionEntity;
import com.lzz.exam.service.FillQuestionService;




/**
 * 填空题题库
 *
 * @author lzz
 * @email 1399508400@qq.com
 * @date 2022-03-21 15:20:42
 */
@RestController
@Api(description ="填空题")
@RequestMapping("exam/fillquestion")
public class FillQuestionController {
    @Autowired
    private FillQuestionService fillQuestionService;

    /**
     * 列表
     */
    @PostMapping("/list")
    @Log("填空题库查询")
    public Page<FillQuestionEntity> list(@RequestBody FillQuestionEntity fillQuestionEntity){
        LambdaQueryWrapper<FillQuestionEntity> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(fillQuestionEntity.getQuestion()!=null , FillQuestionEntity::getQuestion , fillQuestionEntity.getQuestion())
                .eq(fillQuestionEntity.getSubject()!=null , FillQuestionEntity::getSubject , fillQuestionEntity.getSubject())
                .between(fillQuestionEntity.getStartTime()!=null && fillQuestionEntity.getEndTime()!=null , FillQuestionEntity::getCreateTime , fillQuestionEntity.getStartTime() , fillQuestionEntity.getEndTime())
                .eq(fillQuestionEntity.getId()!=null , FillQuestionEntity::getId , fillQuestionEntity.getId());
        return fillQuestionService.page(new Page<>(fillQuestionEntity.getPage() , fillQuestionEntity.getLimit()));
    }

    /**
     * 保存
     */
    @PostMapping("/save")
    @PreAuthorize("hasAnyRole('ROLE_TEACHER' , 'ROLE_ADMIN')")
    @Log("填空题库保存")
    public R save(@RequestBody FillQuestionEntity fillQuestion){
        if (fillQuestion.getQuestion()!=null &&
                fillQuestion.getAnswer()!= null &&
                fillQuestion.getLevel() != null &&
                fillQuestion.getSubject() != null &&
                fillQuestion.getScore() !=null){
            fillQuestionService.save(fillQuestion);
            List<FillQuestionEntity> fillQuestionEntities=fillQuestionService.list(new QueryWrapper<FillQuestionEntity>()
                    .eq("question", fillQuestion.getQuestion())
                    .eq("answer", fillQuestion.getAnswer()));


            return R.ok().put("new fileeQuestions" , fillQuestionEntities);
        }
        return R.error("试题信息不完整");
    }

    /**
     * 修改
     */
    @PostMapping("/update")
    @Log("填空题库修改")

    @PreAuthorize("hasAnyRole('ROLE_TEACHER' , 'ROLE_ADMIN')")
    public R update(@RequestBody FillQuestionEntity fillQuestion){
		fillQuestionService.updateById(fillQuestion);

        return R.ok();
    }

    /**
     * 删除
     */
    @PostMapping("/delete")
    @Log("填空题库删除")
    @PreAuthorize("hasAnyRole('ROLE_TEACHER' , 'ROLE_ADMIN')")
    public R delete(@RequestBody Integer[] ids){
		fillQuestionService.removeByIds(Arrays.asList(ids));

        return R.ok();
    }

}
