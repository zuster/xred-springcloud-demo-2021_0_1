package com.xred.service.loadbalancer.myself.chooser;

import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.cloud.client.loadbalancer.Request;
import org.springframework.cloud.client.loadbalancer.ServiceInstanceChooser;

import java.util.List;
import java.util.Random;

/**
 * 自定义随机算法负载均衡器
 *
 * @author songyh
 * @date 2022/5/16 7:48 下午
 */
public class MyRandomServiceInstanceChooser implements ServiceInstanceChooser {

    private final DiscoveryClient discoveryClient;

    private final Random random;

    public MyRandomServiceInstanceChooser(DiscoveryClient discoveryClient) {
        this.discoveryClient = discoveryClient;
        this.random = new Random();
    }

    @Override
    public ServiceInstance choose(String serviceId) {
        final List<ServiceInstance> instances = discoveryClient.getInstances(serviceId);
        return instances.get(random.nextInt(instances.size()));
    }

    @Override
    public <T> ServiceInstance choose(String serviceId, Request<T> request) {
        return null;
    }
}
