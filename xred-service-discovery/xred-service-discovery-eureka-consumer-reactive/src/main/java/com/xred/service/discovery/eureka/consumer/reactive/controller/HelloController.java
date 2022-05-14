package com.xred.service.discovery.eureka.consumer.reactive.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.ReactiveDiscoveryClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * TestController
 *
 * @author songyh
 * @date 2022/5/13 12:56 下午
 */
@RestController
public class HelloController {
    @Autowired
    private ReactiveDiscoveryClient discoveryClient;
    @Autowired
    private RestTemplate restTemplate;

    private static final String SERVICE_NAME = "xred-service-discovery-eureka-provider";

    @GetMapping("/info")
    public Flux<String> info() {
        final Flux<ServiceInstance> instances = discoveryClient.getInstances(SERVICE_NAME);
        return instances.map(instance ->
                "[ serviceId: " + instance.getInstanceId()
                        + ", host: " + instance.getHost()
                        + ", port: " + instance.getPort() + " ]"
        );
    }

    @GetMapping("/hello")
    public Mono<String> hello() {
        final Flux<ServiceInstance> instances = discoveryClient.getInstances(SERVICE_NAME);
        final ServiceInstance serviceInstance = instances.toStream().findAny().orElseThrow(
                () -> new IllegalStateException("no " + SERVICE_NAME + " instance available")
        );
        return WebClient.create("http://" + serviceInstance.getHost() + ":" + serviceInstance.getPort())
                .get()
                .uri("/echo?name=eurekaReactive")
                .retrieve()
                .bodyToMono(String.class);
    }

}
