package com.ray.yygh;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = "com.ray")
public class ServiceHospitalApplication {
    public static void main(String[] args) {
        SpringApplication.run(ServiceHospitalApplication.class, args);
    }
}
