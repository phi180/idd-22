package it.uniroma3.idd.web3;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = {"it.uniroma3.idd", "it.uniroma3.idd.hw3.api"})
public class Web3Application {

    public static void main(String[] args) {
        SpringApplication.run(Web3Application.class, args);
    }

}
