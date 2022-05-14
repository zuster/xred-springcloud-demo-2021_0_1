package com.xred.service.discovery.eureka.consumer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient(autoRegister = false)
public class XRedServiceDiscoveryEurekaConsumerApplication {

    public static void main(String[] args) {
        SpringApplication.run(XRedServiceDiscoveryEurekaConsumerApplication.class, args);
    }

}
