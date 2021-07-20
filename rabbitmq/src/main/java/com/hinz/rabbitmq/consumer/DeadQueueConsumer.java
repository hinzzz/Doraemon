package com.hinz.rabbitmq.consumer;

import com.rabbitmq.client.Channel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * @author hinzzz
 * @date 2021/7/20 16:56
 * @desc
 */
@Slf4j
@Component
public class DeadQueueConsumer {

    @RabbitListener(queues = "QD")
    public void receiveMsg(Message msg, Channel channel){
        log.info("当前时间：{}，收到死信队列的消息：{}",new Date(),new String(msg.getBody()));
    }

}
