package com.lzz.exam.entity;


import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("exam_to_student")
public class ExamToStudentEntity {

    @TableField(value = "userid")
    private Integer userid;

    @TableField(value = "examid")
    private Integer examid;

    @TableField(value = "status")
    private Integer status;
}
