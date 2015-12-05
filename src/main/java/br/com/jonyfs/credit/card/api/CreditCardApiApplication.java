package br.com.jonyfs.credit.card.api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages="br.com.jonyfs")
public class CreditCardApiApplication {

    public static void main(String[] args) {
        SpringApplication.run(CreditCardApiApplication.class, args);
    }
}
