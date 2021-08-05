package com.hinz.rabbitmq.config;

import org.springframework.amqp.core.*;
import org.springframework.beans.factory.annotation.Qualifier;
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
    public static final String BACKUP_EXCHANGE = "backup_exchange";
    public static final String BACKUP_QUEUE = "backup_queue";
    public static final String WARNING_QUEUE = "warning_queue";

    @Bean("confirm_queue")
    public Queue getConfirmQueue() {
        return QueueBuilder.durable(CONFIRM_QUEUE).build();
    }

    @Bean("backup_queue")
    public Queue getBackupQueue() {
        return QueueBuilder.durable(BACKUP_QUEUE).build();
    }

    @Bean("warning_queue")
    public Queue getWarningQueue() {
        return QueueBuilder.durable(WARNING_QUEUE).build();
    }

    @Bean("backup_exchange")
    public FanoutExchange getBackupExchange(){
        return new FanoutExchange(BACKUP_EXCHANGE);
    }

    @Bean
    public Binding getConfirmQueueExchange(@Qualifier("confirm_queue")Queue queue,
                                           @Qualifier("confirm_exchange")DirectExchange exchange){
        return BindingBuilder.bind(queue).to(exchange).with("key1");
    }

    @Bean("confirm_exchange")
    public DirectExchange getConfirmExchange(){
        ExchangeBuilder exchangeBuilder = ExchangeBuilder.directExchange(CONFIRM_EXCHANGE)
                .durable(true)
                .withArgument("alternate-exchange", BACKUP_EXCHANGE);
        return exchangeBuilder.build();
    }



   /* @Bean
    public Binding getConfirmQueueExchange3(@Qualifier("confirm_queue")Queue queue,
                                            @Qualifier("confirm_exchange")DirectExchange exchange){
        return BindingBuilder.bind(queue).to(exchange).with("key3");
    }*/

    @Bean
    public Binding warningBind(@Qualifier("warning_queue")Queue queue,
                                            @Qualifier("backup_exchange")FanoutExchange exchange){
        return BindingBuilder.bind(queue).to(exchange);
    }

    @Bean
    public Binding backupBind(@Qualifier("backup_queue")Queue queue,
                               @Qualifier("backup_exchange")FanoutExchange exchange){
        return BindingBuilder.bind(queue).to(exchange);
    }

}
