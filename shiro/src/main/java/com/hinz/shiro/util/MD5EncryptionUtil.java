package com.hinz.shiro.util;

import org.apache.shiro.crypto.hash.SimpleHash;

import java.util.UUID;

public class MD5EncryptionUtil {

    /**
     *  根据用户名随机生成盐值
     * @param username 用户名
     * @return
     */
    public static String getSalt(String username) {
        String salt = UUID.nameUUIDFromBytes(username.getBytes()).toString().replaceAll("-", "");
        return salt;
    }

    /**
     * 对原始密码进行加密
     * @param originalPassword
     * @param salt
     * @param username
     * @return
     */
    public static String encryptionPwd(String originalPassword, String salt, String username, String hashAlgorithm, int hashIteration) {
        SimpleHash newPassword = new SimpleHash(hashAlgorithm,originalPassword,username+salt,hashIteration);
        return newPassword.toString();
    }

    public static void main(String[] args) {
        String username = "zhangsan";
        String password = "123456";
        String hashAlgorithm = "md5";
        String salt = "78d92ba9477b3661bc8be4bd2e8dd8c0";
        int hashIteration = 2;
        //b8432e3a2a5adc908bd4ff22ba1f2d65
        System.out.println(encryptionPwd(password, salt, username, hashAlgorithm, hashIteration));

        username = "admin";
        password = "123456";
        salt = "8d78869f470951332959580424d4bf4f";
        System.out.println(encryptionPwd(password, salt, username, hashAlgorithm, hashIteration));
    }
}
