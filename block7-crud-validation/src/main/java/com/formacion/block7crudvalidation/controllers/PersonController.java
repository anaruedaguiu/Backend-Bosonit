package com.formacion.block7crudvalidation.controllers;

import com.formacion.block7crudvalidation.application.impl.PersonServiceImpl;
import com.formacion.block7crudvalidation.controllers.dto.PersonInputDto;
import com.formacion.block7crudvalidation.controllers.dto.PersonOutputDto;
import com.formacion.block7crudvalidation.domain.CustomError;
import com.formacion.block7crudvalidation.exceptions.EntityNotFoundException;
import com.formacion.block7crudvalidation.exceptions.UnprocessableEntityException;
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

    @PostMapping
    public ResponseEntity<PersonOutputDto> addPerson(@RequestBody PersonInputDto person) throws UnprocessableEntityException {
        URI location = URI.create("/person");
        return ResponseEntity.created(location).body(personServiceImpl.addPerson(person));
    }

    // Find person by ID
    @GetMapping(value="/{id}")
    public ResponseEntity<PersonOutputDto> getPersonById(@PathVariable int id) throws EntityNotFoundException {
        personServiceImpl.getPersonById(id);
        return ResponseEntity.ok().body(personServiceImpl.getPersonById(id));
    }

    // Find person by Username
    @GetMapping(value="/username/{username}")
    public ResponseEntity<PersonOutputDto> getPersonByUsername(@PathVariable String username) {
        try {
            return ResponseEntity.ok().body(personServiceImpl.getPersonByUsername(username));
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    // List of all persons
    @GetMapping
    public Iterable<PersonOutputDto> getAllPersons(
            @RequestParam(defaultValue = "0", required = false) int pageNumber,
            @RequestParam(defaultValue = "4", required = false) int pageSize) {
        return personServiceImpl.getAllPersons(pageNumber, pageSize);
    }

    @DeleteMapping
    public ResponseEntity<String> deletePersonById(@RequestParam int id) throws EntityNotFoundException {
        personServiceImpl.deletePerson(id);
        return ResponseEntity.ok().body("Person with id " + id + " was deleted");
    }

    @PutMapping(value="/{id}")
    public ResponseEntity<PersonOutputDto> updatePersonById(@RequestBody PersonInputDto person, @PathVariable int id) throws EntityNotFoundException, UnprocessableEntityException {
        personServiceImpl.getPersonById(person.getId());
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
}