package com.hinz.rabbitmq.woker;

import com.hinz.rabbitmq.utils.RabbitMQUtils;
import com.rabbitmq.client.Channel;

import java.util.Scanner;

public class Task01 {
    private final static String QUEUE_NAME="work";
    public static void main(String[] args) {
        Channel channel = RabbitMQUtils.getChannel();
        try {
            channel.queueDeclare(QUEUE_NAME,false,false,false,null);
            System.out.print("请输入消息：");
            Scanner sc = new Scanner(System.in);
            while (sc.hasNext()){
                String msg = sc.next();
                channel.basicPublish("",QUEUE_NAME,null,msg.getBytes());
                System.out.println("发送消息完成："+new String(msg.getBytes()));
                System.out.print("请输入消息：");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
