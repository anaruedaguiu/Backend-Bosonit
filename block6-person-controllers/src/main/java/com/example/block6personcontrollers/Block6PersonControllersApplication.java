package com.example.block6personcontrollers;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class Block6PersonControllersApplication {

	public static void main(String[] args) {
		SpringApplication.run(Block6PersonControllersApplication.class, args);
	}

	//Crear 3 objetos de tipo Persona utilizando la etiqueta @Bean
	@Bean("bean1")
	public Person personBean1() {
		return new Person("PersonBean1", "LocationBean1", 1);
	}

	@Bean("bean2")
	public Person personBean2() {
		return new Person("PersonBean2", "LocationBean2", 2);
	}

	@Bean("bean3")
	public Person personBean3() {
		return new Person("PersonBean3", "LocationBean3", 3);
	}
}