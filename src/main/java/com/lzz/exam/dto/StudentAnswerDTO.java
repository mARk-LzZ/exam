package com.lzz.exam.dto;

import lombok.Data;

@Data
public class StudentAnswerDTO {
    /**
     *题目
     */
    private String questionName;
    /**
     * 题目类型
     */
    private Integer questionType;
    /**
     * 正确答案
     */
    private String rightAnswer;
    /**
     * 题目分数
     */
    private Integer questionScore;
    /**
     * 学生答案
     */
    private String studentAnswer;
    /**
     * 学生得分
     */
    private Integer studentScore;
    /**
     * 学生答案id
     */
    private Integer studentAnswerId;
    /**
     * 考试名称
     */
    private String source;

}
