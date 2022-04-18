package com.lzz.exam.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

@Data
@TableName("paper")
public class PaperEntity {

    @TableId(type = IdType.AUTO)
    private Integer id;

    @TableField(value = "name")
    private String name;

    @TableField(value = "major")
    private String major;

    @TableField(value = "totalScore")
    private Integer totalScore;

    @TableField(value = "totalTime")
    private Integer totalTime;

    @TableField(value = "create_time")
    private Date createTime;

    @TableField(value = "update_time")
    private Date updateTime;

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
}
