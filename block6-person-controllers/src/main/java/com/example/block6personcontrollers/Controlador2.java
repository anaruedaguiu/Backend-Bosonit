package com.example.block6personcontrollers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value="/controlador2")
public class Controlador2 {
    @Autowired
    private PersonService personService;
    @GetMapping(value="/getPersona")
    public Person getPerson(@RequestHeader ("nombre") String nombre, @RequestHeader ("poblacion") String poblacion, @RequestHeader ("edad") int edad) {
        return personService.addAgePerson(nombre, poblacion, edad); //Se llama al método addAgePerson de PersonService para crear y retornar objeto tipo Person con edad modificada
    }

    @Autowired
    private CityService cityService;
    @GetMapping(value="/getCiudades")
    public List<City> getCities() {
        return cityService.getCities();
    }
}
