package com.lzz.exam.controller;

import java.io.UnsupportedEncodingException;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.plugins.pagination.PageDto;
import com.lzz.exam.aop.Log;
import com.lzz.exam.entity.ClassToStudentEntity;
import com.lzz.exam.service.ClassToStudentService;
import com.lzz.exam.utils.JWTUtils;
import com.lzz.exam.utils.PageUtils;
import com.lzz.exam.utils.R;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import com.lzz.exam.entity.AnnouncementEntity;
import com.lzz.exam.service.AnnouncementService;
import springfox.documentation.annotations.ApiIgnore;

import javax.servlet.http.HttpServletRequest;


/**
 * 留言表
 *
 * @author lzz
 * @email 1399508400@qq.com
 * @date 2022-03-22 16:58:42
 */
@RestController
@Api(value = "通知" , description = "通知")
@RequestMapping("exam/announcement")
public class AnnouncementController {
    @Autowired
    private AnnouncementService announcementService;

    @Autowired
    private ClassToStudentService classToStudentService;



    /**
     * 查询通知
     */
    @PostMapping("/info")
    @PreAuthorize("hasAnyRole('ROLE_TEACHER','ROLE_ADMIN')")
    @Log("查询通知")
    public R info(@RequestBody AnnouncementEntity announcement) {
        QueryWrapper<AnnouncementEntity> wrapper = new QueryWrapper<>();
        wrapper.like(StringUtils.isNotEmpty(announcement.getTitle()), "title", announcement.getTitle())
                .like(StringUtils.isNotEmpty(announcement.getContent()) , "content" , announcement.getContent())
                .eq(announcement.getClazzid()!=null , "clazzid" , announcement.getClazzid())
                .between(announcement.getStartTime() != null && announcement.getEndTime() != null, "create_time", announcement.getStartTime(), announcement.getEndTime());

        PageDto<AnnouncementEntity> result = announcementService.page(new PageDto<>(announcement.getPage() , announcement.getLimit()) , wrapper);

        return R.ok().put("announcement", result);
    }

    @GetMapping("/getmyann")
    @ApiOperation("学生获取自己的通知")
    @Log("学生获取自己的通知")
    public R getMyAnnouncement(@ApiIgnore HttpServletRequest request , @RequestParam(value = "page")Integer page , @RequestParam("limit") Integer limit) throws UnsupportedEncodingException{
        String token = request.getHeader("token");
        Map<String, String> info = JWTUtils.getInfo(token);
        String studentid = info.get("id");
        QueryWrapper<ClassToStudentEntity> wrapper1 = new QueryWrapper<>();
        wrapper1.eq("studentid" ,studentid);
        List<AnnouncementEntity> list = new LinkedList<>();
        for (ClassToStudentEntity student : classToStudentService.list(wrapper1)) {
            Integer classid = student.getClassid();
            QueryWrapper<AnnouncementEntity> w = new QueryWrapper<>();
            w.eq("clazzid", classid).orderByDesc("id");
            list.addAll(announcementService.list(w));
        }
        Page<?> pages = PageUtils.getPages(page, limit, list);
        return R.ok().put("acc" , pages);
    }
    /**
     * 保存
     */
    @PostMapping("/save")
    @PreAuthorize("hasAnyRole('ROLE_TEACHER','ROLE_ADMIN')")
    @ApiOperation("ids内传入班级id，对ids内所有班级发送通知")
    @Log("保存通知")
    public R save(@RequestBody AnnouncementEntity announcement){
        Integer[] ids = announcement.getIds();
        for(int id : ids){
            announcement.setClazzid(id);
            announcementService.save(announcement);
        }
        return R.ok();
    }

    /**
     * 修改
     */
    @PostMapping("/update")
    @PreAuthorize("hasAnyRole('ROLE_TEACHER','ROLE_ADMIN')")
    @Log("修改通知")
   // @RequiresPermissions("exam:announcement:update")
    public R update(@RequestBody AnnouncementEntity announcement){
		announcementService.updateById(announcement);

        return R.ok();
    }

    /**
     * 批量删除
     */
    @PostMapping("/delete")
    @Log("批量删除通知")
    @PreAuthorize("hasAnyRole('ROLE_TEACHER','ROLE_ADMIN')")
   // @RequiresPermissions("exam:announcement:delete")
    public R delete(@RequestBody Integer[] ids){
		announcementService.removeByIds(Arrays.asList(ids));
        return R.ok();
    }

}
