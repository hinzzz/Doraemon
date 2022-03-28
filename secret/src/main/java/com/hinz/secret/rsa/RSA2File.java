package com.hinz.secret.rsa;

import cn.hutool.core.codec.Base64;
import cn.hutool.core.io.FileUtil;

import javax.crypto.Cipher;
import java.io.File;
import java.nio.charset.Charset;
import java.security.*;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;

public class RSA2File {
    public static void main(String[] args) throws Exception {
        String input = "即见未来 为何不拜";
        // 加密算法
        String algorithm = "RSA";

        //生成密钥对并保存在本地文件中
        generateKeyToFile(algorithm, "secret/a.pub", "secret/a.pri");

        //加密
//        String s = encryptRSA(algorithm, privateKey, input);
        // 解密
//        String s1 = decryptRSA(algorithm, publicKey, s);
//        System.out.println(s1);


        PrivateKey privateKey = getPrivateKey("secret/a.pri", algorithm);
        PublicKey publicKey = getPublicKey("secret/a.pub", algorithm);

        String s = encryptRSA(algorithm, privateKey, input);
        String s1 = decryptRSA(algorithm, publicKey, s);
        System.out.println("s1"+s1);

    }

    public static PublicKey getPublicKey(String publicPath, String algorithm) throws Exception {
        // 将文件内容转为字符串
        String publicKeyString = FileUtil.readString(new File(publicPath), Charset.defaultCharset());
        // 获取密钥工厂
        KeyFactory keyFactory = KeyFactory.getInstance(algorithm);
        // 构建密钥规范 进行Base64解码
        X509EncodedKeySpec spec = new X509EncodedKeySpec(Base64.decode(publicKeyString));
        // 生成公钥
        return keyFactory.generatePublic(spec);
    }

    public static PrivateKey getPrivateKey(String priPath, String algorithm) throws Exception {
        // 将文件内容转为字符串
        String privateKeyString = FileUtil.readString(new File(priPath), Charset.defaultCharset());
        // 获取密钥工厂
        KeyFactory keyFactory = KeyFactory.getInstance(algorithm);
        // 构建密钥规范 进行Base64解码
        PKCS8EncodedKeySpec spec = new PKCS8EncodedKeySpec(Base64.decode(privateKeyString));
        // 生成私钥
        return keyFactory.generatePrivate(spec);
    }

    /**
     * 生成密钥对并保存在本地文件中
     *
     * @param algorithm : 算法
     * @param pubPath   : 公钥保存路径
     * @param priPath   : 私钥保存路径
     * @throws Exception
     */
    private static void generateKeyToFile(String algorithm, String pubPath, String priPath) throws Exception {
        // 获取密钥对生成器
        KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance(algorithm);
        // 获取密钥对
        KeyPair keyPair = keyPairGenerator.generateKeyPair();
        // 获取公钥
        PublicKey publicKey = keyPair.getPublic();
        // 获取私钥
        PrivateKey privateKey = keyPair.getPrivate();
        // 获取byte数组
        byte[] publicKeyEncoded = publicKey.getEncoded();
        byte[] privateKeyEncoded = privateKey.getEncoded();
        // 进行Base64编码
        String publicKeyString = Base64.encode(publicKeyEncoded);
        String privateKeyString = Base64.encode(privateKeyEncoded);
        // 保存文件
        File pubFile = new File(pubPath);
        File priFile = new File(priPath);
        FileUtil.writeBytes(publicKeyString.getBytes(), pubFile);
        FileUtil.writeBytes(privateKeyString.getBytes(), priFile);
        System.out.println("pubFile.exists() = " + pubFile.getAbsolutePath());
    }

    /**
     * 解密数据
     *
     * @param algorithm : 算法
     * @param encrypted : 密文
     * @param key       : 密钥
     * @return : 原文
     * @throws Exception
     */
    public static String decryptRSA(String algorithm, Key key, String encrypted) throws Exception {
        // 创建加密对象
        // 参数表示加密算法
        Cipher cipher = Cipher.getInstance(algorithm);
        // 私钥进行解密
        cipher.init(Cipher.DECRYPT_MODE, key);
        // 由于密文进行了Base64编码, 在这里需要进行解码
        byte[] decode = Base64.decode(encrypted);
        // 对密文进行解密，不需要使用base64，因为原文不会乱码
        byte[] bytes1 = cipher.doFinal(decode);
        System.out.println(new String(bytes1));
        return new String(bytes1);

    }

    /**
     * 使用密钥加密数据
     *
     * @param algorithm : 算法
     * @param input     : 原文
     * @param key       : 密钥
     * @return : 密文
     * @throws Exception
     */
    public static String encryptRSA(String algorithm, Key key, String input) throws Exception {
        // 创建加密对象
        // 参数表示加密算法
        Cipher cipher = Cipher.getInstance(algorithm);
        // 初始化加密
        // 第一个参数:加密的模式
        // 第二个参数：使用私钥进行加密
        cipher.init(Cipher.ENCRYPT_MODE, key);
        // 私钥加密
        byte[] bytes = cipher.doFinal(input.getBytes());
        // 对密文进行Base64编码
        System.out.println(Base64.encode(bytes));
        return Base64.encode(bytes);
    }
}
