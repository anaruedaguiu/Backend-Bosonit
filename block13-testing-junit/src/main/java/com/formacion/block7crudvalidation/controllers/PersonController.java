package com.formacion.block7crudvalidation.controllers;

import com.formacion.block7crudvalidation.application.impl.PersonServiceImpl;
import com.formacion.block7crudvalidation.controllers.dto.input.PersonInputDto;
import com.formacion.block7crudvalidation.controllers.dto.output.PersonOutputDto;
import com.formacion.block7crudvalidation.controllers.dto.output.TeacherOutputSimpleDto;
import com.formacion.block7crudvalidation.domain.CustomError;
import com.formacion.block7crudvalidation.exceptions.EntityNotFoundException;
import com.formacion.block7crudvalidation.exceptions.UnprocessableEntityException;
import com.formacion.block7crudvalidation.feign.TeacherFeignClient;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@RestController
@RequestMapping("/person")
public class PersonController {
    @Autowired
    PersonServiceImpl personServiceImpl;
    @Autowired
    TeacherFeignClient teacherFeignClient;

    @PostMapping
    public ResponseEntity<PersonOutputDto> addPerson(@RequestBody PersonInputDto person) throws UnprocessableEntityException {
        URI location = URI.create("/person");
        return ResponseEntity.created(location).body(personServiceImpl.addPerson(person));
    }

    // Find person by ID
    @GetMapping("/{id}")
    public ResponseEntity<PersonOutputDto> getPersonById(@PathVariable int id) {
        try {
            return ResponseEntity.ok().body(personServiceImpl.getPersonById(id));
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping(value="/role/{id}")
    public ResponseEntity<?> getPersonAndRoleById(@PathVariable int id, @RequestParam(value = "outputType", defaultValue = "simple") String outputType) throws EntityNotFoundException {
        Object person = personServiceImpl.getPersonAndRoleById(id, outputType);
        return ResponseEntity.ok().body(person);
    }

    // Find person by Username
    @GetMapping("/username/{username}")
    public ResponseEntity<PersonOutputDto> getPersonByUsername(@PathVariable String username) {
        try {
            return ResponseEntity.ok().body(personServiceImpl.getPersonByUsername(username));
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    // List of all persons
    @GetMapping
    public ResponseEntity<Iterable<PersonOutputDto>> getAllPersons(
            @RequestParam(defaultValue = "0", required = false) int pageNumber,
            @RequestParam(defaultValue = "4", required = false) int pageSize) {
        try {
            return ResponseEntity.ok().body(personServiceImpl.getAllPersons(pageNumber, pageSize));
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping
    public ResponseEntity<String> deletePersonById(@RequestParam int id) throws EntityNotFoundException {
        personServiceImpl.deletePerson(id);
        return ResponseEntity.ok().body("Person with id " + id + " was deleted");
    }

    @PutMapping(value="/{id}")
    public ResponseEntity<PersonOutputDto> updatePersonById(@RequestBody PersonInputDto person, @PathVariable int id) throws EntityNotFoundException, UnprocessableEntityException {
        personServiceImpl.updatePerson(person, id);
        return ResponseEntity.ok().body(personServiceImpl.updatePerson(person, id));
    }

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<CustomError> handleEntityNotFoundException(EntityNotFoundException ex) {
        CustomError customError = ex.getEntityNotFoundException();
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(customError);
    }

    @ExceptionHandler(UnprocessableEntityException.class)
    public ResponseEntity<CustomError> handleUnprocessableEntityException(UnprocessableEntityException ex) {
        CustomError customError = ex.getUnprocessableEntityException();
        return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(customError);
    }

    @GetMapping("/teacher/{id}")
    public TeacherOutputSimpleDto getTeacherClient(@PathVariable Integer id) {
        TeacherOutputSimpleDto teacherOutputSimpleDto = teacherFeignClient.getTeacherClient(id);
        return teacherOutputSimpleDto;
    }
}