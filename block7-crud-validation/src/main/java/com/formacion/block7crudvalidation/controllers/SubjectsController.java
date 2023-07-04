package com.formacion.block7crudvalidation.controllers;

import com.formacion.block7crudvalidation.application.impl.SubjectsServiceImpl;
import com.formacion.block7crudvalidation.controllers.dto.input.SubjectsInputDto;
import com.formacion.block7crudvalidation.controllers.dto.output.StudentOutputSimpleDto;
import com.formacion.block7crudvalidation.controllers.dto.output.SubjectsOutputDto;
import com.formacion.block7crudvalidation.exceptions.EntityNotFoundException;
import org.hibernate.annotations.IdGeneratorType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@RestController
@RequestMapping("/subjects")
public class SubjectsController {
    @Autowired
    SubjectsServiceImpl subjectsServiceImpl;

    @PostMapping
    public ResponseEntity<SubjectsOutputDto> addSubject(@RequestBody SubjectsInputDto subjectsInputDto) {
        URI location = URI.create("/subjects");
        return ResponseEntity.created(location).body(subjectsServiceImpl.addSubject(subjectsInputDto));
    }

    @PostMapping("/student")
    public ResponseEntity<String> addSubjectToStudent(@RequestParam int id_subject, @RequestParam int id_student) {
        try {
            subjectsServiceImpl.addSubjectsToStudent(id_subject, id_student);
            return ResponseEntity.ok().body("The subject with id " + id_subject + " was successfully added to student with id " + id_student);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Something went wrong " + e.getMessage());
        }
    }


    @GetMapping(value="/{id}")
    public ResponseEntity<SubjectsOutputDto> getSubjectById(@PathVariable int id) throws EntityNotFoundException {
        subjectsServiceImpl.getSubjectById(id);
        return ResponseEntity.ok().body(subjectsServiceImpl.getSubjectById(id));
    }

    @GetMapping
    public Iterable<SubjectsOutputDto> getAllSubjects(
            @RequestParam(defaultValue = "0", required = false) int pageNumber,
            @RequestParam(defaultValue = "4", required = false) int pageSize) {
        return subjectsServiceImpl.getAllSubjects(pageNumber, pageSize);
    }

    @DeleteMapping
    public ResponseEntity<String> deleteSubjectById(@RequestParam int id) throws EntityNotFoundException {
        try {
            subjectsServiceImpl.deleteSubjectById(id);
            return ResponseEntity.ok().body("Subject with id " + id + " was deleted");
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping(value="/{id}")
    public ResponseEntity<SubjectsOutputDto> updateSubjectBtId(@RequestBody SubjectsInputDto subjectsInputDto, @PathVariable int id) {
        subjectsServiceImpl.updateSubjectById(subjectsInputDto, id);
        return ResponseEntity.ok().body(subjectsServiceImpl.getSubjectById(id));
    }


    @GetMapping("/subjects/{id}")
    public ResponseEntity<StudentOutputSimpleDto> getSubjectsListByStudent(@PathVariable int id) {
        return ResponseEntity.ok().body(subjectsServiceImpl.getSubjectsListByStudent(id));
    }
}
