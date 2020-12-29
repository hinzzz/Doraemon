package com.hinz.utils;

import org.apache.commons.io.FileUtils;

import java.io.*;
import java.util.Random;
import java.util.Scanner;

/**
 *
 * @author ：quanhz
 * @date ：Created in 2020/11/25 14:23
 * @Description : 文件解密
 */
public class DecUtil {
    public static void dec(){
        System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
        while (true){
            System.out.print("请输入文件或者文件夹路径：");
            Scanner in = new Scanner(System.in);
            String filePath = in.nextLine();
            File file = new File(filePath);
            if(MainUtils.stop(filePath)) return;
            if(!file.exists()){
                System.out.println( "文件不存在："+filePath );
                continue;
            }
            try {
                System.out.println("解密 begin ======================>");
                long begin = System.currentTimeMillis();
                File decFile = File.createTempFile("dec" ,""+ new Random().nextInt());
                transfer(file,decFile);
                decFile.delete();
                System.out.println("解密 end ======================>"+"\"总耗时\" = " + (System.currentTimeMillis()-begin));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public static void  transfer(File file ,File decFile) throws Exception{
        if(file.isDirectory()){
            File[] files = file.listFiles();
            for (File srcFile : files) {
                if(srcFile.isDirectory()){
                    transfer(srcFile,decFile);
                    continue;
                }
                System.out.println("当前解密文件 " + srcFile.getAbsoluteFile());
                decFile.createNewFile();
                FileUtils.copyFile(srcFile,decFile);
                srcFile.delete();
                File newFile = new File(srcFile.getAbsoluteFile().getAbsolutePath());
                FileUtils.copyFile(decFile,newFile);
            }
        }else{
            System.out.println("当前解密文件 " +file.getAbsoluteFile());
            decFile.createNewFile();
            FileUtils.copyFile(file,decFile);
            file.delete();
            File newFile = new File(file.getAbsoluteFile().getAbsolutePath());
            newFile.createNewFile();
            FileUtils.copyFile(decFile,newFile);
        }
    }
}
