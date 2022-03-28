package com.hinz.secret.bean;


import lombok.Data;


@Data
//接口公共响应实体
public class ResponseBean<T> {
    //接口返回加密数据
    private ResponseData<T> responseData;
    //接口返回加密数据
    private String encryptData;
    //使用私钥签名的sign
    private String sign;

    private ResponseBean(int code, String message, T data, boolean success) {
        this.responseData = new ResponseData<T>(code, message, data, success);
        this.encryptData = null;
        this.sign = null;
    }

    public static <T> ResponseBean<T> success() {
        return success(null, "操作成功");
    }

    public static <T> ResponseBean<T> success(T data) {
        return success(data, "操作成功");
    }

    public static <T> ResponseBean<T> success(T data, String message) {
        return new ResponseBean<T>(200, message, data, true);
    }

    public static <T> ResponseBean<T> error(String message) {
        return error(400, message, null);
    }

    public static <T> ResponseBean<T> error(int code, String message) {
        return error(code, message, null);
    }

    public static <T> ResponseBean<T> error(int code, String message, T data) {
        return new ResponseBean<T>(code, message, data, false);
    }

    public static <T> ResponseBean<T> of(boolean success) {
        return success ? success(null, "") : error("");
    }

    public static <T> ResponseBean<T> of(boolean success, String message) {
        return success ? success(null, message) : error(message);
    }

    public static <T> ResponseBean<T> of(boolean success, String message, T data) {
        return success ? success(data, message) : error(400, message, data);
    }
}
