package com.formacion.block7crudvalidation.controllers;

import com.formacion.block7crudvalidation.application.impl.StudentServiceImpl;
import com.formacion.block7crudvalidation.controllers.dto.StudentInputDto;
import com.formacion.block7crudvalidation.controllers.dto.StudentOutputFullDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@RestController
@RequestMapping("/student")
public class StudentController {
    @Autowired
    StudentServiceImpl studentServiceImpl;

    @PostMapping
    public ResponseEntity<StudentOutputFullDto> addStudent(@RequestBody StudentInputDto student) throws Exception {
        URI location = URI.create("/student");
        return ResponseEntity.created(location).body(studentServiceImpl.addStudentFull(student));
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
}