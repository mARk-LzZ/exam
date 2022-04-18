package com.lzz.exam.service.impl;


import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lzz.exam.dao.ExamToStudentDao;
import com.lzz.exam.entity.ExamToStudentEntity;
import com.lzz.exam.service.ExamToStudentService;
import org.springframework.stereotype.Service;

@Service("examToStudentService")
public class ExamToStudentServiceImpl extends ServiceImpl<ExamToStudentDao , ExamToStudentEntity> implements ExamToStudentService {
}
