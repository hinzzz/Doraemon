package com.hinz.secret.bean;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
/** 注解的作用是序列化json时，如果是null对象，key也会消失 */
//@JsonInclude(JsonInclude.Include.NON_NULL)
public class RequestBean {
    // sign = sha1(data+对称密钥明文)
    private String sign;
    // aes对称加密(请求参数)
    private String data;
    // RSA公钥加密(对称密钥)
    private String key;
    private String aesKey;
}
