package com.lzz.exam.controller;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lzz.exam.aop.WebLog;
import com.lzz.exam.service.WebLogService;
import com.lzz.exam.utils.R;
import io.swagger.annotations.Api;
import io.swagger.models.auth.In;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;

@RestController
@Slf4j
@Api(description = "日志管理")
@RequestMapping("exam/weblog")
public class WebLogController {

    @Autowired
    private WebLogService webLogService;


    @GetMapping("list")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public Page<WebLog> list(@RequestParam("page") Integer page , @RequestParam("limit") Integer limit){
        return webLogService.page(new Page<>(page,limit));
    }

    @PostMapping("/delete")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public R delete(@RequestBody Integer[] ids){
        return R.ok().put("msg" , webLogService.removeByIds(Arrays.asList(ids)));
    }

}
