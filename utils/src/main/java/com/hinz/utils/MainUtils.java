package com.hinz.utils;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

/**
 * @author ：quanhz
 * @date ：Created in 2020/11/25 11:56
 * @Description : 自定义工具类home
 */
public class MainUtils {
    private static Map<String,String> utils = new HashMap();
    static {
        utils.put("1","阿里云图片上传");
        utils.put("2","文件解密");
    }
    public static void main(String[] args) {
        System.out.println( "  _       _\n" +
                " | |     (_)\n" +
                " | |__    _   _ __    ____  ____  ____\n" +
                " | '_ \\  | | | '_ \\  |_  / |_  / |_  /\n" +
                " | | | | | | | | | |  / /   / /   / /\n" +
                " |_| |_| |_| |_| |_| /___| /___| /___|\n");
        Arrays.stream(args).forEach(System.out::println);
        MainUtils.run();

    }
    public static void run(){
        System.out.println("===========请选择工具 输入序号既可 输入q退出 ===========");
        ALiUtil.toUpload();
        System.out.println(utils);
        Scanner in = new Scanner(System.in);
        while (true){
            System.out.print("请输入序号：");
            String num=in.next();
            if (Arrays.asList("quit", "q", "bye", "exit").contains(num.toLowerCase())) {
                MainUtils.byebye();
                in.close();
                return;
            }
            if(utils.get(num)==null)continue;
            System.out.println("当前工具："+utils.get(num));
            switch (num){
                case "1":
                    ALiUtil.toUpload();
                    return;
                case "2":
                    DecUtil.dec();
                    return;
            }
        }
    }


    public static boolean stop(String command){
        if(Arrays.asList("quit", "q", "bye", "exit").contains(command.toLowerCase())){
            System.out.println("\n");
            MainUtils.run();
            return true;
        }
        return false;
    }
    public static void byebye(){
        System.out.println("\n" +
                "  _                _                \n" +
                " | |__  _   _  ___| |__  _   _  ___ \n" +
                " | '_ \\| | | |/ _ | '_ \\| | | |/ _ \\\n" +
                " | |_) | |_| |  __| |_) | |_| |  __/\n" +
                " |_.__/ \\__, |\\___|_.__/ \\__, |\\___|\n" +
                "        |___/            |___/      \n");
    }
}
