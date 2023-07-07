package com.formacion.block7crudvalidation.controllers;

import com.formacion.block7crudvalidation.application.impl.StudentServiceImpl;
import com.formacion.block7crudvalidation.controllers.dto.input.StudentInputDto;
import com.formacion.block7crudvalidation.controllers.dto.input.TeacherInputDto;
import com.formacion.block7crudvalidation.controllers.dto.output.StudentOutputFullDto;
import com.formacion.block7crudvalidation.controllers.dto.output.StudentOutputSimpleDto;
import com.formacion.block7crudvalidation.controllers.dto.output.TeacherOutputFullDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/student")
public class StudentController {
    @Autowired
    StudentServiceImpl studentServiceImpl;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<?> addStudent(@RequestBody StudentInputDto studentInputDto) {
        try {
            Optional<StudentOutputFullDto> optional = studentServiceImpl.addStudentFull(studentInputDto);
            return ResponseEntity.ok(optional.orElseThrow());
        } catch (Exception e) {
            String errorMessage = "Error: this person has already a role assigned" + e.getMessage();
            return ResponseEntity.status(HttpStatus.CONFLICT).body(errorMessage);
        }
    }

    @GetMapping(value="/{id}")
    public ResponseEntity<?> getStudentFullById(@PathVariable int id, @RequestParam(value = "outputType", defaultValue = "simple") String outputType) {
        if(outputType.equalsIgnoreCase("full")) {
            studentServiceImpl.getStudentFullById(id);
            return ResponseEntity.ok().body(studentServiceImpl.getStudentFullById(id));
        } else  {
            studentServiceImpl.getStudentSimpleById(id);
            return ResponseEntity.ok().body(studentServiceImpl.getStudentSimpleById(id));
        }
    }

    @GetMapping
    public Iterable<StudentOutputFullDto> getAllStudents(
            @RequestParam(defaultValue = "0", required = false) int pageNumber,
            @RequestParam(defaultValue = "4", required = false) int pageSize) {
        return studentServiceImpl.getAllStudents(pageNumber, pageSize);
    }

    @DeleteMapping
    public ResponseEntity<String> deleteStudentById(@RequestParam int id) {
        try {
            studentServiceImpl.deleteStudentById(id);
            return ResponseEntity.ok().body("Student with id " + id + " was deleted");
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping(value="/{id}")
    public ResponseEntity<StudentOutputFullDto> updateStudentById(@RequestBody StudentInputDto student, @PathVariable Integer id) {
        studentServiceImpl.getStudentFullById(student.getId_person());
        return ResponseEntity.ok().body(studentServiceImpl.updateStudentFullById(student, id));
    }

    @PutMapping("/addSubjects/{id_student}")
    public ResponseEntity<StudentOutputSimpleDto> addSubjectListToStudent(@PathVariable int id_student, @RequestParam("id_list") List<Integer> id_list) {
        try {
            studentServiceImpl.addSubjectListToStudent(id_student, id_list);
            return ResponseEntity.ok().body(studentServiceImpl.getStudentSimpleById(id_student));
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/removeSubjects/{id_student}")
    public ResponseEntity<StudentOutputSimpleDto> removeSubjectListToStudent(@PathVariable int id_student, @RequestParam("id_list") List<Integer> id_list) {
        try {
            studentServiceImpl.removeSubjectListToStudent(id_student, id_list);
            return ResponseEntity.ok().body(studentServiceImpl.getStudentSimpleById(id_student));
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }
}