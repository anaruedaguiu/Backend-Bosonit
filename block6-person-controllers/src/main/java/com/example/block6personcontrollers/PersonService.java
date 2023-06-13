package com.example.block6personcontrollers;

public interface PersonService {
    public abstract Person createPerson(String personName, String personLocation, int personAge);

    public abstract Person addAgePerson(String personName, String personLocation, int personAge);
}