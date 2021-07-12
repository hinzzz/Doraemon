package com.hinz.rabbitmq.prefetch;

import com.hinz.rabbitmq.utils.RabbitMQUtils;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.DeliverCallback;


public class PreConsumer1 {
    public static final String TASK_QUEUE = "ack";
    public static void main(String[] args) {
        Channel channel = RabbitMQUtils.getChannel();
        try {
            int prefetchCount = 1;
            channel.basicQos(prefetchCount);
            boolean autoAck = false;
            DeliverCallback deliverCallback = (consumerTag,  message) -> {
                try {
                    Thread.sleep(100000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println("消费消息："+new String(message.getBody()));
                /**
                 * 1、消息标记
                 * 2、false:只应答接收到的那个传递的消息
                 *    true:应答所有消息 包括传过来的消息
                 */
                channel.basicAck(message.getEnvelope().getDeliveryTag(),false);
            };
            channel.basicConsume(TASK_QUEUE,autoAck,deliverCallback,a -> {});
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
