package com.formacion.block6simplecontrollers;

import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class SimpleController {
    @GetMapping(value="/user/{nombre}")
    public String nombre(@PathVariable String nombre) {
        return "Hola " + nombre;
    }

    @PostMapping(value="/useradd")
    public ResponseEntity<Person> addPerson(@RequestBody Person person) {
        person.getNombre();
        person.getPoblacion();
        person.setEdad(person.getEdad()+1);

        return ResponseEntity.ok(person);
    }
}
