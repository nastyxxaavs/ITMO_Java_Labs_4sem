package org.example.labthree;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = "org.example")
public class LabThreeApplication {

    public static void main(String[] args) {
        SpringApplication.run(LabThreeApplication.class, args);
    }

}
