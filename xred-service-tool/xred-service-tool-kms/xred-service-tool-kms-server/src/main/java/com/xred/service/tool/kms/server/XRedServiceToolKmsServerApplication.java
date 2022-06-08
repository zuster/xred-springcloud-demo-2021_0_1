package com.xred.service.tool.kms.server;

import de.codecentric.boot.admin.server.config.EnableAdminServer;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@EnableAdminServer
public class XRedServiceToolKmsServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(XRedServiceToolKmsServerApplication.class, args);
    }
}
