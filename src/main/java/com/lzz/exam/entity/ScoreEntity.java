package com.lzz.exam.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * 成绩管理表
 * 
 * @author lzz
 * @email 1399508400@qq.com
 * @date 2022-03-21 15:20:42
 */
@Data
@TableName("score")
public class ScoreEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 分数编号
	 */
	@TableId(type = IdType.AUTO)
	private Integer id;
	/**
	 * 考试编号
	 */
	private Integer examid;
	/**
	 * 学号
	 */
	private Integer userid;
	/**
	 * 总成绩
	 */
	private Integer score;
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
