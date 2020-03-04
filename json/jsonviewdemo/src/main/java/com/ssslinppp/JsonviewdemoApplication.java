package com.ssslinppp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.web.servlet.ServletComponentScan;

@SpringBootApplication
@EnableConfigurationProperties
@ServletComponentScan
public class JsonviewdemoApplication {

    public static void main(String[] args) {
        SpringApplication.run(JsonviewdemoApplication.class, args);
    }
}
