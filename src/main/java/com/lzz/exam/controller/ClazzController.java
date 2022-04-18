package com.lzz.exam.controller;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lzz.exam.aop.Log;
import com.lzz.exam.entity.AnnouncementEntity;
import com.lzz.exam.entity.ClazzEntity;
import com.lzz.exam.entity.ScoreEntity;
import com.lzz.exam.service.ClazzService;
import com.lzz.exam.utils.JWTUtils;
import com.lzz.exam.utils.PageUtils;
import com.lzz.exam.utils.R;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

@RestController
@Api(value = "班级" , description = "班级")
@RequestMapping("exam/class")
public class ClazzController {

    @Autowired
    private ClazzService clazzService;
    /**
     * 列表
     */
    @PostMapping("/info")
    @Log("班级查询")
    public Page<ClazzEntity> list(@RequestBody ClazzEntity clazz){
        LambdaQueryWrapper<ClazzEntity> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(clazz.getId()!=null , ClazzEntity::getId,clazz.getId())
                .eq(clazz.getClazz()!=null , ClazzEntity::getClazz , clazz.getClazz())
                .like(clazz.getGrade()!=null,ClazzEntity::getGrade,clazz.getGrade())
                .like(clazz.getInstitute()!= null,ClazzEntity::getInstitute,clazz.getInstitute())
                .like(clazz.getMajor()!=null,ClazzEntity::getMajor,clazz.getMajor())
                .between(clazz.getStartTime()!=null && clazz.getEndTime()!=null , ClazzEntity::getCreateTime , clazz.getStartTime(),clazz.getEndTime())
                .like(clazz.getUniversity()!=null ,ClazzEntity::getUniversity,clazz.getUniversity())
                .eq(clazz.getTeacherid() != null , ClazzEntity::getTeacherid , clazz.getTeacherid());
        return clazzService.page(new Page<>(clazz.getPage(),clazz.getLimit()),wrapper);
    }


    /**
     * 保存
     */
    @PostMapping("/save")
    @Log("班级保存")
    public R save(@RequestBody ClazzEntity clazz , HttpServletRequest request) throws UnsupportedEncodingException {
        String token = request.getHeader("token");
        Map<String, String> info = JWTUtils.getInfo(token);
        String id = info.get("id");
        clazz.setTeacherid(Integer.parseInt(id));
        clazzService.save(clazz);
        return R.ok();
    }

    /**
     * 修改
     */
    @PostMapping("/update")
    @Log("班级修改")
    public R update(@RequestBody ClazzEntity clazz){
        clazzService.updateById(clazz);

        return R.ok();
    }

    /**
     * 删除
     */
    @PostMapping("/delete")
    @Log("班级删除")
    public R delete(@RequestBody Integer[] scoreids){
        clazzService.removeByIds(Arrays.asList(scoreids));

        return R.ok();
    }
}
