package com.lzz.exam.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lzz.exam.aop.Log;
import com.lzz.exam.entity.ClassToStudentEntity;
import com.lzz.exam.entity.ClazzEntity;
import com.lzz.exam.entity.UserEntity;
import com.lzz.exam.service.ClassToStudentService;
import com.lzz.exam.service.ClazzService;
import com.lzz.exam.service.UserService;
import com.lzz.exam.utils.R;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.LinkedList;
import java.util.List;

@RestController
@Api(description = "班级和学生绑定")
@RequestMapping(value = "/classtostu")
public class ClassToStudentController {

    @Autowired
    private ClassToStudentService classToStudentService;

    @Autowired
    private ClazzService clazzService;

    @Autowired
    private UserService userService;

    /**
     * 绑定
     *
     * @return R
     */
    @PostMapping("/binding")
    @ApiOperation("绑定")
    @Log("学生加入班级")
    public R binding(@RequestBody ClassToStudentEntity classToStudent){
        if(classToStudent.getClassid()!=null && classToStudent.getStudentid()!=null){
            QueryWrapper<ClassToStudentEntity> studentEntityQueryWrapper = new QueryWrapper<>();
            studentEntityQueryWrapper.eq("classid" , classToStudent.getClassid())
                    .eq("studentid" , classToStudent.getStudentid());
            if (classToStudentService.getOne(studentEntityQueryWrapper)!=null){
                return R.error().put("msg: " , "不能重复绑定");
            }
            QueryWrapper<ClazzEntity> wrapper = new QueryWrapper<>();
            wrapper.eq("id" , classToStudent.getClassid());
            ClazzEntity clazz = clazzService.getOne(wrapper);
            clazz.setStudentNum(clazz.getStudentNum()+1);
            return R.ok().put("binding" , classToStudentService.save(classToStudent) && clazzService.updateById(clazz));
        }
        return R.error();
    }

    /**
     * 解除绑定
     */

    @PostMapping("/unbinding")
    @ApiOperation("解除绑定")
    @Log("学生退出班级")
    public R unbinding(@RequestBody ClassToStudentEntity classToStudent){
        QueryWrapper<ClassToStudentEntity> wrapper = new QueryWrapper<>();
        wrapper.eq("classid" , classToStudent.getClassid())
                .eq("studentid" , classToStudent.getStudentid());
        List<ClassToStudentEntity> list = classToStudentService.list(wrapper);
        if(list != null){
            classToStudentService.remove(wrapper);
            QueryWrapper<ClazzEntity> wrapper1 = new QueryWrapper<>();
            wrapper1.eq("id" , classToStudent.getClassid());
            ClazzEntity clazz = clazzService.getOne(wrapper1);
            clazz.setStudentNum(clazz.getStudentNum()-list.size());
            clazzService.updateById(clazz);
        }
        return R.ok();
    }

    /**
     * 查询班级学生
     */
    @PostMapping("/queryStudent")
    @ApiOperation("查询班级下的所有学生")
    @Log("班级学生查询")
    public Page<ClassToStudentEntity> query(@RequestBody ClassToStudentEntity classToStudent){
        QueryWrapper<ClassToStudentEntity> wrapper = new QueryWrapper<>();
        wrapper.eq("classid" ,classToStudent.getClassid());
        Page<ClassToStudentEntity> page1 = classToStudentService.page(new Page<>(classToStudent.getPage(), classToStudent.getLimit()), wrapper);
        if(page1 != null){
            return  page1;
        }
        return null;
    }

    @PostMapping("/queryClass")
    @ApiOperation("查询学生对应所有班级")
    @Log("学生对应所有班级查询")
    public List<ClazzEntity> queryClass(@RequestBody ClassToStudentEntity classToStudent){
        QueryWrapper<ClassToStudentEntity> wrapper = new QueryWrapper<>();
        wrapper.eq("studentid" ,classToStudent.getStudentid());
        List<ClassToStudentEntity> list = classToStudentService.list(wrapper);
        List<ClazzEntity> clazzs = new LinkedList<>();
        list.forEach(clazz -> {
            Integer clazzid = clazz.getClassid();
            ClazzEntity c = clazzService.getById(clazzid);
            Integer teaid = c.getTeacherid();
            UserEntity user = userService.getById(teaid);
            c.setTeacherName(user.getUsername());
            clazzs.add(c);
        });
            return  clazzs;
    }
}
