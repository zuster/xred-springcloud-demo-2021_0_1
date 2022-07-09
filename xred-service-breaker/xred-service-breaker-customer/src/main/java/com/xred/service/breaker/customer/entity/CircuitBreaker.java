package com.xred.service.breaker.customer.entity;

import com.xred.service.breaker.customer.exception.DegradeException;

import java.util.function.Function;
import java.util.function.Supplier;

/**
 * 断路器
 *
 * @author songyh
 * @date 2022/7/9 10:27
 */
public class CircuitBreaker {
    private State state;
    private Config config;
    private Counter counter;
    private long lastOpenedTime;

    public CircuitBreaker(Config config) {
        this.counter = new Counter(config.getFailureCount(), config.getFailureTimeInterval());
        this.state = State.CLOSED;
        this.config = config;
    }

    public <T> T run(Supplier<T> toRun, Function<Throwable, T> fallback) {
        try {
            if (state == State.OPEN) {
                // 判断 half-open 是否超时
                if (halfOpenTimeout()) {
                    halfOpenHandler(toRun, fallback);
                }
                return fallback.apply(new DegradeException("degrade by circuit breaker"));
            } else if (state == State.CLOSED) {
                T result = toRun.get();
                close();
                return result;
            } else {
                return halfOpenHandler(toRun, fallback);
            }
        } catch (Exception e) {
            counter.incrFailureCount();
            if (counter.failureThresholdReached()) {
                open();
            }
            return fallback.apply(e);
        }
    }

    private <T> T halfOpenHandler(Supplier<T> toRun, Function<Throwable, T> fallback) {
        try {
            halfOpen();
            T result = toRun.get();
            final int halfOpenSuccessCount = counter.incrSuccessHalfOpenCount();
            if (halfOpenSuccessCount >= this.config.getHalfOpenSuccessCount()) {
                close();
            }
            return result;
        } catch (Exception e) {
            // Half-Open 状态发生一次错误进入 Open 状态
            open();
            return fallback.apply(new DegradeException("degrade by circuit breaker"));
        }
    }

    private boolean halfOpenTimeout() {
        return System.currentTimeMillis() - lastOpenedTime > config.getHalfOpenTimeout();
    }

    private void close() {
        counter.reset();
        state = State.CLOSED;
    }

    private void open() {
        state = State.OPEN;
        lastOpenedTime = System.currentTimeMillis();
    }

    private void halfOpen() {
        state = State.HALF_OPEN;
    }
}
