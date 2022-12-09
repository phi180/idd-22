package it.uniroma3.idd.tablesrepo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = {"it.uniroma3.idd.hw3"})
public class TablesRepoApplication {

    public static void main(String[] args) {
        SpringApplication.run(TablesRepoApplication.class, args);
    }

}
