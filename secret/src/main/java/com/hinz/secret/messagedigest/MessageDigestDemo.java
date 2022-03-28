package com.hinz.secret.messagedigest;

import cn.hutool.core.codec.Base64;

import java.security.MessageDigest;

/**
 * 消息摘要
 */
public class MessageDigestDemo {
    public static void main(String[] args) throws Exception{
        // 原文
        String input = "aa";
        // 算法
        String algorithm = "MD5";
        // 获取数字摘要对象
        MessageDigest messageDigest = MessageDigest.getInstance(algorithm);
        // 获取消息数字摘要的字节数组
        byte[] digest = messageDigest.digest(input.getBytes());
        //System.out.println(new String(digest)); 乱码

        // base64编码  QSS8CpM1wn8IbyS6IHpJEg== 与在线md5的结果不一致4124bc0a9335c27f086f24ba207a4912
        //System.out.println(Base64.encode(digest));
        //消息摘要使用的是16进制
        // 创建对象用来拼接
        StringBuilder sb = new StringBuilder();

        for (byte b : digest) {
            // 转成 16进制
            String s = Integer.toHexString(b & 0xff);
            //System.out.println(s);
            if (s.length() == 1){
                // 如果生成的字符只有一个，前面补0
                s = "0"+s;
            }
            sb.append(s);
        }
        System.out.println(sb.toString());
    }



}
