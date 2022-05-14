package com.xred.xred.service.discovery.eureka.server;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

@SpringBootApplication
@EnableEurekaServer
public class XRedServiceDiscoveryEurekaServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(XRedServiceDiscoveryEurekaServerApplication.class, args);
    }

}
