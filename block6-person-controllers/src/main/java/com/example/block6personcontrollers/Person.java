package com.example.block6personcontrollers;

public class Person {
    private String personName;
    private String personLocation;
    private int personAge;

    public Person(String personName, String personLocation, int personAge) {
        this.personName = personName;
        this.personLocation = personLocation;
        this.personAge = personAge;
    }

    public String getPersonName() {
        return personName;
    }

    public void setPersonName(String personName) {
        this.personName = personName;
    }

    public String getPersonLocation() {
        return personLocation;
    }

    public void setPersonLocation(String personLocation) {
        this.personLocation = personLocation;
    }

    public int getPersonAge() {
        return personAge;
    }

    public void setPersonAge(int personAge) {
        this.personAge = personAge;
    }
}