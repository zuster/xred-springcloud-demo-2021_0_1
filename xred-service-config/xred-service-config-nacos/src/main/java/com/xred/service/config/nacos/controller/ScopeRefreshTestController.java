package com.xred.service.config.nacos.controller;

import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.context.annotation.Scope;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

/**
 * Scope 测试 Controller - refresh
 *
 * @author songyh
 * @date 2022/6/2 6:48 PM
 */
@RestController
@RequestMapping("/scope/refresh")
@RefreshScope
public class ScopeRefreshTestController {
    @PostConstruct
    public void init() {
        System.out.println("ScopeRefreshTestController - init()");
    }

    @PreDestroy
    public void destroy() {
        System.out.println("ScopeRefreshTestController - destroy()");
    }

    @GetMapping
    public String test() {
        return "ScopeRefreshTestController - test()";
    }
}
