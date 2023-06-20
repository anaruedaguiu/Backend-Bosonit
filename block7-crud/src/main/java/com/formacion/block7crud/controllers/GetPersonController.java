package com.formacion.block7crud.controllers;

import com.formacion.block7crud.application.PersonServiceImpl;
import com.formacion.block7crud.controllers.dto.PersonInputDto;
import com.formacion.block7crud.controllers.dto.PersonOutputDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/person")
public class GetPersonController {
    @Autowired
    PersonServiceImpl personServiceImpl;
    @GetMapping(value="/{id}")
    public ResponseEntity<PersonOutputDto> getPersonById(@PathVariable int id) {
        try {
            return ResponseEntity.ok().body(personServiceImpl.getPersonById(id));
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping
    public Iterable<PersonOutputDto> getAllPersons(
            @RequestParam(defaultValue = "0", required = false) int pageNumber,
            @RequestParam(defaultValue = "4", required = false) int pageSize) {
        return personServiceImpl.getAllPersons(pageNumber, pageSize);
    }

    @GetMapping(value="/name/{name}")
    public ResponseEntity<List<PersonOutputDto>> getPersonsByName(@PathVariable String name) {
        List<PersonOutputDto> persons = personServiceImpl.getPersonsByName(name);
        try {
            return ResponseEntity.ok().body(persons);
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }
}