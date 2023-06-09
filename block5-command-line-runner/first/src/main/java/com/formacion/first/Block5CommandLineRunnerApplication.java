package com.formacion.first;

import jakarta.annotation.PostConstruct;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Block5CommandLineRunnerApplication implements CommandLineRunner {

	public static void main(String[] args) {

		SpringApplication.run(Block5CommandLineRunnerApplication.class, args);
	}

	@PostConstruct
	public void claseInicial(){
		System.out.println("Hola desde clase inicial");
	}

	public void claseSecundaria(){
		System.out.println("Hola desde clase secundaria");
	}
	public void claseTercera(String mensaje){
		System.out.println(mensaje);
	}

	//La interfaz CommandLineRunner necesita implementar el método run
	@Override
	public void run(String... args) throws Exception {
		claseSecundaria();
		claseTercera("Soy la tercera clase");
	}
}

//El orden en el que se muestran los mensajes se debe a usar PostConstruct y CommandLineRunner de esa forma
//PostConstruct ejecuta durante la fase de inicialización de Spring, antes de ejecutarse el método run()
//Luego se ejecuta el método run(), que llama a los métodos en el orden en el que aparecen

//Tercera función: se pasa el mensaje como parámetro, y después se pasa la cadena de texto como argumento al parámetro mensaje