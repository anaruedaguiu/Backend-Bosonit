package com.formacion.backendfront;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class BackendFrontApplication {

	public static void main(String[] args) {
		SpringApplication.run(BackendFrontApplication.class, args);
	}

}
