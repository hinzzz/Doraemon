package com.hinz.rabbitmq.exchanges.direct;

import com.hinz.rabbitmq.utils.RabbitMQUtils;
import com.rabbitmq.client.Channel;

public class Sender {
    public static final String EXCHANGE_NAME = "direct-log";

    public static void main(String[] args) {
        Channel channel = RabbitMQUtils.getChannel();
        try {
            channel.exchangeDeclare(EXCHANGE_NAME, "direct");

            channel.basicPublish(EXCHANGE_NAME, "info", null, "info".getBytes());
            channel.basicPublish(EXCHANGE_NAME, "warn", null, "warn".getBytes());
            channel.basicPublish(EXCHANGE_NAME, "error", null, "error".getBytes());
            System.out.println("消息发送成功");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
