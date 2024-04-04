package org.dopaminz;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;

@ConfigurationPropertiesScan
@SpringBootApplication
public class DopaminzApplication {

    public static void main(String[] args) {
        SpringApplication.run(DopaminzApplication.class, args);
    }
}
