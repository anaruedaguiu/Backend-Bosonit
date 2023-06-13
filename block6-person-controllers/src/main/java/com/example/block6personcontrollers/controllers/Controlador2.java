package com.example.block6personcontrollers.controllers;

import com.example.block6personcontrollers.models.City;
import com.example.block6personcontrollers.models.Person;
import com.example.block6personcontrollers.servicesImpl.CityServiceImpl;
import com.example.block6personcontrollers.servicesImpl.PersonServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value="/controlador2")
public class Controlador2 {
    @Autowired
    PersonServiceImpl personServiceImpl;
    @Autowired
    CityServiceImpl cityServiceImpl;

    @GetMapping(value="/getPersona")
    public Person getPerson(@RequestHeader ("personName") String personName, @RequestHeader ("personLocation") String personLocation, @RequestHeader ("personAge") int personAge) {
        return personServiceImpl.addAgePerson(personName, personLocation, personAge); //Se llama al método addAgePerson de PersonService para crear y retornar objeto tipo Person con edad modificada
    }

    @GetMapping(value="/getCiudades")
    public List<City> getAllCities() {

        return cityServiceImpl.getCities();
    }
}