package com.xred.service.config.scc.client.controller;

import com.xred.service.config.scc.client.config.UserInfo;
import com.xred.service.config.scc.client.service.IEnvDemoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.web.bind.annotation.*;

/**
 * DEMO Controller
 *
 * @author songyh
 * @date 2022/6/4 11:50 PM
 */
@RestController
@RequestMapping("/demo")
public class DemoController {
    @Autowired
    private UserInfo userInfo;
    @Autowired
    private ApplicationContext context;
    @Autowired
    private IEnvDemoService envDemoService;

    @GetMapping("/getUserInfo")
    public UserInfo getUserInfo() {
        return userInfo;
    }

    @GetMapping("/getUserId")
    public String getUserId() {
        return envDemoService.getUserId();
    }

    @GetMapping("/getGitTestId")
    public String getGitTestId() {
        return envDemoService.getGitTestId();
    }

    @GetMapping("/env/{envName}")
    private String getEnv(@PathVariable String envName) {
        return context.getEnvironment().getProperty(envName);
    }
}
