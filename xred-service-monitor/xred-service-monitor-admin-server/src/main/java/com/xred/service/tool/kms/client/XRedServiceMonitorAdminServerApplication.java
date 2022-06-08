package com.xred.service.tool.kms.client;

import de.codecentric.boot.admin.server.config.EnableAdminServer;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@EnableAdminServer
public class XRedServiceMonitorAdminServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(XRedServiceMonitorAdminServerApplication.class, args);
    }
}
