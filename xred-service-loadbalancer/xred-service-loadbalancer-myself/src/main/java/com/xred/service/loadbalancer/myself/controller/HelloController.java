package com.xred.service.loadbalancer.myself.controller;

import com.xred.service.loadbalancer.myself.chooser.MyRandomServiceInstanceChooser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

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
    private RestTemplate customRestTemplate;
    @Autowired
    private RestTemplate restTemplate;

    private static final String SERVICE_NAME = "xred-service-discovery-multi-provider";

    /**
     * Test API Request 1 - 使用自定义负载均衡选择器进行服务调用。
     *
     * @return
     */
    @GetMapping("/hello")
    public String hello() {
        final MyRandomServiceInstanceChooser chooser = new MyRandomServiceInstanceChooser(discoveryClient);
        final ServiceInstance instance = chooser.choose(SERVICE_NAME);
        return customRestTemplate.getForObject(
                "http://" + instance.getHost() + ":" + instance.getPort() + "/echo?name=loadbalance-my"
                , String.class
        );
    }

    /**
     * Test API Request 2 - 直接使用 RestTemplate 根据服务名进行调用，屏蔽 ServiceInstance 的获取细节。
     *
     * @return
     */
    @GetMapping("/hello2")
    public String hello2() {
        return restTemplate.getForObject(
                "http://" + SERVICE_NAME + "/echo?name=loadbalance-my"
                , String.class
        );
    }

}
