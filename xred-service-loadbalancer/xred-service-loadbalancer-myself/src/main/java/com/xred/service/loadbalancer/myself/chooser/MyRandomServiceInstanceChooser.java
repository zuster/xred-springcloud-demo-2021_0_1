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

    /**
     * 根据服务名选择服务实例
     * <p>先使用 DiscoveryClient 基于服务名获取这个服务对应的所有 ServiceInstance 集合，然后根据负载均衡算法从这个集合中得到一个 ServiceInstance。</p>
     * <p>发起请求时，基于获取的 ServiceInstance 里的 IP 和 端口 使用RestTemplate发起 HTTP调用。</p>
     *
     * @param serviceId The service ID to look up the LoadBalancer.
     * @return
     */
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
