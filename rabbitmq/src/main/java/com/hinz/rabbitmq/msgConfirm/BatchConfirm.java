package com.hinz.rabbitmq.msgConfirm;

import com.hinz.rabbitmq.utils.RabbitMQUtils;
import com.rabbitmq.client.Channel;

public class BatchConfirm {
    public static void main(String[] args) {
        Channel channel = RabbitMQUtils.getChannel();
        try {
            channel.queueDeclare("batch-confirm", false, false, false, null);
            channel.confirmSelect();
            int confirmCount = 200;
            int msgCount = 0;
            long begin = System.currentTimeMillis();
            for (int i = 0; i < 1000; i++) {
                String msg = i + "";
                channel.basicPublish("", "batch-confirm", null, msg.getBytes());
                msgCount++;
                if (msgCount == confirmCount) {
                    channel.waitForConfirms();
                    msgCount = 0;
                }
            }
            if(msgCount>0){
                channel.waitForConfirms();
            }

            long end = System.currentTimeMillis();

            System.out.println("批量共发送1000条消息，共耗时" + (end - begin));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
