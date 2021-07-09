package com.hinz.rabbitmq;

import com.hinz.rabbitmq.utils.RabbitMQUtils;
import com.rabbitmq.client.Channel;

import java.util.Scanner;

/**
 * 通用生产者
 */
public class CommonProducer {
    public static void main(String[] args) {
        Channel channel = RabbitMQUtils.getChannel();
        try {
            Scanner sc = new Scanner(System.in);
            System.out.println("请输入队列名称：");
            String QUEUE_NAME = sc.next();
            channel.queueDeclare(QUEUE_NAME,false,false,false,null);
            System.out.println("请输入消息：");
            while (sc.hasNext()){
                String msg = sc.next();
                if("q".equals(msg)){
                    System.out.println("bye bye");
                    return;
                }
                channel.basicPublish("",QUEUE_NAME,null,msg.getBytes());
                System.out.println("发送消息完成："+new String(msg.getBytes())+"请输继续输入消息，或者输入q退出");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }


    }
}
