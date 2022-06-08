package com.xred.service.tool.kms.client;

import de.codecentric.boot.admin.server.config.EnableAdminServer;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@EnableAdminServer
public class XRedServiceToolKmsClientApplication {

    public static void main(String[] args) {
        SpringApplication.run(XRedServiceToolKmsClientApplication.class, args);
    }
}
