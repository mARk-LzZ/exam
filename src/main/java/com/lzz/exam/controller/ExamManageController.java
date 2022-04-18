package com.lzz.exam.controller;

import java.io.UnsupportedEncodingException;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.plugins.pagination.PageDto;
import com.lzz.exam.aop.Log;
import com.lzz.exam.entity.*;
import com.lzz.exam.service.*;
import com.lzz.exam.utils.JWTUtils;
import com.lzz.exam.utils.R;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import com.lzz.exam.utils.PageUtils;
import springfox.documentation.annotations.ApiIgnore;

import javax.servlet.http.HttpServletRequest;


/**
 * 考试管理表
 *
 * @author lzz
 * @email 1399508400@qq.com
 * @date 2022-03-21 15:20:42
 */
@RestController
@RequestMapping("exam/exammanage")
@Api(description = "考试管理")
@Slf4j
public class ExamManageController {
    @Autowired
    private ExamManageService examManageService;
    @Autowired
    private PaperService paperService;
    @Autowired
    private ClassToStudentService classToStudentService;
    @Autowired
    private ExamToStudentService examToStudentService;
    @Autowired
    private StudentAnswerService studentAnswerService;
    @Autowired
    private ClazzService clazzService;
    /**
     * 信息
     */
    @PostMapping("/info")
    @Log("考试查询")
    public R info(@RequestBody ExamManageEntity examManage) {
        LambdaQueryWrapper<ExamManageEntity> wrapper = new LambdaQueryWrapper<>();
        wrapper.between(examManage.getStartTime() != null && examManage.getEndTime() != null, ExamManageEntity::getCreateTime, examManage.getStartTime(), examManage.getEndTime())
                .eq(examManage.getStatus() != null, ExamManageEntity::getStatus, examManage.getStatus())
                .eq(examManage.getPaperid() != null, ExamManageEntity::getPaperid, examManage.getPaperid());
        PageDto<ExamManageEntity> res = examManageService.page(new PageDto<>(examManage.getPage(), examManage.getLimit()), wrapper);
        return R.ok().put("examManage", res);
    }

    /**
     * 保存
     */
    @PostMapping("/save")
    @Log("考试保存")
    @PreAuthorize("hasAnyRole('ROLE_TEACHER' , 'ROLE_ADMIN')")
    public R save(@RequestBody ExamManageEntity examManage) {
        Integer[] ids = examManage.getIds();
        if (examManage.getSource() != null &&
                examManage.getExamdate() != null &&
                examManage.getPaperid() != null) {
            Integer paperid = examManage.getPaperid();
            PaperEntity paper = paperService.getById(paperid);
            examManage.setTotalscore(paper.getTotalScore());
            examManage.setTotaltime(paper.getTotalTime());
        }else{
            return R.error("储存失败 信息不完整");
        }
        for (int classid : ids) {
            examManage.setClassid(classid);
            examManageService.save(examManage);
        }

        return R.ok();
    }


    /**
     * 修改
     */
    @PostMapping("/update")
    @Log("考试修改")
    @PreAuthorize("hasAnyRole('ROLE_TEACHER' , 'ROLE_ADMIN')")
    public R update(@RequestBody ExamManageEntity examManage) {
        examManageService.updateById(examManage);

        return R.ok();
    }

    /**
     * 删除
     */
    @PostMapping("/delete")
    @Log("考试删除")
    @PreAuthorize("hasAnyRole('ROLE_TEACHER' , 'ROLE_ADMIN')")
    public R delete(@RequestBody Integer[] ids) {
        examManageService.removeByIds(Arrays.asList(ids));

        return R.ok();
    }

    @GetMapping("/getmyexam")
    @ApiOperation("学生获取自己的考试")
    @Log("学生对应考试查询")
    public R getMyAnnouncement(@ApiIgnore HttpServletRequest request , @RequestParam(value = "page")Integer page , @RequestParam("limit") Integer limit) throws UnsupportedEncodingException {
        String token = request.getHeader("token");
        Map<String, String> info = JWTUtils.getInfo(token);
        String studentid = info.get("id");
        QueryWrapper<ClassToStudentEntity> wrapper1 = new QueryWrapper<>();
        wrapper1.eq("studentid" ,Integer.parseInt(studentid));
        log.info(studentid);
        //存储所有考试
        List<ExamManageEntity> list = new LinkedList<>();
        for (ClassToStudentEntity student : classToStudentService.list(wrapper1)) {
            Integer classid = student.getClassid();
            log.info("class: {}" , classid);
            QueryWrapper<ExamManageEntity> wrapper = new QueryWrapper<>();
            wrapper.eq("classid", classid).orderByDesc("id");
            List<ExamManageEntity> list1 = examManageService.list(wrapper);
            list1.forEach(examManageEntity -> {
                QueryWrapper<ClazzEntity> wrapper2 = new QueryWrapper<>();
                wrapper2.eq("id" , classid);
                examManageEntity.setClassName(clazzService.getOne(wrapper2).getClazz());
            });
            list.addAll(list1);
        }
        Page<?> pages = PageUtils.getPages(page, limit, list);
        return R.ok().put("exam" , pages);
    }

    @PostMapping("startexam")
    @ApiOperation("开始考试")
    @Log(value = "考试开始")
    public R startExam(@RequestBody ExamToStudentEntity exam){
        QueryWrapper<ExamToStudentEntity> wrapper = new QueryWrapper<>();
        wrapper.eq("userid" , exam.getUserid())
                .eq("examid", exam.getExamid());
        ExamToStudentEntity update = examToStudentService.getOne(wrapper);
        if (update == null){
            return R.ok().put("success",examToStudentService.save(exam));
        }
        return R.error("请勿重复开始");
    }

    @PostMapping("finishexam")
    @ApiOperation("结束考试")
    @Log(value = "考试结束")
    public R finishExam(@RequestBody ExamToStudentEntity exam){
        QueryWrapper<ExamToStudentEntity> wrapper = new QueryWrapper<>();
        wrapper.eq("userid" , exam.getUserid())
                .eq("examid", exam.getExamid());
        ExamToStudentEntity update = examToStudentService.getOne(wrapper);
        if (update != null){
            if (update.getStatus() == 1){
                UpdateWrapper<ExamToStudentEntity> updateWrapper = new UpdateWrapper<>();
                updateWrapper.eq("userid" , exam.getUserid())
                        .eq("examid", exam.getExamid())
                        .set("status",2);
                return R.ok().put("succuss" , examToStudentService.update(updateWrapper));
            }
        }
      return R.error("提交失败");
    }

}
