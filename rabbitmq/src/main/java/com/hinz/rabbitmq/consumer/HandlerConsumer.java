package com.hinz.rabbitmq.consumer;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;

@Slf4j
@RabbitListener(queues = "WW")
public class HandlerConsumer {

    @RabbitHandler
    public void consume1(String message) {
        System.out.println(message);
    }

    @RabbitHandler
    public void consume2(byte[] message) {
        System.out.println(new String(message));
    }

}
