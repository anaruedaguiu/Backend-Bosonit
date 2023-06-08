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
    public Person addPerson(@RequestHeader ("personName") String personName, @RequestHeader ("personLocation") String personLocation, @RequestHeader ("personAge") int personAge) {
        return personService.createPerson(personName, personLocation, personAge); //Se llama al método createPerson de PersonService para crear y retornar objeto tipo Person
    }

    @Autowired
    private CityService cityService;
    @PostMapping(value="/addCiudad")
    public City createCity(@RequestBody City city) {

        return cityService.addCity(city);
    }
}