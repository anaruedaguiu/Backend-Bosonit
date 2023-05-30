package com.formacion.block5properties;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.core.env.Environment;

@SpringBootApplication
public class Block5PropertiesApplication implements CommandLineRunner {

	@Autowired
	private Environment environment;
	private String apartadoDos;
	@Value("${greeting}")
	private String greeting;
	@Value("${my.number}")
	private int myNumber;
	@Value("${new.property:new.property no tiene valor}")
	private String newProperty;

	public static void main(String[] args) {
		SpringApplication.run(Block5PropertiesApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		System.out.println("El valor de greeting es " + greeting);
		System.out.println("El Valor de my.number es " + myNumber);
		System.out.println("El valor de new.property es " + newProperty);
		this.apartadoDos = environment.getProperty("greeting");
		System.out.println(apartadoDos);
	}
}