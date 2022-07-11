package com.xred.service.breaker.hystrix.controller;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

/**
 * Hystrix 测试 Controller
 *
 * @author songyh
 * @date 2022/7/11 09:35
 */
@RestController
@RequestMapping("demo")
public class HystrixTestController {
    @HystrixCommand(
            commandKey = "hello",
            fallbackMethod = "fallback",
            commandProperties = {
                    @HystrixProperty(name = "execution.isolation.strategy", value = "SEMAPHORE"),
                    @HystrixProperty(name = "execution.isolation.semaphore.maxConcurrentRequests", value = "0"),
                    @HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "5000")
            }
    )
    @GetMapping("hello1")
    public String hello1() {
        return new RestTemplate().getForObject("https://httpbin.org/status/200", String.class);
    }

    @HystrixCommand(
            commandKey = "hello",
            fallbackMethod = "fallback"
    )
    @GetMapping("hello2")
    public String hello2() {
        return new RestTemplate().getForObject("https://httpbin.org/status/200", String.class);
    }

    @HystrixCommand(
            threadPoolKey = "singleThread",
            fallbackMethod = "fallback",
            threadPoolProperties = {
                    @HystrixProperty(name = "coreSize", value = "1")
            }

    )
    @GetMapping("singleThread")
    public String singleThread() {
        System.out.println("----- " + Thread.currentThread().getName());
        return new RestTemplate().getForObject("https://httpbin.org/status/200", String.class);
    }

    @HystrixCommand(
            threadPoolKey = "singleThread",
            fallbackMethod = "fallback"
    )
    @GetMapping("singleThread2")
    public String singleThread2() {
        System.out.println("----- " + Thread.currentThread().getName());
        return new RestTemplate().getForObject("https://httpbin.org/status/200", String.class);
    }

    @HystrixCommand(
            fallbackMethod = "fallback"
    )
    @GetMapping("multipleThread")
    public String multipleThread() {
        System.out.println("----- " + Thread.currentThread().getName());
        return new RestTemplate().getForObject("https://httpbin.org/status/200", String.class);
    }

    public String fallback(Throwable throwable) {
        return "Hystrix fallback : " + throwable.getMessage();
    }
}
