package com.xred.service.discovery.consul.consumer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient(autoRegister = false)
public class XRedServiceDiscoveryConsulConsumerApplication {

    public static void main(String[] args) {
        SpringApplication.run(XRedServiceDiscoveryConsulConsumerApplication.class, args);
    }

}
