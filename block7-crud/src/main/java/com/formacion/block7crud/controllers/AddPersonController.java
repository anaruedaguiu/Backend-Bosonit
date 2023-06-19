package com.formacion.block7crud.controllers;

import com.formacion.block7crud.application.PersonServiceImpl;
import com.formacion.block7crud.controllers.dto.PersonInputDto;
import com.formacion.block7crud.controllers.dto.PersonOutputDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;

@RestController
@RequestMapping("/person")
public class AddPersonController {
    @Autowired
    PersonServiceImpl personServiceImpl;

    @PostMapping
    public ResponseEntity<PersonOutputDto> addPerson (@RequestBody PersonInputDto person) {
        URI location = URI.create("/person");
        return ResponseEntity.created(location).body(personServiceImpl.addPerson(person));
    }
}
