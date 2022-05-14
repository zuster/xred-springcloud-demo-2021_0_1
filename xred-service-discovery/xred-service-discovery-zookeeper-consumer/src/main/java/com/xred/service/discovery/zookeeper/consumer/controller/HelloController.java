package com.xred.service.discovery.zookeeper.consumer.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.List;

/**
 * TestController
 *
 * @author songyh
 * @date 2022/5/13 12:56 下午
 */
@RestController
public class HelloController {
    @Autowired
    private DiscoveryClient discoveryClient;
    @Autowired
    private RestTemplate restTemplate;

    private static final String SERVICE_NAME = "xred-service-discovery-zookeeper-provider";

    @GetMapping("/info")
    public String info() {
        final List<ServiceInstance> instances = discoveryClient.getInstances(SERVICE_NAME);
        StringBuilder sb = new StringBuilder();
        sb.append("All Services: " + discoveryClient.getServices() + "\n");
        sb.append(SERVICE_NAME + " instance list: \n");
        instances.forEach(instance -> {
            sb.append("[ serviceId: " + instance.getInstanceId()
                    + ", host: " + instance.getHost()
                    + ", port: " + instance.getPort() + " ]");
            sb.append("\n");
        });
        return sb.toString();
    }

    @GetMapping("/hello")
    public String hello() {
        final List<ServiceInstance> instances = discoveryClient.getInstances(SERVICE_NAME);
        final ServiceInstance serviceInstance = instances.stream().findAny().orElseThrow(
                () -> new IllegalStateException("no " + SERVICE_NAME + " instance available")
        );
        return restTemplate.getForObject(
                "http://" + serviceInstance.getHost() + ":" + serviceInstance.getPort() + "/echo?name=zookeeper"
                , String.class
        );
    }

}
