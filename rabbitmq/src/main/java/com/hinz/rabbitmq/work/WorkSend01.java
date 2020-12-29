package com.hinz.rabbitmq.work;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.util.concurrent.TimeUnit;

public class WorkSend01 {
    private final static String QUEUE_NAME = "hello";

    public static void main(String[] args) throws Exception {
        // 创建连接
        ConnectionFactory factory = new ConnectionFactory();
        // 设置 RabbitMQ 的主机名
        factory.setHost("localhost");
        // 创建一个连接
        Connection connection = factory.newConnection();
        // 创建一个通道
        Channel channel = connection.createChannel();
        // 指定一个队列
        channel.queueDeclare(QUEUE_NAME, false, false, false, null);
        // 发送10个消息
        TimeUnit.SECONDS.sleep(5L);
        for (int i = 0; i < 10; i++) {  
            String message = "hinz:" + i;
            channel.basicPublish("", QUEUE_NAME, null, message.getBytes());  
            System.out.println(" [x] Sent '" + message + "'");  
        }  
        // 关闭频道和连接  
        channel.close();
        connection.close();
    }
}