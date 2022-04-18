package com.lzz.exam.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * 留言表
 * 
 * @author lzz
 * @email 1399508400@qq.com
 * @date 2022-03-22 16:58:42
 */
@Data
@TableName("announcement")
public class AnnouncementEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 留言编号
	 */
	@TableId(type = IdType.AUTO)
	private Integer id;
	/**
	 * 标题
	 */
	private String title;
	/**
	 * 留言内容
	 */
	private String content;
	/**
	 * 所属班级
	 */
	private Integer clazzid;
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

	@TableField(exist = false)
	private Integer[] ids;

}
