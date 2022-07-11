package com.xred.service.breaker.sentinel;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.core.AutoConfigureCache;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cloud.client.circuitbreaker.CircuitBreakerFactory;

/**
 * Sentinel TEST
 *
 * @author songyh
 * @date 2022/7/10 17:39
 */
@SpringBootTest
public class SentinelTest {

    @Autowired
    private CircuitBreakerFactory circuitBreakerFactory;



}
