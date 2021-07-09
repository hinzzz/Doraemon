package com.hinz.rabbitmq.utils;

import com.rabbitmq.client.*;


public class RabbitMQUtils {
    public static Channel getChannel(){
        //创建连接工厂
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("www.xieguangda.top");
        factory.setPort(5672);
        factory.setUsername("admin");
        factory.setPassword("admin");

        //channel实现了自动close接口 不需要手动关闭
        try {
            Connection connection = factory.newConnection();
            return connection.createChannel();

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
