package com.formacion.block7crudvalidation.application;

import com.formacion.block7crudvalidation.controllers.dto.input.StudentInputDto;
import com.formacion.block7crudvalidation.controllers.dto.output.StudentOutputFullDto;
import com.formacion.block7crudvalidation.controllers.dto.output.StudentOutputSimpleDto;

import java.util.Optional;


public interface StudentService {
    Optional<StudentOutputFullDto> addStudentFull(StudentInputDto student) throws Exception;
    StudentOutputSimpleDto getStudentSimpleById(Integer id);
    StudentOutputFullDto getStudentFullById(Integer id);
    Iterable<StudentOutputFullDto> getAllStudents(int pageNumber, int pageSize);
    void deleteStudentById(Integer id);
    StudentOutputFullDto updateStudentFullById(StudentInputDto student, Integer id);
}
