package com.hinz.rabbitmq.config;

import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.QueueBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

/**
 * @author hinzzz
 * @date 2021/7/22 15:18
 * @desc
 */
@Component
public class ConfirmQueueConfig {
    public static final String CONFIRM_QUEUE = "confirm_queue";
    public static final String CONFIRM_EXCHANGE = "confirm_exchange";

    @Bean("confirm_queue")
    public Queue getConfirmQueue() {
        return QueueBuilder.durable(CONFIRM_QUEUE).build();
    }

    @Bean("confirm_exchange")
    public DirectExchange getConfirmExchange(){
        return new DirectExchange(CONFIRM_EXCHANGE);
    }




}
