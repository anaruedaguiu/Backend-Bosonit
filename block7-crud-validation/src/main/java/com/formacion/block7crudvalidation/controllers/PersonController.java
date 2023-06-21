package com.formacion.block7crudvalidation.controllers;

import com.formacion.block7crudvalidation.application.PersonServiceImpl;
import com.formacion.block7crudvalidation.controllers.dto.PersonInputDto;
import com.formacion.block7crudvalidation.controllers.dto.PersonOutputDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/person")
public class PersonController {
    @Autowired
    PersonServiceImpl personServiceImpl;

    @PostMapping
    public ResponseEntity<PersonOutputDto> addPerson(@RequestBody PersonInputDto person) throws Exception {
        URI location = URI.create("/person");
        return ResponseEntity.created(location).body(personServiceImpl.addPerson(person));
    }

    // Find person by ID
    @GetMapping(value="/{id}")
    public ResponseEntity<PersonOutputDto> getPersonById(@PathVariable int id) {
        try {
            return ResponseEntity.ok().body(personServiceImpl.getPersonById(id));
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
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
    public ResponseEntity<String> deletePersonById(@RequestParam int id) {
        try {
            personServiceImpl.deletePerson(id);
            return ResponseEntity.ok().body("Person with id " + id + " was deleted");
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping(value="/{id}")
    public ResponseEntity<PersonOutputDto> updatePersonById(@RequestBody PersonInputDto person, @PathVariable int id) {
        try {
            personServiceImpl.getPersonById(person.getId());
            return ResponseEntity.ok().body(personServiceImpl.updatePerson(person, id));
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }
}