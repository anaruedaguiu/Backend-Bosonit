package com.formacion.block7crud.controllers;

import com.formacion.block7crud.application.PersonServiceImpl;
import com.formacion.block7crud.controllers.dto.PersonOutputDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/person")
public class DeletePersonController {
    @Autowired
    PersonServiceImpl personServiceImpl;

    @DeleteMapping
    public ResponseEntity<String> deletePersonById(@RequestParam int id) {
        try {
            personServiceImpl.deletePersonById(id);
            return ResponseEntity.ok().body("Person with id: " + id + " was deleted");
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }
}