package com.example.block6personcontrollers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping(value="/controlador1")
public class Controlador1 {
    @Autowired //Inyección de dependencia para que utilice PersonService
    private PersonService personService;

    @GetMapping(value="/addPersona")
    public Person addPerson(@RequestHeader ("nombre") String nombre, @RequestHeader ("poblacion") String poblacion, @RequestHeader ("edad") int edad) {
        return personService.createPerson(nombre, poblacion, edad); //Se llama al método createPerson de PersonService para crear y retornar objeto tipo Person
    }

    @Autowired
    private CityService cityService;
    @PostMapping(value="/addCiudad")
    public City addCity(@RequestBody City city) {

        return cityService.addCity(city);
    }
}