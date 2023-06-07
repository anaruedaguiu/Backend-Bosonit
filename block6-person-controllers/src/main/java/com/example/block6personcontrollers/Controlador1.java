package com.example.block6personcontrollers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class Controlador1 {
    @Autowired //Inyección de dependencia para que utilice PersonService
    private PersonService personService;
    @GetMapping(value="/controlador1/addPersona")
    public Person addPerson(@RequestHeader ("nombre") String nombre, @RequestHeader ("poblacion") String poblacion, @RequestHeader ("edad") int edad) {
        return personService.createPerson(nombre, poblacion, edad); //Se llama al método createPerson de PersonService para crear y retornar objeto tipo Person
    }
}