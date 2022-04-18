package com.lzz.exam.controller;


import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lzz.exam.aop.Log;
import com.lzz.exam.entity.*;
import com.lzz.exam.service.*;
import com.lzz.exam.utils.R;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import java.util.*;
import java.util.stream.Collectors;

@RestController
@Api(description ="试卷管理")
@Slf4j
@RequestMapping("exam/paper")
public class PaperController {

    @Autowired
    private PaperService paperService;
    @Autowired
    private PaperManageService paperManageService;
    @Autowired
    private FillQuestionService fillQuestionService;
    @Autowired
    private JudgeQuestionService judgeQuestionService;
    @Autowired
    private MultiQuestionService multiQuestionService;
    @Autowired
    private SubjectiveQuestionService subjectiveQuestionService;

    @PostMapping("/list")
    @Log(value = "试卷查询")
    public Page<PaperEntity> list(@RequestBody PaperEntity paper){
        QueryWrapper<PaperEntity> wrapper = new QueryWrapper<>();
        wrapper.eq(paper.getId()!= null , "id" , paper.getId())
                .eq(paper.getName() != null , "name" ,  paper.getName())
                .eq(paper.getMajor()!=null ,"major" ,paper.getMajor())
                .between(paper.getStartTime()!=null && paper.getEndTime()!=null , "create_time" , paper.getStartTime() , paper.getEndTime());
        return paperService.page(new Page<>(paper.getPage(), paper.getLimit()), wrapper);
    }

    @PostMapping("/save")
    @Log(value = "试卷添加")
    @PreAuthorize("hasAnyRole('ROLE_TEACHER','ROLE_ADMIN')")
    public R save(@RequestBody PaperEntity paper){
        return R.ok().put("success:" , paperService.save(paper));
    }

    @PostMapping("/update")
    @Log(value = "试卷修改")
    @PreAuthorize("hasAnyRole('ROLE_TEACHER','ROLE_ADMIN')")
    public R update(@RequestBody PaperEntity paper){
        return R.ok().put("success: " , paperService.updateById(paper));
    }

    /**
     * 删除
     */
    @PostMapping("/delete")
    @Log(value = "试卷删除")
    @ApiOperation("删除试卷(同时删除对应所有题目)")
    @PreAuthorize("hasAnyRole('ROLE_TEACHER','ROLE_ADMIN')")
    public R delete(@RequestBody Integer[] paperids){
        // 删除试卷
        paperService.removeByIds(Arrays.asList(paperids));
        //删除试题
        for (int id : paperids) {
            QueryWrapper<PaperManageEntity> wrapper = new QueryWrapper<>();
            wrapper.eq("paperId" , id);
            paperManageService.remove(wrapper);
        }
        return R.ok();
    }

    @PostMapping("deleteQues")
    @ApiOperation("编写试卷的时候 删除试题")
    @Log(value = "试题删除")
    @PreAuthorize("hasAnyRole('ROLE_TEACHER','ROLE_ADMIN')")
    public R deleteQuestion(@RequestBody String text){
        List<PaperManageEntity> list = JSONObject.parseArray(text , PaperManageEntity.class);
        list.forEach(question ->{
            QueryWrapper<PaperManageEntity> wrapper = new QueryWrapper<>();
            wrapper.eq("paperId" , question.getPaperid())
                    .eq("questionId" , question.getQuestionid())
                    .eq("questionType" , question.getQuestiontype());
            paperManageService.remove(wrapper);
        });
        return R.ok();
    }

    @PostMapping("/addQues")
    @ApiOperation("为一张试卷添加试题")
    @Log(value = "为试卷添加试题")
    @PreAuthorize("hasAnyRole('ROLE_TEACHER','ROLE_ADMIN')")
    public R addQuestion(@RequestBody String text){
        List<PaperManageEntity> paper = JSONArray.parseArray(text , PaperManageEntity.class);
        for (PaperManageEntity question: paper) {
            if (question.getQuestiontype() == null){
                return R.error("请指定题目类型");
            }else if (question.getQuestionid() == null){
                return R.error("请指定题目id");
            }else if (question.getPaperid() == 0){
                return R.error("请指定试卷id");
            }
            paperManageService.save(question);
        }
        return R.ok();
    }

    /*
     * 查看试卷
     * */

    @ApiOperation("查看试卷")
    @PostMapping("/paperselect")
    @ApiImplicitParam(name="paperid" , value="试卷id")
    public R paperSelect(@ApiIgnore @RequestBody PaperManageEntity paperManageEntity) {
        Map<String, List<Object>> map=paperManageService.paperSelect(paperManageEntity.getPaperid());
            return R.ok().put("paper", map);
    }

    /**
     * 自动出卷
     */
    @ApiOperation("自动出卷")
    @PostMapping("/autoGetPaper")
    @Log("自动出卷")
    public R autoGetPaper(@RequestBody PaperEntity paper){
        //先创建试卷
        paperService.save(paper);
        //获取paper的id
        Integer paperId = paper.getId();
        int totalScore = 0;
        Random random = new Random();
        //添加填空题 固定10道
        List<Integer> fillids = new LinkedList<>();
        List<Integer> fillcollect = fillQuestionService.list().stream()
                .map(FillQuestionEntity::getId)
                .collect(Collectors.toList());
        for(int i = 0; i < 10; i++){
            int i1 = random.nextInt(fillcollect.size());
            Integer randomid = fillcollect.get(i1);
            if(!fillids.contains(randomid)){
                fillids.add(randomid);
                paperManageService.save(new PaperManageEntity(paperId , 1 , randomid));
                totalScore += fillQuestionService.getById(randomid).getScore();
            }
        }
        //添加判断题 固定5道
        List<Integer> judgeids = new LinkedList<>();
        List<Integer> judgecollect = judgeQuestionService.list().stream()
                .map(JudgeQuestionEntity::getId)
                .collect(Collectors.toList());
        for(int i = 0; i < 5; i++){
            int i1 = random.nextInt(judgecollect.size());
            Integer randomid = judgecollect.get(i1);
            if(!judgeids.contains(randomid)){
                judgeids.add(randomid);
                paperManageService.save(new PaperManageEntity(paperId , 2 , randomid));
                totalScore += judgeQuestionService.getById(randomid).getScore();
            }
        }
        //添加选择题 固定10道
        List<Integer> selids = new LinkedList<>();
        List<Integer> selcollect = multiQuestionService.list().stream()
                .map(MultiQuestionEntity::getId)
                .collect(Collectors.toList());
        for(int i = 0; i < 10; i++){
            int i1 = random.nextInt(selcollect.size());
            Integer randomid = selcollect.get(i1);
            if(!selids.contains(randomid)){
                selids.add(randomid);
                paperManageService.save(new PaperManageEntity(paperId , 3 , randomid));
                totalScore += multiQuestionService.getById(randomid).getScore();
            }
        }
        //添加主观题 固定3道
        List<Integer> subjectiveids = new LinkedList<>();
        List<Integer> subjectivecollect = subjectiveQuestionService.list().stream()
                .map(SubjectiveQuestionEntity::getId)
                .collect(Collectors.toList());
        for(int i = 0; i < 3; i++){
            int i1 = random.nextInt(subjectivecollect.size());
            Integer randomid = subjectivecollect.get(i1);
            if(!subjectiveids.contains(randomid)){
                subjectiveids.add(randomid);
                paperManageService.save(new PaperManageEntity(paperId , 4 , randomid));
                totalScore += subjectiveQuestionService.getById(randomid).getScore();
            }
        }
        //储存总分
        paper.setTotalScore(totalScore);
        paperService.updateById(paper);
        return R.ok().put("id" , paper.getId());
    }
}
