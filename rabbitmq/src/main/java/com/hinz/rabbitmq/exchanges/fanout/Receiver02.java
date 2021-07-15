package com.hinz.rabbitmq.exchanges.fanout;

import com.hinz.rabbitmq.utils.RabbitMQUtils;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.DeliverCallback;


public class Receiver02 {
    public static final String EXCHANGE_NAME = "log";
    public static void main(String[] args) {
        Channel channel = RabbitMQUtils.getChannel();
        try {
            String queueName = channel.queueDeclare().getQueue();
            channel.exchangeDeclare(EXCHANGE_NAME,"fanout");
            channel.queueBind(queueName,EXCHANGE_NAME,"");
            System.out.println("等待接收到消息>>>>>>>>>>");
            DeliverCallback deliverCallback = (consumerTag,  message)->{
                System.out.println("Receiver02接收到消息：" + new String(message.getBody()));
            };
            channel.basicConsume(queueName,deliverCallback,(x,y)->{});
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
