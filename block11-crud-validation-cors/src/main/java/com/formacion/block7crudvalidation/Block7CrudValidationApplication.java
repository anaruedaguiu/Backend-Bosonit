package com.formacion.block7crudvalidation;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.ImportAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.cloud.openfeign.FeignAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@EnableFeignClients
@SpringBootApplication
@ImportAutoConfiguration({FeignAutoConfiguration.class})
public class Block7CrudValidationApplication {

	public static void main(String[] args) {
		SpringApplication.run(Block7CrudValidationApplication.class, args);
	}

	@Bean
	public WebMvcConfigurer corsConfigurer() {
		return new WebMvcConfigurer() {
			@Override
			public void addCorsMappings(CorsRegistry registry) {
				registry.addMapping("/person/**").allowedOrigins("https://cdpn.io");
			}
		};
	}

}
