package com.hinz.rabbitmq.deadqueue.ttl;

import com.hinz.rabbitmq.utils.RabbitMQUtils;
import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Channel;

import java.nio.charset.StandardCharsets;


public class MsgTTLProd {
    public static void main(String[] args) {
        Channel channel = RabbitMQUtils.getChannel();
        try {
            channel.exchangeDeclare("msg-ttl", BuiltinExchangeType.DIRECT);
            channel.queueDeclare("msg-ttl", true, false, false, null);
            channel.queueBind("msg-ttl", "msg-ttl", "msg-ttl");
            //给消息单独设置属性，链式编程（构建器模式，也是一种设计模式）
            AMQP.BasicProperties properties = new AMQP.BasicProperties.Builder()
                    .deliveryMode(2) //持久化消息
                    .contentEncoding("UTF-8") //消息编码
                    .expiration("10000") //TTL消息，10秒过期
                    .build();

            channel.basicPublish("msg-ttl", "msg-ttl", properties, "hello message ttl".getBytes(StandardCharsets.UTF_8));
            System.out.println("消息发送完毕-->");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
