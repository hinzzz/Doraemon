package com.hinz.shiro;

import org.jasypt.util.text.BasicTextEncryptor;

public class JasyptTest {

    public static void main(String[] args) {
        BasicTextEncryptor textEncryptor = new BasicTextEncryptor();
        textEncryptor.setPassword("shiro@jasypt");
        encrypt(textEncryptor);
        // decrypt(textEncryptor,"eJa4qXxdO2WLxc/sXiI2Zw==");
    }
    private static void encrypt(BasicTextEncryptor textEncryptor) {

        String newValue = textEncryptor.encrypt("root");
        System.out.println(newValue);
    }

    private static void decrypt(BasicTextEncryptor textEncryptor, String obj) {
        String oldValue = textEncryptor.decrypt(obj);
        System.out.println(oldValue);
    }
}
