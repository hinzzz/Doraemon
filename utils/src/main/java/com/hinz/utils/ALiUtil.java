package com.hinz.utils;

import java.io.File;
import java.util.Scanner;

/**
 * @author ：quanhz
 * @date ：Created in 2020/12/28 15:42
 * @Description  : 阿里云图片自动上传工具
 */
public class ALiUtil {
    public static void toUpload(){
        System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
        while (true){
            System.out.print("请输入文件路径：");
            Scanner in = new Scanner(System.in);
            String filePath = in.nextLine();
            File file = new File(filePath);
            if(MainUtils.stop(filePath)) return;
            if(!file.exists()){
                System.out.println( "文件不存在："+filePath );
                continue;
            }
            if(file.isDirectory()){
                System.out.println( "不能上传文件夹："+filePath );
            }
            try {
                System.out.println("上传 begin ======================>");
                ALiYunOssUtil.saveFile(file);
                System.out.println("阿里云链接： " + ALiYunOssUtil.genUrl(file.getName()));
                System.out.println("上传 end ======================>");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

}
