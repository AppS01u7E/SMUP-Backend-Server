package com.appsolute.soomapi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;

@SpringBootApplication
@ConfigurationPropertiesScan
public class SoomApiApplication {
    public static void main(String[] args) {
        SpringApplication.run(SoomApiApplication.class, args);
    }

}
