package com.hinz.rabbitmq.exchanges;

import com.hinz.rabbitmq.utils.RabbitMQUtils;
import com.rabbitmq.client.Channel;


public class HelloExchanges {
    public static void main(String[] args) {
        Channel channel = RabbitMQUtils.getChannel();
        try {
            channel.confirmSelect();
            channel.queueDeclare("hello-exchanges",false,false,false,null);
            channel.basicPublish("hello-exchanges","hello-exchanges",null,"hello-exchanges".getBytes());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
