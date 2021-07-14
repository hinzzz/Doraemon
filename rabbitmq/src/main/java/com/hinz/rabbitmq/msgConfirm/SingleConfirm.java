package com.hinz.rabbitmq.msgConfirm;

import com.hinz.rabbitmq.utils.RabbitMQUtils;
import com.rabbitmq.client.Channel;


public class SingleConfirm {
    public static void main(String[] args) {
        Channel channel = RabbitMQUtils.getChannel();
        try {
            channel.confirmSelect();
            channel.queueDeclare("single-confirm",false,false,false,null);
            channel.confirmSelect();
            long begin = System.currentTimeMillis();
            for (int i = 0; i < 1000; i++) {
                String msg = i+"";
                channel.basicPublish("","single-confirm",null,msg.getBytes());
                boolean confirm = channel.waitForConfirms();
                if(confirm){
                    System.out.println("消息发送成功");
                }
            }
            long end = System.currentTimeMillis();
            System.out.println("单个共发送1000条消息，共耗时" + (end - begin));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
