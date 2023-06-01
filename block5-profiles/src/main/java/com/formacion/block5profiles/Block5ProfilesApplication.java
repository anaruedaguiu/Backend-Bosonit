package com.formacion.block5profiles;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Block5ProfilesApplication implements CommandLineRunner {
	@Value("${bd.url}")
	private String bdUrl;
	@Value("${spring.profiles.active}")
	private String springProfilesActive;

	public static void main(String[] args) {
		SpringApplication.run(Block5ProfilesApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		System.out.println("Valor de la propiedad bdUrl: " + bdUrl);
		System.out.println("Perfil activo: " + springProfilesActive);
	}
}
