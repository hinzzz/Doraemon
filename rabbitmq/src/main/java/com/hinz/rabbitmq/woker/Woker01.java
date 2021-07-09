package com.hinz.rabbitmq.woker;

import com.hinz.rabbitmq.utils.RabbitMQUtils;
import com.rabbitmq.client.CancelCallback;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.DeliverCallback;


public class Woker01 {
    private final static String QUEUE_NAME="work";
    public static void main(String[] args) {
        Channel channel = RabbitMQUtils.getChannel();
        DeliverCallback deliverCallback =  (consumerTag, message) ->{
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("consumerTag = " + consumerTag);
            System.out.println("工作线程01：" + new String (message.getBody()));
        };

        CancelCallback cancelCallback = consumerTag ->{
            System.out.println("consumerTag = " + consumerTag);
        };
        try {
            System.out.println("工作线程01 等待消费......");
            channel.basicConsume(QUEUE_NAME,true,deliverCallback,cancelCallback);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
