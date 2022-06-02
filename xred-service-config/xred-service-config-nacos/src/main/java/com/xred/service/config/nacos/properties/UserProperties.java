package com.xred.service.config.nacos.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * TEST @ConfigurationProperties
 *
 * @author songyh
 * @date 2022/6/2 7:52 PM
 */
@Component
@ConfigurationProperties(prefix = "user")
public class UserProperties {
    private String id;
    private String username;

    public UserProperties() {
    }

    public UserProperties(String id, String username) {
        this.id = id;
        this.username = username;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
