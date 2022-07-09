package com.xred.service.breaker.customer;

import com.xred.service.breaker.customer.entity.CircuitBreaker;
import com.xred.service.breaker.customer.entity.Config;
import com.xred.service.breaker.customer.exception.DegradeException;
import org.springframework.web.client.RestTemplate;

import javax.xml.transform.Source;

/**
 * 断路器测试
 *
 * @author songyh
 * @date 2022/7/9 16:22
 */
public class CircuitBreakerTest {
    public static void main(String[] args) {
        System.out.println("--- TEST1 ---");
        test1();
        System.out.println("--- TEST2 ---");
        test2();
        System.out.println("--- TEST3 ---");
        test3();
    }

    /**
     * 正常逻辑下的run方法
     * <p>
     * 该场景最终在控制台上打印结果“run”。
     * 因为这里的 CircuitBreaker 一直处于Closed状态，我们在逻辑体内仅仅返回了 "run"。
     * </p>
     */
    private static void test1() {
        CircuitBreaker breaker = new CircuitBreaker(new Config());
        String str = breaker.run(() -> {
            return "run";
        }, t -> {
            return "callback";
        });
        System.out.println(str);
    }

    /**
     * 发生异常逻辑下的run方法
     * <p>
     * 该场景最终在控制台上打印结果“callback”。
     * 因为这个HTTP请求返回了 Response Code 为 500 的响应
     * （https：//httpbin.org/是一个对外提供 HTTP 各种操作测试的网站，这里模拟 Response Code为500的请求），
     * RestTemplate 调用发生了 HttpServerErrorException$InternalServerError异常。
     * </p>
     */
    private static void test2() {
        CircuitBreaker breaker = new CircuitBreaker(new Config());
        RestTemplate restTemplate = new RestTemplate();
        String str = breaker.run(() -> {
            return restTemplate.getForObject("https://httpbin.org/status/500", String.class);
        }, t -> {
            return "callback";
        });
        System.out.println(str);
    }

    /**
     * 触发Open状态的run方法
     * <p>
     * 该场景最终在控制台上打印结果5。
     * 因为默认情况下的失败阈值是5次，这10次请求中，前面5次都发生异常，第6～10次触发了降级，抛出的异常是DegradeException。
     * </p>
     */
    private static void test3() {
        CircuitBreaker breaker = new CircuitBreaker(new Config());
        RestTemplate restTemplate = new RestTemplate();

        int degradeCount = 0;

        for (int i = 0; i < 10; i++) {
            String str = breaker.run(() -> {
                return restTemplate.getForObject("https://httpbin.org/status/500", String.class);
            }, t -> {
                if (t instanceof DegradeException) {
                    return "degrade";
                }
                return "callback";
            });
            if (str.equals("degrade")) {
                degradeCount++;
            }
            System.out.println(i + " --- " + str);
        }
        System.out.println(degradeCount);
    }

}
