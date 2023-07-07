package com.formacion.block7crudvalidation.application;

import com.formacion.block7crudvalidation.controllers.dto.input.SubjectsInputDto;
import com.formacion.block7crudvalidation.controllers.dto.output.StudentOutputSimpleDto;
import com.formacion.block7crudvalidation.controllers.dto.output.SubjectsOutputDto;

import java.util.Set;

public interface SubjectsService {
    SubjectsOutputDto addSubject(SubjectsInputDto subjectsInputDto);
    void addSubjectsToStudent(int id_subject, int id_student);
    SubjectsOutputDto getSubjectById(int id);
    Iterable<SubjectsOutputDto> getAllSubjects(int pageNumber, int pageSize);
    void deleteSubjectById(int id);
    SubjectsOutputDto updateSubjectById(SubjectsInputDto subjectsInputDto, int id);
    StudentOutputSimpleDto getSubjectsListByStudent(int id_student);
}
