package com.hinz.rabbitmq.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.PostConstruct;

@Slf4j
@RestController
public class MsgProducer implements RabbitTemplate.ConfirmCallback, RabbitTemplate.ReturnCallback {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    //rabbitTemplate注入之后设置该值
    @PostConstruct
    private void init(){
        rabbitTemplate.setConfirmCallback(this::confirm);
        /**
         * true：交换机无法将消息进行路由时，会将该消息返回给生产者
         * false：如果发现消息无法路由 直接丢弃
         */
        rabbitTemplate.setMandatory(true);
        rabbitTemplate.setReturnCallback(this::returnedMessage);
    }

    @GetMapping("sendMsg/{msg}")
    public void sendMsg(@PathVariable String msg){
        //让消息绑定一个id值
        CorrelationData correlationData = new CorrelationData("111");
        rabbitTemplate.convertAndSend("confirm.exchange","key1",msg+"key1",correlationData);
        log.info("发送消息id：{},内容：{}",correlationData.getId(),msg+"key1");

        CorrelationData correlationData2 = new CorrelationData("222");
        rabbitTemplate.convertAndSend("confirm.exchange","key2",msg+"key2",correlationData2);
        log.info("发送消息id：{},内容：{}",correlationData2.getId(),msg+"key2");
    }

    @Override
    public void returnedMessage(Message message, int replyCode, String replyText, String exchange, String routingKey) {
        log.info("消息：{}被退回，原因是：{}，交换机是：{}，routingKey是：{}",new String(message.getBody()),replyText,exchange,routingKey);
    }

    @Override
    public void confirm(CorrelationData correlationData, boolean ack, String cause) {
        String id = correlationData.getId();
        if(ack){
            log.info("交换机收到消息，id：{}",id);
        }else{
            log.info("交换机未收到消息id：{}，原因是：{}",id,cause);
        }
    }
}
