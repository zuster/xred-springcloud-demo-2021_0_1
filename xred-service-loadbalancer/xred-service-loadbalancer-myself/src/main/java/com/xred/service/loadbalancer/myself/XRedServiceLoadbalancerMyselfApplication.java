package com.xred.service.loadbalancer.myself;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.client.serviceregistry.AutoServiceRegistrationProperties;

@SpringBootApplication
@EnableDiscoveryClient(autoRegister = false)
@EnableConfigurationProperties(AutoServiceRegistrationProperties.class)
public class XRedServiceLoadbalancerMyselfApplication {

    public static void main(String[] args) {
        SpringApplication.run(XRedServiceLoadbalancerMyselfApplication.class, args);
    }

}
