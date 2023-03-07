package com.greybear.snackmachine;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jdbc.repository.config.EnableJdbcRepositories;

@SpringBootApplication
@EnableJdbcRepositories
public class SnackMachineApplication {
    public static void main(String[] args) {
        SpringApplication.run(SnackMachineApplication.class, args);
    }
}