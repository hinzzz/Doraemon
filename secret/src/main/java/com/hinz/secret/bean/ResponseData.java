package com.hinz.secret.bean;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author kco1989
 * @email kco1989@qq.com
 * @create 2021-03-09 10:50
 */
@Data
@NoArgsConstructor
public class ResponseData<T> {
    //接口响应码
    private int code;
    //接口返回描述信息
    private String message;
    //接口返回明文数据
    private T data;
    //接口返回成功标识
    private boolean success;

    public ResponseData(int code, String message, T data, boolean success) {
        this.code = code;
        this.message = message;
        this.data = data;
        this.success = success;
    }

    public ResponseData(int code, String message, T data, long count, boolean success, long total) {
        this.code = code;
        this.message = message;
        this.data = data;
        this.success = success;
    }
}
