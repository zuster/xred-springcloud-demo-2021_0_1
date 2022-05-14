package com.xred.service.discovery.nacos.consumer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient(autoRegister = false)
public class XRedServiceDiscoveryNacosConsumerApplication {

    public static void main(String[] args) {
        SpringApplication.run(XRedServiceDiscoveryNacosConsumerApplication.class, args);
    }

}
