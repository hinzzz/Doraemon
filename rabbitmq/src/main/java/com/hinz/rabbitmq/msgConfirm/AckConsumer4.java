package com.hinz.rabbitmq.msgConfirm;

import com.hinz.rabbitmq.utils.RabbitMQUtils;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.DeliverCallback;


public class AckConsumer4 {
    public static final String TASK_QUEUE = "async-confirm";
    public static void main(String[] args) {
        Channel channel = RabbitMQUtils.getChannel();
        try {
            boolean autoAck = false;
            DeliverCallback deliverCallback = (consumerTag,  message) -> {
                /**
                 * 1、消息标记
                 * 2、false:只应答接收到的那个传递的消息
                 *    true:应答所有消息 包括传过来的消息
                 */
                System.out.println(new String(message.getBody()));
                channel.basicAck(message.getEnvelope().getDeliveryTag(),false);
            };
            channel.basicConsume(TASK_QUEUE,autoAck,deliverCallback,a -> {});
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
