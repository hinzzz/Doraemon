package com.hinz.rabbitmq.consumer;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
@Slf4j
public class BindingsConsumer {


    /**
     * 通过 @RabbitListener 的 bindings 属性声明 Binding（若 RabbitMQ 中不存在该绑定所需要的 Queue、Exchange、RouteKey 则自动创建，若存在则抛出异常）
     * payload是一种以JSON格式进行数据传输的一种方式。
     * @param msg
     */
    @RabbitListener(bindings = @QueueBinding(
            exchange = @Exchange(value = "QQ", durable = "true", type = "topic"),
            value = @Queue(value = "WW", durable = "true"),
            key = "hinzzz.#"
    ))
    public void consume(@Payload String msg) {
        log.info("消费消息>>>>"+msg);
    }
    /*public void consume(Message msg) {
        log.info("消费消息>>>>"+new String(msg.getBody()));
    }*/


}
