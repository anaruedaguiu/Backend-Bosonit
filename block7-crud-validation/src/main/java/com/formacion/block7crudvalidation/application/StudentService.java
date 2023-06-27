package com.formacion.block7crudvalidation.application;

import com.formacion.block7crudvalidation.controllers.dto.StudentInputDto;
import com.formacion.block7crudvalidation.controllers.dto.StudentOutputFullDto;
import com.formacion.block7crudvalidation.controllers.dto.StudentOutputSimpleDto;


public interface StudentService {
    StudentOutputFullDto addStudentFull(StudentInputDto student) throws  Exception;
    StudentOutputSimpleDto getStudentSimpleById(Integer id);
    StudentOutputFullDto getStudentFullById(Integer id);
    Iterable<StudentOutputFullDto> getAllStudents(int pageNumber, int pageSize);
    void deleteStudentById(Integer id);
    StudentOutputFullDto updateStudentFullById(StudentInputDto student, Integer id);
}
