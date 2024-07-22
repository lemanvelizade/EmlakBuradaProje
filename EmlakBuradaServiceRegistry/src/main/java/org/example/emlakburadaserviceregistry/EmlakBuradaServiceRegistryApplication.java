package org.example.emlakburadaserviceregistry;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

@SpringBootApplication
@EnableEurekaServer
public class EmlakBuradaServiceRegistryApplication {

    public static void main(String[] args) {
        SpringApplication.run(EmlakBuradaServiceRegistryApplication.class, args);
    }

}
