package com.hinz.rabbitmq.helloworld;

import com.rabbitmq.client.*;

import java.time.LocalDateTime;

public class Consumer {
    private final static String QUEUE_NAME="hello";

    public static void main(String[] args) {

        //创建连接工厂
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("www.xieguangda.top");
        factory.setPort(5672);
        factory.setUsername("admin");
        factory.setPassword("admin");

        //channel实现了自动close接口 不需要手动关闭
        try {
            Connection connection = factory.newConnection();
            Channel channel = connection.createChannel();

            DeliverCallback deliverCallback =  (consumerTag,message) ->{
                System.out.println("consumerTag = " + consumerTag);
                System.out.println("消费成功：" + new String (message.getBody())+" "+ LocalDateTime.now().toString());
            };

            CancelCallback cancelCallback = consumerTag ->{
                System.out.println("consumerTag = " + consumerTag);
            };
            channel.basicConsume(QUEUE_NAME,deliverCallback,cancelCallback);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
