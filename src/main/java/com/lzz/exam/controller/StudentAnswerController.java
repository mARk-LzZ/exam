package com.lzz.exam.controller;

import java.io.UnsupportedEncodingException;
import java.util.*;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.lzz.exam.aop.Log;
import com.lzz.exam.dto.StudentAnswerDTO;
import com.lzz.exam.dto.StudentAnswerScoreDTO;
import com.lzz.exam.entity.*;
import com.lzz.exam.service.*;
import com.lzz.exam.utils.JWTUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import com.lzz.exam.utils.PageUtils;
import com.lzz.exam.utils.R;
import springfox.documentation.annotations.ApiIgnore;

import javax.servlet.http.HttpServletRequest;


/**
 * @author lzz
 * @email 1399508400@qq.com
 * @date 2022-03-21 15:20:42
 */
@RestController
@RequestMapping("exam/studentanswer")
@Api(description = "学生答题内容")
@Slf4j
public class StudentAnswerController {
    @Autowired
    private StudentAnswerService studentAnswerService;
    @Autowired
    private ClazzService clazzService;
    @Autowired
    private ExamManageService examManageService;
    @Autowired
    private PaperService paperService;
    @Autowired
    private ClassToStudentService classToStudentService;
    @Autowired
    private FillQuestionService fillQuestionService;
    @Autowired
    private MultiQuestionService multiQuestionService;
    @Autowired
    private JudgeQuestionService judgeQuestionService;
    @Autowired
    private SubjectiveQuestionService subjectiveQuestionService;

    private final Integer KEY = 99999;

    /**
     * 列表
     */
    @PostMapping("/list")
    @Log("学生答案查询")
    @PreAuthorize("hasAnyRole('ROLE_TEACHER','ROLE_ADMIN')")
    public R list(@RequestParam Map<String, Object> params) {
        PageUtils page = studentAnswerService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @PostMapping("/info/{id}")
    @PreAuthorize("hasAnyRole('ROLE_TEACHER','ROLE_ADMIN')")
    public R info(@PathVariable("id") Integer id) {
        StudentAnswerEntity studentAnswer = studentAnswerService.getById(id);

        return R.ok().put("studentAnswer", studentAnswer);
    }

    /**
     * 保存
     */
    @PostMapping("/save")
    @Log("学生答案保存")
    public R save(@RequestBody StudentAnswerEntity studentAnswer) {
        studentAnswerService.save(studentAnswer);

        return R.ok();
    }

    /**
     * 修改
     */
    @PostMapping("/update")
    @Log("学生答案修改")
    public R update(@RequestBody StudentAnswerEntity studentAnswer) {
        studentAnswerService.updateById(studentAnswer);

        return R.ok();
    }

    /**
     * 删除
     */
    @PostMapping("/delete")
    @Log("学生答案删除")
    public R delete(@RequestBody Integer[] ids) {
        studentAnswerService.removeByIds(Arrays.asList(ids));

        return R.ok();
    }

    /*
     * 試題提交
     * */
    @PostMapping("/questionsubmit")
    @ApiOperation("试题提交")
    @Log("提交试卷")
    @Transactional
    @ApiImplicitParams({@ApiImplicitParam(name = "examid", value = "考试id"),
            @ApiImplicitParam(name = "questionType", value = "1填空 2判断 3选择 4主观"), @ApiImplicitParam(name = "questionid", value = "题目id"),
            @ApiImplicitParam(name = "userAnswer", value = "用户答案")})
    public R questionSubmit(@ApiIgnore @RequestBody String text, @ApiIgnore HttpServletRequest request) throws UnsupportedEncodingException {
        List<StudentAnswerEntity> list = JSONObject.parseArray(text, StudentAnswerEntity.class);
        String token = request.getHeader("token");
        Map<String, String> info = JWTUtils.getInfo(token);
        String id = info.get("id");
        for (StudentAnswerEntity studentAnswerEntity : list) {
            if (studentAnswerEntity.getQuestionType() != null &&
                    studentAnswerEntity.getExamid() != null &&
                    studentAnswerEntity.getQuestionid() != null &&
                    studentAnswerEntity.getUserAnswer() != null) {
                studentAnswerEntity.setUserid(Integer.valueOf(id));
                studentAnswerService.questionSubmit(studentAnswerEntity);
            } else {
                return R.error("试题提交信息不完整");
            }
        }
        return R.ok();

    }

    @PostMapping("/handjudge")
    @ApiOperation("手改填空和主观(先请求教师查看未批改试卷)")
    @Log("手动改卷")
    public R handJudge(@RequestBody StudentAnswerScoreDTO scores) {
        scores =  studentAnswerService.handJudge(scores);
        return R.ok().put("score" , scores);
    }

    @PostMapping("/autojudge")
    @ApiOperation("自动改选择和判断（先请求教师查看未批改试卷）")
    @Log("自动改卷")
    public R autoJudge(@RequestBody StudentAnswerScoreDTO scores) {
        scores = studentAnswerService.autoJudge(scores);
        return R.ok().put("score" , scores);
    }

    @PostMapping("questioncorrected")
    @ApiOperation("学生查看已批改试卷")
    @Log("查看已批改试卷（学生）")
    @ApiImplicitParams({@ApiImplicitParam(name = "paperid", value = "试卷id")})
    public R questionsCorrected(@ApiIgnore @RequestBody StudentAnswerEntity studentAnswerEntity, @ApiIgnore HttpServletRequest request) throws UnsupportedEncodingException {
        String token = request.getHeader("token");
        Map<String, String> info = JWTUtils.getInfo(token);
        String id = info.get("id");
        Integer examid = studentAnswerEntity.getExamid();
        return R.ok().put("paper", studentAnswerService.questionsCorrected(Integer.valueOf(id), examid));
    }


    @PostMapping("allwaitingcorrect")
    @ApiOperation("教师查看自己班级下未批改试题")
    @Log("查看未批改试卷（教师）")
    @ApiImplicitParams({@ApiImplicitParam(name = "paperid", value = "试卷id")})
    @PreAuthorize("hasAnyRole('ROLE_TEACHER','ROLE_ADMIN')")
    public R allWaitingCorrect(@ApiIgnore HttpServletRequest request) throws UnsupportedEncodingException {
        String token = request.getHeader("token");
        Map<String, String> info = JWTUtils.getInfo(token);
        //获取教师id
        String userid = info.get("id");
        QueryWrapper<ClazzEntity> wrapper = new QueryWrapper<>();
        wrapper.eq("teacherid", userid);
        //检出老师对应班级
        List<ClazzEntity> list = clazzService.list(wrapper);
        //存储学生考试内容
        List<Map<Integer, List<StudentAnswerDTO>>> stuAns = new LinkedList<>();
        //遍历所有老师对应班级
        list.forEach(clazz -> {
            //查询所有对应考试
            log.info("class: {}" , clazz);
            QueryWrapper<ExamManageEntity> wrapper1 = new QueryWrapper<>();
            wrapper1.eq("classid", clazz.getId());
            List<ExamManageEntity> list2 = examManageService.list(wrapper1);
            //遍历班级下的所有考试
            list2.forEach(examManage -> {
                //获取考试名称
                String source = examManage.getSource();
                log.info("source: {}" , source);
                //查询所有对应考生
                QueryWrapper<ClassToStudentEntity> classToStudentEntityQueryWrapper = new QueryWrapper<>();
                classToStudentEntityQueryWrapper.eq("classid", clazz.getId());
                List<ClassToStudentEntity> list1 = classToStudentService.list(classToStudentEntityQueryWrapper);
                Map<Integer, List<StudentAnswerDTO>> map1 = new HashMap<>();
                //遍历所有考生
                for (ClassToStudentEntity cts : list1) {
                    //查询所有对应答案 按学生 + 考试储存
                    log.info("cts: {}" , cts.getStudentid());
                    QueryWrapper<StudentAnswerEntity> studentAnswerEntityQueryWrapper = new QueryWrapper<>();
                    studentAnswerEntityQueryWrapper.eq("examid", examManage.getId())
                            .eq("userid", cts.getStudentid())
                            .eq("question_status",0);
                    List<StudentAnswerDTO> answerDTOS = new LinkedList<>();
                    List<StudentAnswerEntity> list3 = studentAnswerService.list(studentAnswerEntityQueryWrapper);
                    //考生没有未批改试卷跳过
                    if (list3 == null || list3.isEmpty()) {
                        continue;
                    }
                    //遍历所有考生答案 存储DTO
                    for (StudentAnswerEntity studentAnswerEntity : list3) {
                        log.info("student: {}" , studentAnswerEntity);
                        StudentAnswerDTO dto = new StudentAnswerDTO();
                        dto.setStudentAnswer(studentAnswerEntity.getUserAnswer());
                        dto.setRightAnswer(studentAnswerEntity.getRightAnswer());
                        switch (studentAnswerEntity.getQuestionType()){
                            case 1 : {
                                QueryWrapper<FillQuestionEntity> wrapper2 = new QueryWrapper<>();
                                wrapper2.eq("id" , studentAnswerEntity.getQuestionid());
                                FillQuestionEntity one = fillQuestionService.getOne(wrapper2);
                                if (one != null){
                                    dto.setQuestionName(one.getQuestion());
                                    dto.setQuestionScore(one.getScore());
                                    dto.setQuestionType(studentAnswerEntity.getQuestionType());
                                    dto.setSource(source);
                                }
                                break;
                            }
                            case 2:{
                                QueryWrapper<JudgeQuestionEntity> wrapper2 = new QueryWrapper<>();
                                wrapper2.eq("id" , studentAnswerEntity.getQuestionid());
                                JudgeQuestionEntity one = judgeQuestionService.getOne(wrapper2);
                                if (one != null){
                                    dto.setQuestionName(one.getQuestion());
                                    dto.setQuestionScore(one.getScore());
                                    dto.setQuestionType(studentAnswerEntity.getQuestionType());
                                    dto.setSource(source);
                                }
                                break;
                            }
                            case 3:{
                                QueryWrapper<MultiQuestionEntity> wrapper2 = new QueryWrapper<>();
                                wrapper2.eq("id" , studentAnswerEntity.getQuestionid());
                                MultiQuestionEntity one = multiQuestionService.getOne(wrapper2);
                                if (one != null){
                                    dto.setQuestionName(one.getQuestion());
                                    dto.setQuestionScore(one.getScore());
                                    dto.setQuestionType(studentAnswerEntity.getQuestionType());
                                    dto.setSource(source);
                                }
                                break;
                            }
                            case 4:{
                                QueryWrapper<SubjectiveQuestionEntity> wrapper2 = new QueryWrapper<>();
                                wrapper2.eq("id" , studentAnswerEntity.getQuestionid());
                                SubjectiveQuestionEntity one = subjectiveQuestionService.getOne(wrapper2);
                                if (one != null){
                                    dto.setQuestionName(one.getQuestion());
                                    dto.setQuestionScore(one.getScore());
                                    dto.setQuestionType(studentAnswerEntity.getQuestionType());
                                    dto.setSource(source);
                                }
                                break;
                            }
                        }
                        dto.setStudentAnswerId(studentAnswerEntity.getId());
                        answerDTOS.add(dto);
                    }
                    map1.put(cts.getStudentid() * KEY + examManage.getId(), answerDTOS);
                }
                stuAns.add(map1);
                log.info("map: {}" , stuAns);
            });
        });

        return R.ok().put("paper", stuAns);
    }
}
