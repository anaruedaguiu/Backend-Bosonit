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
    public Person getPerson(@RequestHeader ("personName") String personName, @RequestHeader ("personLocation") String personLocation, @RequestHeader ("personAge") int personAge) {
        return personService.addAgePerson(personName, personLocation, personAge); //Se llama al método addAgePerson de PersonService para crear y retornar objeto tipo Person con edad modificada
    }

    @Autowired
    private CityService cityService;
    @GetMapping(value="/getCiudades")
    public List<City> getAllCities() {
        return cityService.getCities();
    }
}
