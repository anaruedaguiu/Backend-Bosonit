package com.formacion.block7crudvalidation.application.impl;

import com.formacion.block7crudvalidation.controllers.dto.output.TeacherOutputSimpleDto;
import com.formacion.block7crudvalidation.feign.TeacherFeignClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TeacherFeignService implements TeacherFeignClient {
    @Autowired
    TeacherFeignClient teacherFeignClient;

    @Override
    public TeacherOutputSimpleDto getTeacherClient(Integer id) {
        return teacherFeignClient.getTeacherClient(id);
    }
}
