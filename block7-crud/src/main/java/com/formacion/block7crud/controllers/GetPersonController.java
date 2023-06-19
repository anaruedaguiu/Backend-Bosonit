package com.formacion.block7crud.controllers;

import com.formacion.block7crud.application.PersonServiceImpl;
import com.formacion.block7crud.controllers.dto.PersonInputDto;
import com.formacion.block7crud.controllers.dto.PersonOutputDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}