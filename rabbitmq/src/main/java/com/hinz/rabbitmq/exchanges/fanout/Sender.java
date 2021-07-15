package com.hinz.rabbitmq.exchanges.fanout;

import com.hinz.rabbitmq.utils.RabbitMQUtils;
import com.rabbitmq.client.Channel;

import java.util.Scanner;

public class Sender {
    public static final String EXCHANGE_NAME = "log";
    public static void main(String[] args) {
        Channel channel = RabbitMQUtils.getChannel();
        try {
            channel.exchangeDeclare(EXCHANGE_NAME,"fanout");
            Scanner scanner = new Scanner(System.in);
            System.out.println("请输入消息");
            while (scanner.hasNext()){
                String msg = scanner.next();
                channel.basicPublish(EXCHANGE_NAME,"",null,msg.getBytes());
                System.out.println("消息发送成功：" + msg);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
