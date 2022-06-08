package com.xred.service.config.scc.client.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * DEMO - ConfigurationProperties- UserInfo
 *
 * @author songyh
 * @date 2022/6/4 11:48 PM
 */
@Data
@Component
@ConfigurationProperties(prefix = "custom.user")
public class UserInfo {
    private String id;
    private String name;
}
