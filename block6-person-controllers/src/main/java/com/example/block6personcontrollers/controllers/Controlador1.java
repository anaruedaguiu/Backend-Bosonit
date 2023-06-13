package com.example.block6personcontrollers.controllers;

import com.example.block6personcontrollers.models.City;
import com.example.block6personcontrollers.models.Person;
import com.example.block6personcontrollers.servicesImpl.CityServiceImpl;
import com.example.block6personcontrollers.servicesImpl.PersonServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value="/controlador1")
public class Controlador1 {
    @Autowired //Inyección de dependencia para que utilice PersonService
    PersonServiceImpl personServiceImpl;
    @Autowired
    CityServiceImpl cityServiceImpl;

    @GetMapping(value="/addPersona")
    public Person addPerson(@RequestHeader ("personName") String personName, @RequestHeader ("personLocation") String personLocation, @RequestHeader ("personAge") int personAge) {
        return personServiceImpl.createPerson(personName, personLocation, personAge); //Se llama al método createPerson de PersonService para crear y retornar objeto tipo Person
    }

    @PostMapping(value="/addCiudad")
    public City createCity(@RequestBody City city) {

        return cityServiceImpl.addCity(city);
    }
}