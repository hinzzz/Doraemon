package com.hinz.rabbitmq.confirm;

import com.hinz.rabbitmq.utils.RabbitMQUtils;
import com.rabbitmq.client.Channel;

import java.io.IOException;

public class SingleConfirm {
    public static void main(String[] args) {
        Channel channel = RabbitMQUtils.getChannel();
        try {
            channel.confirmSelect();
            //channel.queueDeclare("single-confirm",)
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
