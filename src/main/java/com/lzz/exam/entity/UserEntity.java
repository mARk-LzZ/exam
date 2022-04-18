package com.lzz.exam.entity;



import java.io.Serializable;
import java.util.Date;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import org.springframework.data.annotation.Id;

/**
 * 学生信息表
 * 
 * @author lzz
 * @email 1399508400@qq.com
 * @date 2022-03-21 15:20:42
 */
@Data
@TableName("user")
public class UserEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * ID
	 */
	@TableId(type= IdType.AUTO)
	private Integer id;
	/**
	 * 姓名
	 */
	private String username;
	/*
	* 密保问题
	* */
	private String passques;
	/*
	* 密保答案
	* */
	private String passans;
	/**
	 * 密码
	 */
	private String password;
	/**
	 * 角色(2管理员，1教师，0学生)
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
	 * +++
	 */
	@TableField(exist=false)
	private Date endTime;
}
