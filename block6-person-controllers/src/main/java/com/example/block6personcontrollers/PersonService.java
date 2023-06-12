package com.example.block6personcontrollers;

import com.example.block6personcontrollers.Person;

public interface PersonService {
    public abstract Person createPerson(String personName, String personLocation, int personAge);

    public abstract Person addAgePerson(String personName, String personLocation, int personAge);
}