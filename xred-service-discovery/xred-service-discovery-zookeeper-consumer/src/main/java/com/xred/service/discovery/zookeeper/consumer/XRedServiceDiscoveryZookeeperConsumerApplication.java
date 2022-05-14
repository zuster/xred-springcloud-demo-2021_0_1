package com.xred.service.discovery.zookeeper.consumer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient(autoRegister = false)
public class XRedServiceDiscoveryZookeeperConsumerApplication {

    public static void main(String[] args) {
        SpringApplication.run(XRedServiceDiscoveryZookeeperConsumerApplication.class, args);
    }

}
