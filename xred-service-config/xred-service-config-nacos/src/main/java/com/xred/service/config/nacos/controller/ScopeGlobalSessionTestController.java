package com.xred.service.config.nacos.controller;

import org.springframework.context.annotation.Scope;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

/**
 * Scope 测试 Controller - global session
 *
 * @author songyh
 * @date 2022/6/2 6:48 PM
 */
@RestController
@RequestMapping("/scope/globalSession")
@Scope("global session")
public class ScopeGlobalSessionTestController {
    @PostConstruct
    public void init() {
        System.out.println("ScopeGlobalSessionTestController - init()");
    }

    @PreDestroy
    public void destroy() {
        System.out.println("ScopeGlobalSessionTestController - destroy()");
    }

    @GetMapping
    public String test() {
        return "ScopeGlobalSessionTestController - test()";
    }
}
