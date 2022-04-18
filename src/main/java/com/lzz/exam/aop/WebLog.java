package com.lzz.exam.aop;


import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.lang.reflect.Method;

@Data
@TableName("user_operation_log")
public class WebLog {


    @TableId(type = IdType.AUTO)
    private Integer id;
    /**
     * 操作用户
     */
    @TableField(value = "username")
    private String username;
    /**
     * 用户id
     */
    @TableField(value = "userid")
    private Integer userid;
    /**
     * 用户等级
     */
    @TableField(value = "user_level")
    private Integer userLevel;
    /**
     * 方法名
     */
    @TableField(value = "method")
    private String method;
    /**
     * 注解值
     */
    @TableField("operation")
    private String operation;
    /**
     * 操作时间
     */
    @TableField("operation_time")
    private Long operationTime;

    /**
     * 消耗时间
     */
    @TableField("spend_time")
    private Integer spendTime;


}