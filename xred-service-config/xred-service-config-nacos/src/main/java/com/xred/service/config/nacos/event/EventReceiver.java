package com.xred.service.config.nacos.event;

import org.checkerframework.checker.compilermsgs.qual.CompilerMessageKey;
import org.springframework.cloud.context.environment.EnvironmentChangeEvent;
import org.springframework.context.ApplicationListener;

/**
 * 事件接收器
 *
 * @author songyh
 * @date 2022/6/1 9:14 AM
 */
@CompilerMessageKey
public class EventReceiver implements ApplicationListener<EnvironmentChangeEvent> {

    @Override
    public void onApplicationEvent(EnvironmentChangeEvent event) {
        System.out.println(event.getKeys());
    }
}
