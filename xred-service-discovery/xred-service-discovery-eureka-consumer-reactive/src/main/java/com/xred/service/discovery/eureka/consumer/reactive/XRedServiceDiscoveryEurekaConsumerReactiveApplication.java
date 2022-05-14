package com.xred.service.discovery.eureka.consumer.reactive;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient(autoRegister = false)
public class XRedServiceDiscoveryEurekaConsumerReactiveApplication {

    public static void main(String[] args) {
        SpringApplication.run(XRedServiceDiscoveryEurekaConsumerReactiveApplication.class, args);
    }

}
