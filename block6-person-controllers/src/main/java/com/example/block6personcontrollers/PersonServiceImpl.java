package com.example.block6personcontrollers;

import com.example.block6personcontrollers.Person;
import org.springframework.stereotype.Service;
import com.example.block6personcontrollers.PersonService;

@Service
public class PersonServiceImpl implements PersonService {
    @Override
    public Person createPerson(String personName, String personLocation, int personAge) {
        Person person = new Person(personName, personLocation, personAge);
        return person;
    }

    @Override
    public Person addAgePerson(String personName, String personLocation, int personAge) {
        Person person = new Person(personName, personLocation, personAge);
        person.setPersonAge(person.getPersonAge()*2);
        return person;
    }
}