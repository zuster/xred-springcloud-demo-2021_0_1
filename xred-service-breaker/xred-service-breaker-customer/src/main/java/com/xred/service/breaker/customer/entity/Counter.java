package com.xred.service.breaker.customer.entity;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * 断路器状态统计
 * <p>
 * 时间窗口的统计需要临时保存上一次调用失败的时间戳（lastTime），
 * 该时间戳需要与当前时间进行比较，确认是否超过时间窗口；
 * 当前失败次数也需要记录，该次数会与错误个数阈值进行比较，确认是否进入Open状态。
 * 该类用于记录统计相关的逻辑。
 * </p>
 *
 * @author songyh
 * @date 2022/7/9 09:59
 */
public class Counter {
    // Closed 状态进入 Open 状态的错误个数阈值
    private final int failureCount;
    // failureCount 统计时间窗口
    private final long failureTimeInterval;
    // 当前错误次数
    private final AtomicInteger currentCount;
    // 上一次调用失败的时间戳
    private long lastTime;
    // Hafl-Open 状态下成功次数
    private final AtomicInteger halfOpenSuccessCount;

    public Counter(int failureCount, long failureTimeInterval) {
        this.failureCount = failureCount;
        this.failureTimeInterval = failureTimeInterval;
        this.currentCount = new AtomicInteger(0);
        this.halfOpenSuccessCount = new AtomicInteger(0);
        this.lastTime = System.currentTimeMillis();
    }

    public synchronized int incrFailureCount() {
        long current = System.currentTimeMillis();
        // 超过时间窗口，当前失败次数重置为0
        if (current - lastTime > failureTimeInterval) {
            lastTime = current;
            currentCount.set(0);
        }
        return currentCount.getAndIncrement();
    }

    public int incrSuccessHalfOpenCount() {
        return this.halfOpenSuccessCount.incrementAndGet();
    }

    public boolean failureThresholdReached() {
        return getCurCount() >= failureCount;
    }

    public int getCurCount() {
        return currentCount.get();
    }

    public synchronized void reset() {
        halfOpenSuccessCount.set(0);
        currentCount.set(0);
    }
}
