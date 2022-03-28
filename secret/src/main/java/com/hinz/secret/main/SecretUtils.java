package com.hinz.secret.main;


import cn.hutool.core.codec.Base64;
import cn.hutool.crypto.SecureUtil;
import cn.hutool.crypto.asymmetric.KeyType;
import cn.hutool.crypto.asymmetric.Sign;
import cn.hutool.crypto.asymmetric.SignAlgorithm;
import cn.hutool.json.JSON;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.hinz.secret.bean.RequestBean;
import com.hinz.secret.bean.ResponseBean;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;

public class SecretUtils {

    private static final String publicKey="MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCyFsTX/C1HUQJHvTt6ckhAYFstCUoy6e+PsBcdFHVvD/PEKyzxzThXqitLuXWF/7NZjPwjcWW33WjRp8OWuWzyNpSMkSyCCkISB0ZlZiVhUYst8i8xCL76+gcHJT+zwuq5oEBu5Cuts5gz2J+pS4x89VaM9NYm9Zi7+yHKe/m5bQIDAQAB";


    public static void main(String[] args) {
        if(args!=null && args.length>0){
            if(args[0].equals("request")){
                System.out.println(JSONUtil.toJsonStr(request( args[1], args[2])));
            }else if(args[0].equals("response")){
                System.out.println(JSONUtil.toJsonStr(response(args[1], args[2], args[3])));
            }
        }
    }
    /*public static void main(String[] args) {
        System.out.println("\"阿萨德\" = " + "阿萨德");
        JSONObject data = new JSONObject();
        data.append("responseData",null);
        data.append("encryptData","czQgd6B5wwwaNuHEhk188Xuz4ax9xoMihHAKhpE5XcTpi04R02xRCMlqg/HxDi+UEpm1oyPrU5ZFSQJuj0Gcn74KsBdxKLAC5v4mb4vlgUWjXRgP6rWkBGwFUsLcoOIgmZIhWO42yIMJ5z257hlsjGaoOEaIzlIFbo9S08/pQsqVTnKok//xqM6dYp13t6Y7y5Hl003DTyBBPxQkpOcGX/acSqAq7cVtDA1SBUu7XWmXWRkxBLpPmwO0Uu3fqT9pTDgixd4J5/YeFTWHZrMTyknurBa2FZnJNVuUnRnF3mebz8wC27pZUK2k623xKlNZ7H5omW+SeIsYgvCFFXh4MyLFi7LHQoMe6Vk9yQhkkvVKbP94G8wtMzWzxwB963OIJA5Gapw808+xFAH+vU82ZWQ0TDZHU6mrlaiXVUYKlrvZzTNO8MXBhD5pAXYce7i/hU7aBou6ZmFpucePk0uGKoCJ7aNblnbjuZQlNN3zgjCUi1VjqSBE1TtWIm4WPUMG9ScPtEOv7kTLKSbRxQeuJdcqLjK1HqOliL3qroYLNTgugJSqwH42jm0RpW0ZpYPoSn41A0RM9YIdrSvHc0SLkPxk7HjbykGqmKLkZNpvglZCbzvBtUX2ma//u5UaDM2/jVgz7ONVz3fV6wT3GExg7SoDk4CTOjRf4WWU0xbNURHlOSmbMql2JgZJDyuaZnB3HIb8gxgl04LHSJd0OMnBWqH42Z6fzm5Tzjahij+YWZLh68Ndh3X3JNZxgt7KZWikgAEsb9t6lY/E9RSsfjiPnyRavzQrkpoO0cyJ/fqYnx6rTrIqNBJ0VHZgWc6DkxXurfmln6yDWDenMaA9v0/dK9F6Bg1kZ1mv0UkoUiM4fTw=");
        data.append("sign","RJJR/bPW7f1ALVzp/ggXC87+StLlSyC+En6dApXwijJVD4PVOL0a3VTezEoIfv9VA9wEdiPfEfkirHci59ltzQUY6wkQAykN+riNt9y9vMQH3yGCfOR7moonp5zXH/kEBMFyKxvgZrZOyFf9NJ/a83ppbSy5Zab5zmohNnxHCI4=");

        String response = response(JSONUtil.toJsonStr(data), "vZ2Vr3Gul3gW1j72AUnEag==", publicKey);
        System.out.println("response = " + response);
    }*/


    public static <T> RequestBean request( T requestBody, String publicKey) {

        // 真实上传的请求参数
        String requestBodyJson = JSONUtil.toJsonStr(requestBody);

        // 每次请求生成一个AES对称加密密钥
        SecretKey secretKey = SecureUtil.generateKey("AES");
        String baseSecretKey = Base64.encode(secretKey.getEncoded());
        String requestData = SecureUtil.aes(secretKey.getEncoded()).encryptBase64(requestBodyJson.getBytes());
        RequestBean reqestBean = RequestBean.builder()
                .data(requestData)
                .key(Base64.encode(SecureUtil.rsa(null, publicKey).encrypt(baseSecretKey, KeyType.PublicKey)))
                .sign(SecureUtil.sha256(requestData + baseSecretKey))
                .aesKey(baseSecretKey)
                .build();
        return reqestBean;
    }

    public static  String response(String responseBody, String aesKey ,String publicKey) {
        ResponseBean<?> responseBean = JSONUtil.toBean(responseBody, ResponseBean.class);

        Sign signAlgorithm = SecureUtil.sign(SignAlgorithm.SHA256withRSA, null, publicKey);
        boolean verify = signAlgorithm.verify(Base64.decode(responseBean.getEncryptData()), Base64.decode(responseBean.getSign()));
        byte[] realAseKey = Base64.decode(aesKey.getBytes());
        String decodeData = new String(SecureUtil.aes(realAseKey).decrypt(responseBean.getEncryptData()), StandardCharsets.UTF_8);
        return decodeData;
    }
}
