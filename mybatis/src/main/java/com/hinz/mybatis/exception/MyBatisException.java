package com.hinz.mybatis.exception;

public class MyBatisException extends RuntimeException{
    private final int code;

    public MyBatisException(String message) {
        this(500, message);
    }

    public MyBatisException(int code, String message) {
        super(message);
        this.code = code;
    }
}
