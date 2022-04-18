package com.lzz.exam.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;
import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 试卷管理表
 * 
 * @author lzz
 * @email 1399508400@qq.com
 * @date 2022-03-21 15:20:42
 */
@Data
@TableName("paper_manage")
public class PaperManageEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 试卷编号
	 */
	private Integer paperid;
	/**
	 * 题目类型
	 */
	@TableField(value = "question_type")
	private Integer questiontype;


	/**
	 * 题目编号
	 */
	private Integer questionid;
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

	public PaperManageEntity(Integer paperid, Integer questiontype, Integer questionid) {
		this.paperid = paperid;
		this.questiontype = questiontype;
		this.questionid = questionid;
	}

	public PaperManageEntity() {
	}
}
