package com.hinz.rabbitmq.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.CustomExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

/**
 * @author hinzzz
 * @date 2021/7/22 11:04
 * @desc 延迟队列插件
 */

@Configuration
public class DelayedQueueConfig {

    public static final String DELAYED_QUEUE = "delayed_queue";
    public static final String DELAYED_EXCHANGE = "delayed_exchange";
    public static final String ROUTING_KEY = "delayed_key";


    /**
     * 自定义交换机
     * @return
     */
    @Bean("delayedExchange")
    public CustomExchange delayedExchange(){
        Map<String,Object> map  = new HashMap<>();
        map.put("x-delayed-type","direct");
        return new CustomExchange(DELAYED_EXCHANGE,"x-delayed-message",true,false,map);
    }

    @Bean("delayedQueue")
    public Queue getDelayedQueue(){
        return new Queue(DELAYED_QUEUE);
    }

    @Bean
    public Binding getDelayedQueueBindDelayedExchange(@Qualifier("delayedExchange") CustomExchange exchange,
    @Qualifier("delayedQueue") Queue queue){
        return BindingBuilder.bind(queue).to(exchange).with(ROUTING_KEY).noargs();
    }
}
