package com.hinz.rabbitmq.consumer;

import com.hinz.rabbitmq.bean.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.*;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import java.util.Map;

//@Component
@Slf4j
/**
 * 通过 @RabbitListener 的 bindings 属性声明 Binding（若 RabbitMQ 中不存在该绑定所需要的 Queue、Exchange、RouteKey 则自动创建，若存在则抛出异常）
 * payload是一种以JSON格式进行数据传输的一种方式。
 */
@RabbitListener(bindings = @QueueBinding(
        exchange = @Exchange(value = "QQ", durable = "true", type = "topic"),
        value = @Queue(value = "WW", durable = "true"),
        key = "hinzzz.#"
))
public class BindingsConsumer {

    @RabbitHandler
    public void consume( Message msg) {
        log.info("接收的参数为字符串类型：{}",msg);
    }

    @RabbitHandler
    public void consume(@Payload String msg) {
        log.info("接收的参数为字符串类型：{}",msg);
    }
    @RabbitHandler
    public void consume(@Payload User user) {
        log.info("接收的参数为对象：{}",user);
    }

}
