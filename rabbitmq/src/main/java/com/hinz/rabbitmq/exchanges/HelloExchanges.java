package com.hinz.rabbitmq.exchanges;

import com.hinz.rabbitmq.utils.RabbitMQUtils;
import com.rabbitmq.client.Channel;


public class HelloExchanges {
    public static void main(String[] args) {
        Channel channel = RabbitMQUtils.getChannel();
        try {
            String queueName = channel.queueDeclare().getQueue();
            channel.queueBind(queueName,"",queueName);
            channel.basicPublish("","hello-exchanges",null,"hello-exchanges".getBytes());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
