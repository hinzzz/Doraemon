package com.hinz.rabbitmq.msgConfirm;

import com.hinz.rabbitmq.utils.RabbitMQUtils;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.ConfirmCallback;
import java.util.concurrent.ConcurrentNavigableMap;
import java.util.concurrent.ConcurrentSkipListMap;

public class AsyncConfirm {
    public static void main(String[] args) {
        Channel channel = RabbitMQUtils.getChannel();
        try {
            channel.queueDeclare("async-confirm",false,false,false,null);
            channel.confirmSelect();

            /**
             * 线程安全有序的一个哈希表，适用于高并发的情况下
             * 1、轻松的将序号与消息关联
             * 2、轻松批量删除信息，只要传序号
             * 3、支持并发访问
             */
            ConcurrentSkipListMap<Long,String> waitConfirmMap = new ConcurrentSkipListMap<>();

            /**
             * 确认消息的一个回调
             * 1、消息序列号
             * 2、true：可以确认小于等于当前序列号的消息
             *    false：只能确认当前消息
             */

            ConfirmCallback confirmCallback = (deliveryTag,  multiple) ->{
                if(multiple){
                    //返回的是小于或等于当前序号未确认的消息 是一个map
                    ConcurrentNavigableMap<Long, String> confirmed = waitConfirmMap.headMap(deliveryTag, true);
                    //清楚部分未确认消息
                    confirmed.clear();
                }else {
                    //只删除当前序号的消息
                    waitConfirmMap.remove(deliveryTag);
                }
            };

            ConfirmCallback nackCallback = (deliveryTag,  multiple) ->{
                String msg = waitConfirmMap.get(deliveryTag);
                System.out.println("消息：" + msg + " 未被确认");
            };
            /**
             * 添加一个异步的确认监听器
             * 1、确认消息的回调
             * 2、未收到消息的回调
             */
            channel.addConfirmListener(confirmCallback,nackCallback);
            long begin = System.currentTimeMillis();
            for (int i = 0; i < 1000; i++) {
                String msg = i+"";
                /**
                 * channel.getNextPublishSeqNo() 获取下一个消息的序列号
                 * 通过序列号与消息体进行关联
                 * 全部都是未确认的消息
                 */
                waitConfirmMap.put(channel.getNextPublishSeqNo(),msg);
                channel.basicPublish("","async-confirm",null,msg.getBytes());
            }
            long end = System.currentTimeMillis();
            System.out.println("异步发布1000条消息，共消耗" + (end - begin));
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
