package com.hinz.rabbitmq.helloworld;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;


public class Producer {
    private final static String QUEUE_NAME="hello";

    public static void main(String[] args) {
        //创建连接工厂
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("47.119.181.85");
        factory.setPort(5672);
        factory.setUsername("admin");
        factory.setPassword("admin");

        //channel实现了自动close接口 不需要手动关闭
        try {
            Connection connection = factory.newConnection();
            Channel channel = connection.createChannel();
            /**
             * 创建一个队列
             * 1、队列名称
             * 2、是否持久化
             * 3、是否排他队列 ，只供一个消费者消费
             * 4、是否自动删除
             * 5、其他参数
             */
            channel.queueDeclare(QUEUE_NAME,false,false,false,null);
            /**
             * 1、交换机
             * 2、路由key
             * 3、其他参数
             * 4、消息内容
             */
            String msg = "第3条消息";
            channel.basicPublish("",QUEUE_NAME,null,msg.getBytes());
            System.out.println("发送完毕");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
