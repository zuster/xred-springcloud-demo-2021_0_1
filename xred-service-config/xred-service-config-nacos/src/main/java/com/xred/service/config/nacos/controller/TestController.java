package com.xred.service.config.nacos.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * read env config Controller
 *
 * @author songyh
 * @date 2022/5/31 6:53 PM
 */
@RestController
@RequestMapping(value = "/env")
public class TestController {
    @Autowired
    private ApplicationContext applicationContext;

    @GetMapping("/config/{configName}")
    public String readConfig(@PathVariable(value = "configName") String configName) {
        return applicationContext.getEnvironment().getProperty(configName);
    }
}
