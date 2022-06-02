package com.xred.service.config.nacos.controller;

import org.springframework.context.annotation.Scope;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

/**
 * Scope 测试 Controller - singleton
 *
 * @author songyh
 * @date 2022/6/2 6:48 PM
 */
@RestController
@RequestMapping("/scope/singleton")
@Scope("singleton")
public class ScopeSingletonTestController {
    @PostConstruct
    public void init() {
        System.out.println("ScopeSingletonTestController - init()");
    }

    @PreDestroy
    public void destroy() {
        System.out.println("ScopeSingletonTestController - destroy()");
    }

    @GetMapping
    public String test() {
        return "ScopeSingletonTestController - test()";
    }
}
