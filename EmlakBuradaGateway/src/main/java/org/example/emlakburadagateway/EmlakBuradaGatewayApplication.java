package org.example.emlakburadagateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class EmlakBuradaGatewayApplication {

    public static void main(String[] args) {
        SpringApplication.run(EmlakBuradaGatewayApplication.class, args);
    }

}
