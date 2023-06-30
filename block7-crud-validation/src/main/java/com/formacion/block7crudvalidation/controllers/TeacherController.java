package com.formacion.block7crudvalidation.controllers;

import com.formacion.block7crudvalidation.application.impl.TeacherServiceImpl;
import com.formacion.block7crudvalidation.controllers.dto.input.TeacherInputDto;
import com.formacion.block7crudvalidation.controllers.dto.output.TeacherOutputFullDto;
import com.formacion.block7crudvalidation.exceptions.EntityNotFoundException;
import org.hibernate.action.internal.EntityActionVetoException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.net.URI;
import java.util.Optional;
import java.util.ResourceBundle;

@RestController
@RequestMapping("/teacher")
public class TeacherController {
    @Autowired
    TeacherServiceImpl teacherServiceImpl;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<?> addTeacher(@RequestBody TeacherInputDto teacherInputDto) {
        try {
            Optional<TeacherOutputFullDto> optional = teacherServiceImpl.addTeacher(teacherInputDto);
            return ResponseEntity.ok(optional.orElseThrow());
        } catch (Exception e) {
            String errorMessage = "Error: this person has already a role assigned" + e.getMessage();
            return ResponseEntity.status(HttpStatus.CONFLICT).body(errorMessage);
        }
    }

    @GetMapping(value="/{id}")
    public ResponseEntity<TeacherOutputFullDto> getTeacherById(@PathVariable int id) {
        teacherServiceImpl.getTeacherById(id);
        return ResponseEntity.ok().body(teacherServiceImpl.getTeacherById(id));
    }

    @GetMapping
    public Iterable<TeacherOutputFullDto> getAllTeachers(
            @RequestParam (defaultValue = "0", required = false) int pageNumber,
            @RequestParam (defaultValue = "4", required = false) int pageSize) {
        return teacherServiceImpl.getAllTeachers(pageNumber, pageSize);
    }

    @DeleteMapping
    public ResponseEntity<String> deleteTeacherById(@RequestParam int id) throws EntityNotFoundException {
        try {
            teacherServiceImpl.deleteTeacherById(id);
            return ResponseEntity.ok().body("The teacher with id " + id + " was deleted");
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping(value="/{id}")
    public ResponseEntity<TeacherOutputFullDto> updateTeacherById(@RequestBody TeacherInputDto teacherInputDto, @PathVariable int id) {
        teacherServiceImpl.getTeacherById(teacherInputDto.getId_person());
        return ResponseEntity.ok().body(teacherServiceImpl.updateTeacherById(teacherInputDto, id));
    }
}