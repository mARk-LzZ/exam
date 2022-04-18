package com.lzz.exam.dto;

import lombok.Data;

import java.util.List;

@Data
public class StudentAnswerScoreDTO {

    private List<StudentAnswerDTO> answerDTOS;

    /**
     * 总分
     */
    private Integer score;
 }
