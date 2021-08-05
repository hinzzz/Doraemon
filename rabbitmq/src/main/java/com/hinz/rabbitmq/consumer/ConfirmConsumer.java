package com.hinz.rabbitmq.consumer;

import com.rabbitmq.client.Channel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

/**
 * @author hinzzz
 * @date 2021/7/29 16:03
 * @desc
 */
@Component
@Slf4j
public class ConfirmConsumer {

    @RabbitListener(queues = "confirm_queue")
    public void comsume(Message msg, Channel channel){
        log.info("消息>>>>>>>>>>{} 被消费 ",new String(msg.getBody()));
    }

}
