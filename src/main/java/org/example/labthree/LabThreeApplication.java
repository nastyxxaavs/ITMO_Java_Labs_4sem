package org.example.labthree;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;

@SpringBootApplication
@ComponentScan(basePackages = "org.example")
public class LabThreeApplication {

    public static void main(String[] args) {
        SpringApplication.run(LabThreeApplication.class, args);
    }

}
