package com.xred.service.loadbalancer.myself.config;

import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

/**
 * RestTemplateConfig
 *
 * @author songyh
 * @date 2022/5/13 12:57 下午
 */
@Configuration
public class RestTemplateConfig {
    /**
     * RestTemplate 1 - Use @LoadBalanced
     * <p>使用 Spring Cloud 可以直接基于服务名进行服务调用。这是因为Spring Cloud 扩展了 RestTemplate，只需要在定义 RestTemplate Bean 时加上 @LoadBalanced 注解，就可以基于服务名进行服务调用。</p>
     *
     * @return
     */
    @Bean
    @LoadBalanced
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }

    /**
     * RestTemplate 2 - Default
     *
     * @return
     */
    @Bean
    public RestTemplate customRestTemplate() {
        return new RestTemplate();
    }
}
