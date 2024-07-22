package org.example.emlakburadauser;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class EmlakBuradaUserApplication {

    public static void main(String[] args) {
        SpringApplication.run(EmlakBuradaUserApplication.class, args);
    }

}
