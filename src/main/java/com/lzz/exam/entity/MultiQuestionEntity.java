package com.lzz.exam.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * 选择题题库表
 * 
 * @author lzz
 * @email 1399508400@qq.com
 * @date 2022-03-21 15:20:42
 */
@Data
@TableName("multi_question")
public class MultiQuestionEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 试题编号
	 */
	@TableId(type = IdType.AUTO)
	private Integer id;
	/**
	 * 考试科目
	 */
	private String subject;
	/**
	 * 问题题目
	 */
	private String question;
	/**
	 * 选项A
	 */
	private String answera;
	/**
	 * 选项B
	 */
	private String answerb;
	/**
	 * 选项C
	 */
	private String answerc;
	/**
	 * 选项D
	 */
	private String answerd;
	/**
	 * 正确答案
	 */
	private String rightanswer;
	/**
	 * 题目解析
	 */
	private String analysis;
	/**
	 * 分数
	 */
	private Integer score;
	/**
	 * 所属章节
	 */
	private String section;
	/**
	 * 难度等级
	 */
	private Integer level;
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
}
