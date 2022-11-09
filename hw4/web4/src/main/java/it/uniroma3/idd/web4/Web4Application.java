package it.uniroma3.idd.web4;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = {"it.uniroma3.idd"})
public class Web4Application {

    public static void main(String[] args) {
        SpringApplication.run(Web4Application.class, args);
    }

}
