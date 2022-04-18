package com.lzz.exam.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * 考试管理表
 * 
 * @author lzz
 * @email 1399508400@qq.com
 * @date 2022-03-21 15:20:42
 */
@Data
@TableName("exam_manage")
public class ExamManageEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 考试编号
	 */
	@TableId(type = IdType.AUTO)
	private Integer id;
	/**
	 * 该次考试介绍
	 */
	private String description;
	/**
	 * 课程名称
	 */
	private String source;
	/**
	 * 试卷编号
	 */
	private Integer paperid;
	/**
	 * 考试日期
	 */
	private String examdate;
	/**
	 * 持续时长
	 */
	private Integer totaltime;
	/**
	 * 总分
	 */
	private Integer totalscore;
	/**
	 * 班级id
	 */
	private Integer classid;
	/**
	 * 班级名称
	 */
	@TableField(exist = false)
	private String className;
	/**
	 * 考生须知
	 */
	private String tips;
	/**
	 * 创建时间
	 */
	private Date createTime;
	/**
	 * 更新时间
	 */
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
	/**
	 * ids
	 */
	@TableField(exist = false)
	private Integer[] ids;
	/**
	 * 状态
	 */
	private Integer status;
}
