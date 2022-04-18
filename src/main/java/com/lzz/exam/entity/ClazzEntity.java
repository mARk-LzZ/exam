package com.lzz.exam.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

@Data
@TableName("clazz")
public class ClazzEntity {
    /**
     * id
     */
    @TableId(type = IdType.AUTO)
    private Integer id;
    /**
     * 大学
     */
    private String university;
    /**
     * 学院
     */
    private String institute;
    /**
     * 专业
     */
    private String major;
    /**
     * 年级
     */
    private String grade;
    /**
     * 班级
     */
    private String clazz;
    /**
     * 创建时间
     */
    private Date createTime;
    /**
     * 更新时间
     */
    private Date updateTime;
    /**
     * 当前学生人数
     */
    private Integer studentNum;
    /**
     * 老师id
     */
    private Integer teacherid;
    /**
     * 页数
     */
    @TableField(exist=false)
    private Integer page;
    /**
     * 限制
     */
    @TableField(exist=false)
    private Integer limit;
    /**
     * 起始时间
     */
    @TableField(exist=false)
    private Date startTime;
    /**
     * 结束时间
     */
    @TableField(exist=false)
    private Date endTime;

    @TableField(exist = false)
    private String teacherName;
}
