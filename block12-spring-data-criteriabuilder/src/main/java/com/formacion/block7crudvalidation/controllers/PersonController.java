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
import com.formacion.block7crudvalidation.repository.PersonRepository;
import feign.Feign;
import feign.slf4j.Slf4jLogger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.time.LocalDate;
import java.util.Date;
import java.util.HashMap;

@RestController
@RequestMapping("/person")
public class PersonController {
    @Autowired
    PersonServiceImpl personServiceImpl;
    @Autowired
    PersonRepository personRepository;
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

    //Spring Data - Criteria Builder
    @GetMapping("/findPerson")
    public Iterable<PersonOutputDto> findPerson(@RequestParam(required = false) String username,
                                                @RequestParam(required = false) String name,
                                                @RequestParam(required = false) String surname,
                                                @RequestParam(required = false) LocalDate created_date,
                                                @RequestParam(required = false) String orderBy,
                                                @RequestParam(defaultValue = "1", required = false) int pageNumber,
                                                @RequestParam(defaultValue = "10", required = false) int pageSize) {

        HashMap<String, Object> data = new HashMap<>();

        if(username != null) data.put("username", username);
        if(name != null) data.put("name", name);
        if(surname != null) data.put("surname", surname);
        if(created_date != null) data.put("created_date", created_date);
        if(!orderBy.equals("n") && !orderBy.equals("u")){
            orderBy = "u";
        }

        return personRepository.getCustomQuery(data, orderBy, pageNumber, pageSize);
    }
}