package com.hinz.rabbitmq.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

/**
 * @author hinzzz
 * @date 2021/7/20 16:23
 * @desc
 */
@Slf4j
@RequestMapping("ttl")
@RestController
public class SendMsgController {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @GetMapping("send/{msg}")
    public String send(@PathVariable String msg){
        log.info("当前时间：{},发一条消息给两个ttl队列：{}",new Date(),msg);
        rabbitTemplate.convertAndSend("X","XA",msg);
        rabbitTemplate.convertAndSend("X","XB",msg);
        return "ok";
    }

    /**
     * 消费端设置自定义ttl
     * @param msg
     * @param ttlTime
     * @return
     */
    @GetMapping("autoSend/{msg}/{ttlTime}")
    public String autoSend(@PathVariable String msg,@PathVariable String ttlTime){
        rabbitTemplate.convertAndSend("X","XC",msg, correlationData->{
            correlationData.getMessageProperties().setExpiration(ttlTime);
            return correlationData;
        });
        return "ok";
    }
}
