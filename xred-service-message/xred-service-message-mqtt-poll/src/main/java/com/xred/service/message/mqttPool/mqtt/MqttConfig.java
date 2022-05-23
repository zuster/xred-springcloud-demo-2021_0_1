package com.xred.service.message.mqttPool.mqtt;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * MQTT Config
 *
 * @author songyh
 * @date 2022/5/20 2:16 PM.
 */
@Configuration
@EnableConfigurationProperties(MqttClientProperties.class)
public class MqttConfig {

    private MqttClientProperties mqttClientProperties;

    @Autowired
    public void setClientProperties(MqttClientProperties mqttClientProperties) {
        this.mqttClientProperties = mqttClientProperties;
    }

    @Bean
    public MqttClientFactory getClientFactory() {
        return new MqttClientFactory(mqttClientProperties);
    }

    @Bean
    public MqttTemplate getMqttTemplate() {
        return new MqttTemplate(getClientFactory(), mqttClientProperties);
    }
}