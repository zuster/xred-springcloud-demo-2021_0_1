package com.xred.service.config.scc.server;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.config.server.EnableConfigServer;

@SpringBootApplication
@EnableConfigServer
public class XRedServiceConfigSpringCloudConfigServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(XRedServiceConfigSpringCloudConfigServerApplication.class, args);
    }

}
