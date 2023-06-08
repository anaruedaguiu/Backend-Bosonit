package com.example.block6personcontrollers;

import org.springframework.stereotype.Service;

@Service
public class PersonService {
    public Person createPerson(String personName, String personLocation, int personAge) {
        Person person = new Person(personName, personLocation, personAge);
        return person;
    }

    public Person addAgePerson(String personName, String personLocation, int personAge) {
        Person person = new Person(personName, personLocation, personAge);
        person.setPersonAge(person.getPersonAge() * 2);
        return person;
    }
}

//Esta clase actúa como un servicio que se encarga de la lógica para crear objetos de tipo Person.