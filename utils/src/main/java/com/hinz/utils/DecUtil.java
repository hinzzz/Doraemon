package com.hinz.utils;

import org.apache.commons.io.FileUtils;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

/**
 *
 * @author ：quanhz
 * @date ：Created in 2020/11/25 14:23
 * @Description : 文件解密
 */
public class DecUtil {
    public static void main(String[] args) {
        File file = new File("D:\\kibana-7.10.1-windows-x86_64");
        List<String> result = new ArrayList<>();
        List<String> files = getTransferFiles(file, result);
        transfer(files);
    }
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
                List<String> result = new ArrayList<>();
                List<String> files = getTransferFiles(file,result);
                System.out.println("files.size() = " + files.size());
                transfer(files);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private static void transfer(List<String> files) {
        if(files==null || files.size()<=0)return;
        final int term = 500;
        int threadCount = files.size() / term +1;
        System.out.println("启动 " + threadCount + " 个线程");

        for ( int i = 0; i < threadCount; i++) {
            final int threadIndex = i;
            new Thread(() -> {
                try {
                    int index = term * threadIndex;
                    System.out.println("解密 begin ======================>"+index);
                    long begin = System.currentTimeMillis();
                    File decFile = File.createTempFile("dec" ,""+ new Random().nextInt());
                    for(;index<files.size();index++){
                        File file = new File(files.get(index));
                        decFile.createNewFile();
                        FileUtils.copyFile(file,decFile);
                        file.delete();
                        File newFile = new File(file.getAbsoluteFile().getAbsolutePath());
                        newFile.createNewFile();
                        FileUtils.copyFile(decFile,newFile);
                    }
                    decFile.delete();
                    System.out.println(index + " 解密 end ======================>"+"\"总耗时\" = " + (System.currentTimeMillis()-begin));
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }).start();
        }

    }

    private static List<String> getTransferFiles(File file,List<String> result) {
        if(file.isDirectory()){
            File[] files = file.listFiles();
            for (File srcFile : files) {
                if(srcFile.isDirectory()){
                    getTransferFiles(srcFile,result);
                }else{
                    result.add(srcFile.getAbsolutePath());
                }
            }
        }else{
            result.add(file.getAbsolutePath());
        }
        return result;
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
                FileUtils.copyFile(srcFile,decFile);
                srcFile.delete();
                File newFile = new File(srcFile.getAbsoluteFile().getAbsolutePath());
                FileUtils.copyFile(decFile,newFile);
            }
        }else{
            System.out.println("当前解密文件 " +file.getAbsoluteFile());
            FileUtils.copyFile(file,decFile);
            file.delete();
            File newFile = new File(file.getAbsoluteFile().getAbsolutePath());
            newFile.createNewFile();
            FileUtils.copyFile(decFile,newFile);
        }
    }
}
