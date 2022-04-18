package com.lzz.exam.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * 
 * 
 * @author lzz
 * @email 1399508400@qq.com
 * @date 2022-03-21 15:20:42
 */
@Data
@TableName("student_answer")
public class StudentAnswerEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 主键
	 */
	@TableId(type = IdType.AUTO)
	private Integer id;
	/**
	 * 用户id
	 */
	private Integer userid;
	/**
	 * 试卷id
	 */
	private Integer examid;
	/**
	 * 题目类型
	 */
	private Integer questionType;
	/**
	 * 题目id
	 */
	private Integer questionid;
	/**
	 * 用户答案
	 */
	private String userAnswer;
	/**
	 * 正确答案
	 */
	private String rightAnswer;
	/**
	 * 用户得分
	 */
	private Integer score;
	/**
	 * 0:待批改 1:批改完成
	 */
	private Integer questionStatus;
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
