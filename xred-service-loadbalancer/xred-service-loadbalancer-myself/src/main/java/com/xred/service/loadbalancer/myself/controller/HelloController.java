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
    private RestTemplate restTemplate;

    private static final String SERVICE_NAME = "xred-service-discovery-multi-provider";

    @GetMapping("/hello")
    public String hello() {
        final MyRandomServiceInstanceChooser chooser = new MyRandomServiceInstanceChooser(discoveryClient);
        final ServiceInstance instance = chooser.choose(SERVICE_NAME);
        return restTemplate.getForObject(
                "http://" + instance.getHost() + ":" + instance.getPort() + "/echo?name=loadbalance-my"
                , String.class
        );
    }

}
