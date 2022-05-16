package com.xred.service.discovery.multi.consumer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient(autoRegister = false)
public class XRedServiceDiscoveryMultiConsumerApplication {

    public static void main(String[] args) {
        SpringApplication.run(XRedServiceDiscoveryMultiConsumerApplication.class, args);
    }

}
