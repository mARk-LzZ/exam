package com.lzz.exam.controller;

import java.io.UnsupportedEncodingException;
import java.util.Arrays;
import java.util.Map;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.lzz.exam.aop.Log;
import com.lzz.exam.utils.JWTUtils;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import com.lzz.exam.entity.ScoreEntity;
import com.lzz.exam.service.ScoreService;
import com.lzz.exam.utils.PageUtils;
import com.lzz.exam.utils.R;
import springfox.documentation.annotations.ApiIgnore;

import javax.servlet.http.HttpServletRequest;


/**
 * 成绩管理表
 *
 * @author lzz
 * @email 1399508400@qq.com
 * @date 2022-03-21 15:20:42
 */
@RestController
@Api(description ="分数管理")
@RequestMapping("exam/score")
public class ScoreController {
    @Autowired
    private ScoreService scoreService;

    /**
     * 列表
     */
    @PostMapping("/list")
    @Log("分数查询")
   // @RequiresPermissions("exam:score:list")
    public R list(@ApiIgnore HttpServletRequest request) throws UnsupportedEncodingException {
        String token = request.getHeader("token");
        Map<String, String> info = JWTUtils.getInfo(token);
        String id = info.get("id");
        QueryWrapper<ScoreEntity> scoreEntityQueryWrapper = new QueryWrapper<>();
        scoreEntityQueryWrapper.eq("userid" , id);
        return R.ok().put("page", scoreService.list(scoreEntityQueryWrapper));
    }


    /**
     * 信息
     */
    @PostMapping("/info/{scoreid}")
   // @RequiresPermissions("exam:score:info")
    @PreAuthorize("hasAnyRole('ROLE_TEACHER' , 'ROLE_ADMIN')")
    public R info(@PathVariable("scoreid") Integer scoreid){
		ScoreEntity score = scoreService.getById(scoreid);

        return R.ok().put("score", score);
    }

    /**
     * 保存
     */
    @PostMapping("/save")
    @Log("分数保存")
    @PreAuthorize("hasAnyRole('ROLE_TEACHER' , 'ROLE_ADMIN')")
    //  @RequiresPermissions("exam:score:save")
    public R save(@RequestBody ScoreEntity score){
		scoreService.save(score);

        return R.ok();
    }

    /**
     * 修改
     */
    @PostMapping("/update")
    @Log("分数修改")
    @PreAuthorize("hasAnyRole('ROLE_TEACHER' , 'ROLE_ADMIN')")
    // @RequiresPermissions("exam:score:update")
    public R update(@RequestBody ScoreEntity score){
		scoreService.updateById(score);

        return R.ok();
    }

    /**
     * 删除
     */
    @PostMapping("/delete")
    @Log("分数删除")
    @PreAuthorize("hasAnyRole('ROLE_TEACHER' , 'ROLE_ADMIN')")
    // @RequiresPermissions("exam:score:delete")
    public R delete(@RequestBody Integer[] scoreids){
		scoreService.removeByIds(Arrays.asList(scoreids));

        return R.ok();
    }

}
