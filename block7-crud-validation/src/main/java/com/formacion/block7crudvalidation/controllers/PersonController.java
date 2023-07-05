package com.formacion.block7crudvalidation.controllers;

import com.formacion.block7crudvalidation.application.impl.PersonServiceImpl;
import com.formacion.block7crudvalidation.controllers.dto.input.PersonInputDto;
import com.formacion.block7crudvalidation.controllers.dto.output.PersonOutputDto;
import com.formacion.block7crudvalidation.controllers.dto.output.TeacherOutputSimpleDto;
import com.formacion.block7crudvalidation.domain.CustomError;
import com.formacion.block7crudvalidation.domain.Teacher;
import com.formacion.block7crudvalidation.exceptions.EntityNotFoundException;
import com.formacion.block7crudvalidation.exceptions.UnprocessableEntityException;
import com.formacion.block7crudvalidation.feign.TeacherFeignClient;
import feign.Feign;
import feign.slf4j.Slf4jLogger;
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
    @GetMapping(value="/{id}")
    public ResponseEntity<?> getPersonById(@PathVariable int id, @RequestParam(value = "outputType", defaultValue = "simple") String outputType) throws EntityNotFoundException {
        Object person = personServiceImpl.getPersonById(id, outputType);
        return ResponseEntity.ok().body(person);
    }

    // Find person by Username
    @GetMapping(value="/username/{username}")
    public ResponseEntity<?> getPersonByUsername(@PathVariable String username, @RequestParam(value = "outputType", defaultValue = "simple") String outputType) {
        Object person = personServiceImpl.getPersonByUsername(username, outputType);
        return ResponseEntity.ok().body(person);
    }

    // List of all persons
    @GetMapping
    public ResponseEntity<?> getAllPersons(
            @RequestParam(defaultValue = "0", required = false) int pageNumber,
            @RequestParam(defaultValue = "4", required = false) int pageSize,
            @RequestParam(value = "outputType", defaultValue = "simple") String outputType){
        if(outputType.equalsIgnoreCase("full")) {
            Iterable<?> persons = personServiceImpl.getAllPersonsFull(outputType);
            return ResponseEntity.ok().body(persons);
        } else {
            Iterable<PersonOutputDto> persons = personServiceImpl.getAllPersons(pageNumber, pageSize);
            return ResponseEntity.ok().body(persons);
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