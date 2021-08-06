package com.hinz.rabbitmq.controller;

import com.hinz.rabbitmq.bean.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.nio.charset.StandardCharsets;
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
        log.info("当前时间：{},发送消息成功：{}，过期时间：{}",new Date(),msg,ttlTime);
        return "ok";
    }


    @GetMapping("sendBingds/{msg}")
    public String sendBingds(@PathVariable String msg){
        rabbitTemplate.convertAndSend("QQ","hinzzz.Aaa",msg);
        log.info("当前时间：{},发送消息成功：{}",new Date());
        return "ok";
    }



    @GetMapping("sendUser/{msg}")
    public String sendUser(@PathVariable String msg){
        rabbitTemplate.convertAndSend("QQ","hinzzz.Aaa",msg.getBytes(StandardCharsets.UTF_8)+" byte[]");
        rabbitTemplate.convertAndSend("QQ","hinzzz.Aaa",msg+" String");
        rabbitTemplate.convertAndSend("QQ","hinzzz.Aaa", User.builder().userNo("1").userName(msg).build());
        log.info("当前时间：{},发送消息成功：{}",new Date());
        return "ok";
    }
}


