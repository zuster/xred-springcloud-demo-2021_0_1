package com.xred.service.config.nacos.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.context.ApplicationContext;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

/**
 * Nacos Env Controller
 *
 * @author songyh
 * @date 2022/6/2 1:22 AM
 */
@RestController
@RequestMapping(value = "/nacos/env")
@RefreshScope
public class NacosEnvController {
    @Autowired
    private ApplicationContext context;
    @Value("${custom.welcome}")
    private String welcome;

    @PostConstruct
    public void init() {
        System.out.println("NacosEnvController - init");
    }

    @PreDestroy
    public void destroy() {
        System.out.println("NacosEnvController - destroy");
    }

    @GetMapping("/welcome")
    public String welcome() {
        return welcome;
    }

}
