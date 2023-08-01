package com.formacion.block7crudvalidation;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.ImportAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.cloud.openfeign.FeignAutoConfiguration;

@EnableFeignClients
@SpringBootApplication
@ImportAutoConfiguration({FeignAutoConfiguration.class})
public class Block13TestingJUnitApplication {

	public static void main(String[] args) {
		SpringApplication.run(Block13TestingJUnitApplication.class, args);
	}

}
