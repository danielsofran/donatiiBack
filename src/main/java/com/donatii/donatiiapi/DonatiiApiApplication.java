package com.donatii.donatiiapi;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication

@OpenAPIDefinition(
        info = @Info(
                title = "Donatii Animale",
                version = "1.0.0"
        )
)
public class DonatiiApiApplication {

    public static void main(String[] args) {
        SpringApplication.run(DonatiiApiApplication.class, args);
    }
}

