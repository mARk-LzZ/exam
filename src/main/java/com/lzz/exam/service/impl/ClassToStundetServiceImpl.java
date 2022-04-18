package com.lzz.exam.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lzz.exam.dao.ClassToStudentDao;
import com.lzz.exam.entity.ClassToStudentEntity;
import com.lzz.exam.service.ClassToStudentService;
import org.springframework.stereotype.Service;

@Service("ClassToStundetService")
public class ClassToStundetServiceImpl extends ServiceImpl<ClassToStudentDao , ClassToStudentEntity> implements ClassToStudentService {
}
