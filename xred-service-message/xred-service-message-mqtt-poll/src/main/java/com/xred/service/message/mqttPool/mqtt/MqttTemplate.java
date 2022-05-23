package com.xred.service.message.mqttPool.mqtt;

import io.netty.handler.codec.mqtt.MqttQoS;
import io.vertx.core.buffer.Buffer;
import io.vertx.mqtt.MqttClient;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.pool2.impl.GenericObjectPool;
import org.apache.commons.pool2.impl.GenericObjectPoolConfig;

/**
 * MQTT Template
 *
 * @author songyh
 * @date 2022/5/20 2:20 PM.
 */
@Slf4j
public class MqttTemplate {

    private GenericObjectPool<MqttClient> clientPool;

    public MqttTemplate() {
    }

    public MqttTemplate(MqttClientFactory factory) {
        GenericObjectPoolConfig<MqttClient> config = new GenericObjectPoolConfig<>();
        config.setMinIdle(1);
        config.setBlockWhenExhausted(true);
        // 一定要打开,因为创建连接客户端是异步的,需要在获取的使用对客户端进行判断
        config.setTestOnBorrow(true);
        config.setTestOnReturn(true);
        config.setTestWhileIdle(true);
        //一定要关闭jmx，不然springboot启动会报已经注册了某个jmx的错误
        config.setJmxEnabled(false);
        this.clientPool = new GenericObjectPool<>(factory, config);
        //这里可以做一些初始化连接
    }

    public MqttTemplate(MqttClientFactory factory, MqttClientProperties properties) {
        GenericObjectPoolConfig<MqttClient> config = new GenericObjectPoolConfig<>();
        config.setMinIdle(properties.getMinIdle());
        config.setBlockWhenExhausted(properties.isBlockWhenExhausted());
        // 一定要打开,因为创建连接客户端是异步的,需要在获取的使用对客户端进行判断
        config.setTestOnBorrow(properties.isTestOnBorrow());
        config.setTestOnReturn(properties.isTestOnReturn());
        config.setTestWhileIdle(properties.isTestWhileIdle());
        config.setMaxIdle(properties.getMaxIdle());
        config.setMaxTotal(properties.getMaxTotal());
        //一定要关闭jmx，不然springboot启动会报已经注册了某个jmx的错误
        config.setJmxEnabled(properties.isJmxEnabled());
        this.clientPool = new GenericObjectPool<>(factory, config);
        //这里可以做一些初始化连接
    }

    public boolean publish(String topic, Buffer payload, MqttQoS qosLevel, boolean isDup, boolean isRetain) {
        try {
            MqttClient client = clientPool.borrowObject();
            if (client.isConnected()) {
                log.info("{}获取连接成功", log.getName());
                client.publish(topic, payload, qosLevel, isDup, isRetain, r -> {
                    log.info("{}消息推送成功", log.getName());
                    // 归还客户端
                    clientPool.returnObject(client);
                });
                return true;
            } else {
                log.error("{}获取的客户端是断开的！", log.getName());
            }
        } catch (Exception e) {
            log.error("{}获取连接失败:{}", log.getName(), e.getMessage());
        }
        return false;
    }
}