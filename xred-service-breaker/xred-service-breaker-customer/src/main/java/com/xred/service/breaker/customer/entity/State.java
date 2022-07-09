package com.xred.service.breaker.customer.entity;

/**
 * 断路器状态
 */
public enum State {
    CLOSED,
    HALF_OPEN,
    OPEN;
}
