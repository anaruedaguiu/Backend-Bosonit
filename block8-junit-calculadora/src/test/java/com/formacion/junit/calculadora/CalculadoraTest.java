package com.formacion.junit.calculadora;

import org.junit.jupiter.api.Test;
import org.springframework.boot.autoconfigure.data.redis.ClientResourcesBuilderCustomizer;

import static org.junit.jupiter.api.Assertions.*;

class CalculadoraTest {

    @Test
    void suma() {
        Calculadora calculadora = new Calculadora();
        int resultado = calculadora.suma(32,94);
        assertEquals(126, resultado); //Variable a comparar
    }

    @Test
    void resta() {
        Calculadora calculadora = new Calculadora();
        int resultado = calculadora.resta(97,23);
        assertEquals(74, resultado);
    }

    @Test
    void division() {
        Calculadora calculadora = new Calculadora();
        double resultado = calculadora.division(20,5);
        assertEquals(4, resultado);
    }

    @Test
    void multiplicacion() {
        Calculadora calculadora = new Calculadora();
        int resultado = calculadora.multiplicacion(100,6);
        assertEquals(600, resultado);
    }
}