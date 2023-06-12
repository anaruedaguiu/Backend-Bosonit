package com.formacion.block6simplecontrollers;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Person {
    private String nombre;
    private String poblacion;
    private int edad;
}