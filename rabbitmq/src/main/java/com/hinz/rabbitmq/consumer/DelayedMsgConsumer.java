package com.hinz.rabbitmq.consumer;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * @author hinzzz
 * @date 2021/7/22 14:18
 * @desc
 */
@Component
@Slf4j
public class DelayedMsgConsumer {

    @RabbitListener(queues = "delayedQueue")
    public void receiveDelayQueue(Message msg){
      log.info("当前时间：{}，接收到队列delayed_queue的消息：{}",new Date(),new String(msg.getBody()));
    }
}
