package com.xred.service.config.nacos.controller;

import com.google.gson.Gson;
import com.xred.service.config.nacos.properties.UserProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.endpoint.event.RefreshEvent;
import org.springframework.context.ApplicationContext;
import org.springframework.core.env.AbstractEnvironment;
import org.springframework.core.env.EnumerablePropertySource;
import org.springframework.core.env.MutablePropertySources;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

/**
 * Test Controller
 *
 * @author songyh
 * @date 2022/6/2 12:04 AM
 */
@RestController
@RequestMapping("/test")
public class TestController {
    @Value("${custom.welcome}")
    private String welcome;
    private final UserProperties userProperties;
    private final ApplicationContext context;

    public TestController(ApplicationContext context, UserProperties userProperties) {
        this.context = context;
        this.userProperties = userProperties;
    }

    /**
     * 获取 nacos config
     *
     * @return
     */
    @GetMapping("nacos/config/{child}")
    public String nacosConfig(@PathVariable String child) {
        return context.getEnvironment().getProperty(child);
    }

    /**
     * 获取 nacos config
     *
     * @return
     */
    @GetMapping("nacos/config/userInfo")
    public String nacosUserInfo() {
        return new Gson().toJson(userProperties);
    }

    @GetMapping("config/{child}")
    public String config(@PathVariable String child) {
        return context.getEnvironment().getProperty(child);
    }

    @GetMapping("configs")
    public String configs() {
        Map<String, Object> props = new HashMap<>();
        final MutablePropertySources sources = ((AbstractEnvironment) context.getEnvironment()).getPropertySources();
        sources.forEach(x -> {
            if (x instanceof EnumerablePropertySource) {
                EnumerablePropertySource p = (EnumerablePropertySource) x;
                for (String s : p.getPropertyNames()) {
                    props.put(s, p.getProperty(s));
                }
            }
        });
        return new Gson().toJson(props);
    }

    @GetMapping("/event")
    public String event() {
        context.publishEvent(new RefreshEvent(this, null, "test"));
        return "send RefreshEvent!";
    }
}
