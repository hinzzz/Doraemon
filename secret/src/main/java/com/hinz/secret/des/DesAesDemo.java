package com.hinz.secret.des;

import cn.hutool.core.codec.Base64;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

/**
 *
 * @Author: quanhz
 * @CreateTime: 2022-03-17
 * @Description:
 */
public class DesAesDemo {
    public static void main(String[] args) throws Exception{
        // 原文
        String input = "hinzzz";
        // des加密必须是8位否则会报错 Exception in thread "main" java.security.InvalidKeyException: Invalid key length: 6 bytes
        //String key = "123456";
        String key = "12345678";
        // 算法
        String algorithm = "DES";

        String transformation = "DES";
        // Cipher：密码，获取加密对象
        // transformation:参数表示使用什么类型加密
        Cipher cipher = Cipher.getInstance(transformation);
        // 指定秘钥规则
        // 第一个参数表示：密钥，key的字节数组
        // 第二个参数表示：算法
        SecretKeySpec sks = new SecretKeySpec(key.getBytes(), algorithm);
        // 对加密进行初始化
        // 第一个参数：表示模式，有加密模式和解密模式
        // 第二个参数：表示秘钥规则
        cipher.init(Cipher.ENCRYPT_MODE,sks);
        // 进行加密
        byte[] bytes = cipher.doFinal(input.getBytes());
        // 打印字节，因为ascii码有负数，解析不出来，所以乱码
//        for (byte b : bytes) {
//            System.out.println(b);
//        }
        // 打印密文

        //System.out.println(new String(bytes));
        //修改 密钥 key = “12345678” ，再次运行 ，出现乱码是因为对应的字节出现负数，但负数，没有出现在 ascii 码表里面，所以出现乱码，需要配合base64进行转码
        System.out.println(Base64.encode(bytes));
    }
}