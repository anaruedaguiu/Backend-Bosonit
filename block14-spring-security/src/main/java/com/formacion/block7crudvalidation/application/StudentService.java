package com.formacion.block7crudvalidation.application;

import com.formacion.block7crudvalidation.application.impl.StudentServiceImpl;
import com.formacion.block7crudvalidation.controllers.dto.input.StudentInputDto;
import com.formacion.block7crudvalidation.controllers.dto.output.StudentOutputFullDto;
import com.formacion.block7crudvalidation.controllers.dto.output.StudentOutputSimpleDto;
import com.formacion.block7crudvalidation.domain.Student;

import java.util.List;
import java.util.Optional;
import java.util.Set;


public interface StudentService {
    Optional<StudentOutputFullDto> addStudentFull(StudentInputDto student) throws Exception;
    StudentOutputSimpleDto getStudentSimpleById(Integer id);
    StudentOutputFullDto getStudentFullById(Integer id);
    Iterable<StudentOutputFullDto> getAllStudents(int pageNumber, int pageSize);
    void deleteStudentById(Integer id);
    StudentOutputFullDto updateStudentFullById(StudentInputDto student, Integer id);

    StudentOutputSimpleDto addSubjectListToStudent(int id, List<Integer> id_list) throws Exception;
    StudentOutputSimpleDto removeSubjectListToStudent(int id, List<Integer> id_list) throws Exception;
}
