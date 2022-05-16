package com.xred.service.discovery.multi.provider;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.client.serviceregistry.AutoServiceRegistrationProperties;

@SpringBootApplication
@EnableConfigurationProperties(AutoServiceRegistrationProperties.class)
public class XRedServiceDiscoveryMultiProviderApplication {

    public static void main(String[] args) {
        SpringApplication.run(XRedServiceDiscoveryMultiProviderApplication.class, args);
    }

}
