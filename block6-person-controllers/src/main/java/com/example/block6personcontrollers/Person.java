package com.example.block6personcontrollers;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Person {
    private String personName;
    private String personLocation;
    private int personAge;
}