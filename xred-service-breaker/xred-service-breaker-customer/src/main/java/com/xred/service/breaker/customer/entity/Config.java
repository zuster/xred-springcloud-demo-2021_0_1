package com.xred.service.breaker.customer.entity;

/**
 * 断路器配置
 *
 * @author songyh
 * @date 2022/7/9 09:15
 */
public class Config {
    /**
     * Closed 状态进入 Open 状态的错误个数阈值
     */
    private final int failureCount = 5;
    /**
     * failureCount 统计时间窗口
     */
    private final long failureTimeInterval = 2 * 1000;
    /**
     * Open 进入 Half-Open 状态的超时时间
     */
    private final int halfOpenTimeout = 5 * 1000;

    /**
     * Half-Open 进入 Open 状态的成功个数阈值
     */
    private final int halfOpenSuccessCount = 2;

    public int getFailureCount() {
        return failureCount;
    }

    public long getFailureTimeInterval() {
        return failureTimeInterval;
    }

    public int getHalfOpenTimeout() {
        return halfOpenTimeout;
    }

    public int getHalfOpenSuccessCount() {
        return halfOpenSuccessCount;
    }
}
