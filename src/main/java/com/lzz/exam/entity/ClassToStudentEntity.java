package com.lzz.exam.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("class_to_student")
public class ClassToStudentEntity {

    private Integer classid;

    private Integer studentid;

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
    public ClassToStudentEntity() {
    }

    public ClassToStudentEntity(Integer classid, Integer studentid) {
        this.classid = classid;
        this.studentid = studentid;
    }

}
