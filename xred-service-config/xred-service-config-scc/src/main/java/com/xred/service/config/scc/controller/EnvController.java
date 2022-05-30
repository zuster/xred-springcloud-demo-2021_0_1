package com.xred.service.config.scc.controller;

import com.xred.service.config.scc.service.IEnvService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 环境变量查询 Controller
 *
 * @author songyh
 * @date 2022/5/30 11:28 PM
 */
@RestController
@RequestMapping("/env")
public class EnvController {
    @Autowired
    private IEnvService envService;

    @GetMapping("/{key}")
    public String getEnv(@PathVariable String key) {
        return envService.customWelcome();
    }
}
