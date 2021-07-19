package com.hinz.aop.exception;

/**
 * @author yudong
 * @date 2021/5/25
 */
public class ServiceException extends RuntimeException {
    private final int errorCode;

    public ServiceException(int errorCode, String errorMsg) {
        super(errorMsg);
        this.errorCode = errorCode;
    }

    public int getErrorCode() {
        return errorCode;
    }
}
