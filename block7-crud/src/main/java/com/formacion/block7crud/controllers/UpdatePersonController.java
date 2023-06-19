package com.formacion.block7crud.controllers;

import com.formacion.block7crud.application.PersonServiceImpl;
import com.formacion.block7crud.controllers.dto.PersonInputDto;
import com.formacion.block7crud.controllers.dto.PersonOutputDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/person")
public class UpdatePersonController {
    @Autowired
    PersonServiceImpl personServiceImpl;

    @PutMapping(value="/{id}")
    public ResponseEntity<PersonOutputDto> updatePerson (@RequestBody PersonInputDto person, @PathVariable int id) {
        try {
            return ResponseEntity.ok().body(personServiceImpl.updatePerson(person));
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }
}