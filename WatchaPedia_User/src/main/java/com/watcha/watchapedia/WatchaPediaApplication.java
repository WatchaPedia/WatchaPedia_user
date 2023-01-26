package com.watcha.watchapedia;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;

@ConfigurationPropertiesScan
@SpringBootApplication
public class WatchaPediaApplication {

    public static void main(String[] args) {
        SpringApplication.run(WatchaPediaApplication.class, args);
    }

}
