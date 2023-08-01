package com.formacion.block7crudvalidation.feign;

import com.formacion.block7crudvalidation.controllers.dto.output.TeacherOutputSimpleDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(url = "http://localhost:8081", name = "teacher-service")
public interface TeacherFeignClient {
    @GetMapping("/teacher/{id}")
    TeacherOutputSimpleDto getTeacherClient(@PathVariable Integer id);
}