package com.formacion.block7crudvalidation.application;

import com.formacion.block7crudvalidation.controllers.dto.input.TeacherInputDto;
import com.formacion.block7crudvalidation.controllers.dto.output.TeacherOutputFullDto;
import com.formacion.block7crudvalidation.exceptions.EntityNotFoundException;

import java.util.Optional;

public interface TeacherService {
    TeacherOutputFullDto getTeacherById(int id);
    Optional<TeacherOutputFullDto> addTeacher(TeacherInputDto teacherInputDto) throws Exception;
    void deleteTeacherById(int id) throws EntityNotFoundException;
    TeacherOutputFullDto updateTeacherById(TeacherInputDto teacherInputDto, int id);
    Iterable<TeacherOutputFullDto> getAllTeachers(int pageNumber, int pageSize);
}
