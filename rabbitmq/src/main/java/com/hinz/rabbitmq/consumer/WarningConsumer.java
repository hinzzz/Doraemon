package com.hinz.rabbitmq.consumer;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

/**
 * @author hinzzz
 * @date 2021/8/4 10:27
 * @desc
 */
@Component
@Slf4j
public class WarningConsumer {

    @RabbitListener(queues = "warning_queue")
    public void consumer(Message msg){
        log.info(">>>>>>>>>>>>>>>>>>>>>>>发现不可路由信息：{}",new String(msg.getBody()));
    }
}
