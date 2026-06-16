package com.somosayni.postulaciones;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = {"com.somosayni.postulaciones", "com.somosayni.shared"})
public class PostulacionesApplication {
    public static void main(String[] args) {
        SpringApplication.run(PostulacionesApplication.class, args);
    }
}
