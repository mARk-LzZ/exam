package com.lzz.exam.controller;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lzz.exam.aop.Log;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.parameters.P;
import org.springframework.web.bind.annotation.*;

import com.lzz.exam.entity.MultiQuestionEntity;
import com.lzz.exam.service.MultiQuestionService;
import com.lzz.exam.utils.PageUtils;
import com.lzz.exam.utils.R;



/**
 * 选择题题库表
 *
 * @author lzz
 * @email 1399508400@qq.com
 * @date 2022-03-21 15:20:42
 */
@RestController
@Api(description ="选择题")
@RequestMapping("exam/multiquestion")
public class MultiQuestionController {
    @Autowired
    private MultiQuestionService multiQuestionService;

    /**
     * 列表
     */
    @PostMapping("/list")
    @Log("选择题库查询")
    public Page<MultiQuestionEntity> list(@RequestBody MultiQuestionEntity multiQuestionEntity){
        LambdaQueryWrapper<MultiQuestionEntity> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(multiQuestionEntity.getQuestion()!=null , MultiQuestionEntity::getQuestion , multiQuestionEntity.getQuestion())
                .eq(multiQuestionEntity.getSubject()!=null , MultiQuestionEntity::getSubject , multiQuestionEntity.getSubject())
                .between(multiQuestionEntity.getStartTime()!=null && multiQuestionEntity.getEndTime()!=null , MultiQuestionEntity::getCreateTime , multiQuestionEntity.getStartTime() , multiQuestionEntity.getEndTime())
                .eq(multiQuestionEntity.getId()!=null , MultiQuestionEntity::getId , multiQuestionEntity.getId());
        return multiQuestionService.page(new Page<>(multiQuestionEntity.getPage() , multiQuestionEntity.getLimit()));
    }

    /**
     * 保存
     */
    @PostMapping("/save")
    @Log("选择题库保存")
    @PreAuthorize("hasAnyRole('ROLE_TEACHER' , 'ROLE_ADMIN')")
    public R save(@RequestBody MultiQuestionEntity multiQuestionEntity){
        if (multiQuestionEntity.getQuestion()!=null &&
                multiQuestionEntity.getAnswera()!= null &&
                multiQuestionEntity.getAnswerb()!= null &&
                multiQuestionEntity.getAnswerc()!= null &&
                multiQuestionEntity.getAnswerd()!= null &&
                multiQuestionEntity.getRightanswer()!= null &&
                multiQuestionEntity.getLevel() != null &&
                multiQuestionEntity.getSubject() != null &&
                multiQuestionEntity.getScore() !=null){
            multiQuestionService.save(multiQuestionEntity);
            List<MultiQuestionEntity> multiQuestionEntities=multiQuestionService.list(new QueryWrapper<MultiQuestionEntity>()
                    .eq("question", multiQuestionEntity.getQuestion())
                    .eq("rightAnswer", multiQuestionEntity.getRightanswer()));


            return R.ok().put("new multiQuestions" , multiQuestionEntities);
        }

        return R.error("试题信息不完整");
    }

    /**
     * 修改
     */
    @PostMapping("/update")
    @Log("选择题库修改")
    @PreAuthorize("hasAnyRole('ROLE_TEACHER' , 'ROLE_ADMIN')")
    public R update(@RequestBody MultiQuestionEntity multiQuestion){
		multiQuestionService.updateById(multiQuestion);

        return R.ok();
    }

    /**
     * 删除
     */
    @PostMapping("/delete")
    @Log("选择题库删除")
    @PreAuthorize("hasAnyRole('ROLE_TEACHER' , 'ROLE_ADMIN')")
    public R delete(@RequestBody Integer[] ids){
		multiQuestionService.removeByIds(Arrays.asList(ids));

        return R.ok();
    }

}
