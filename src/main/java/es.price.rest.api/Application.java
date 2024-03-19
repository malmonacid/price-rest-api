package es.price.rest.api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = "es.price.rest.api")
public class Application {
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

}