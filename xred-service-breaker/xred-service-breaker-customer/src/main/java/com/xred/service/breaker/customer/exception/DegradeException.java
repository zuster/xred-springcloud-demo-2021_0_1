package com.xred.service.breaker.customer.exception;

/**
 * TODO
 *
 * @author songyh
 * @date 2022/7/9 11:54
 */
public class DegradeException extends RuntimeException {
    public DegradeException() {
    }

    public DegradeException(String message) {
        super(message);
    }
}
