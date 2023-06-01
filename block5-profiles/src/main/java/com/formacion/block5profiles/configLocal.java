package com.formacion.block5profiles;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;


@Configuration
@Profile("Local")
public class configLocal implements CommandLineRunner {
    @Value("${bd.url}")
    public String bdUrl;

    @Override
    public void run(String... args) throws Exception {
        System.out.println(bdUrl);
    }
}
