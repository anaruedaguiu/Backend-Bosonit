package com.example.block6personcontrollers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class Controlador2 {
    @Autowired
    private PersonService personService;
    @GetMapping(value="/controlador2/getPersona")
    public Person getPerson(@RequestHeader ("nombre") String nombre, @RequestHeader ("poblacion") String poblacion, @RequestHeader ("edad") int edad) {
        return personService.addAgePerson(nombre, poblacion, edad); //Se llama al método addAgePerson de PersonService para crear y retornar objeto tipo Person con edad modificada
    }
}
