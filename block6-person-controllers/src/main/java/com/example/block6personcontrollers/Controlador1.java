package com.example.block6personcontrollers;

import com.example.block6personcontrollers.City;
import com.example.block6personcontrollers.CityService;
import com.example.block6personcontrollers.Person;
import com.example.block6personcontrollers.PersonServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value="/controlador1")
public class Controlador1 {
    @Autowired //Inyección de dependencia para que utilice PersonService
    PersonServiceImpl personServiceImpl;

    @GetMapping(value="/addPersona")
    public Person addPerson(@RequestHeader ("personName") String personName, @RequestHeader ("personLocation") String personLocation, @RequestHeader ("personAge") int personAge) {
        return personServiceImpl.createPerson(personName, personLocation, personAge); //Se llama al método createPerson de PersonService para crear y retornar objeto tipo Person
    }

    @Autowired
    private CityService cityService;
    @PostMapping(value="/addCiudad")
    public City createCity(@RequestBody City city) {

        return cityService.addCity(city);
    }
}