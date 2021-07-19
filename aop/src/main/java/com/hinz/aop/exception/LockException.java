package com.hinz.aop.exception;

/**
 * @author yudong
 * @date 2021/5/21
 */
public class LockException extends RuntimeException {
    private final int errorCode;

    public LockException(int errorCode, String errorMsg) {
        super(errorMsg);
        this.errorCode = errorCode;
    }

    public int getErrorCode() {
        return errorCode;
    }
}
