package com.example.block6personcontrollers;

import org.springframework.stereotype.Service;

@Service
public class PersonService {
    public Person createPerson(String nombre, String poblacion, int edad) {
        Person person = new Person(nombre, poblacion, edad);
        return person;
    }

    public Person addAgePerson(String nombre, String poblacion, int edad) {
        Person person = new Person(nombre, poblacion, edad);
        person.setEdad(person.getEdad() * 2);
        return person;
    }
}

//Esta clase actúa como un servicio que se encarga de la lógica para crear objetos de tipo Person.