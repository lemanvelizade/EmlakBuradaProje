package org.example.emlakburadanotification;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class EmlakBuradaNotificationApplication {

    public static void main(String[] args) {
        SpringApplication.run(EmlakBuradaNotificationApplication.class, args);
    }

}
