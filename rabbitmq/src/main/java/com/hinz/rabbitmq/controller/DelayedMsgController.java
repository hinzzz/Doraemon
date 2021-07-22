package com.hinz.rabbitmq.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

/**
 * @author hinzzz
 * @date 2021/7/22 14:12
 * @desc
 */
@RestController
@RequestMapping("delay")
@Slf4j
public class DelayedMsgController {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @RequestMapping("sendMsg/{msg}/{ttl}")
    public Object sendMsg(@PathVariable String msg,@PathVariable Integer ttl){
        rabbitTemplate.convertAndSend("delayed_exchange","delayed_key",msg,correlationData->{
            correlationData.getMessageProperties().setDelay(ttl);
            return correlationData;
        });
        log.info("当前时间：{}，发送消息：{}",new Date(),msg);
        return "ok";
    }

}
