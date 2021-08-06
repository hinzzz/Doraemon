package com.hinz.rabbitmq.config;


import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.*;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


import java.util.HashMap;
import java.util.Map;

@Configuration
@Slf4j
public class RabbitMQConfig {

    public static final String X_EXCHANGE = "X";
    public static final String QUEUE_A = "QA";
    public static final String QUEUE_B = "QB";
    public static final String QUEUE_C = "QC";

    public static final String DEAD_EXCHANGE = "Y";
    public static final String DEAD_QUEUE = "QD";


    @Bean("xExchange")
    public DirectExchange getXExchange(){
        return new DirectExchange(X_EXCHANGE);
    }

    @Bean("dExchange")
    public DirectExchange getDExchange(){
        return new DirectExchange(DEAD_EXCHANGE);
    }


    //声明队列a ttl为10 并绑定死信交换机D
    @Bean("aQueue")
    public Queue getAQueue(){
        Map<String,Object> params = new HashMap<>(3);
        //声明当前队列绑定的死信交换机
        params.put("x-dead-letter-exchange",DEAD_EXCHANGE);
        //声明死信的路由key
        params.put("x-dead-letter-routing-key","DD");
        //声明队列的ttl
        params.put("x-message-ttl",10000);
        return QueueBuilder.durable(QUEUE_A).withArguments(params).build();
    }

    //声明队列A绑定交换机X
    @Bean
    public Binding getAQueueBindXExchange(@Qualifier("xExchange") DirectExchange exchange,
                                          @Qualifier("aQueue") Queue queue){
        return BindingBuilder.bind(queue).to(exchange).with("XA");
    }

    //声明队列B ttl为40 并帮顶死信交换机D
    @Bean("bQueue")
    public Queue getBQueue(){
        Map<String,Object> params = new HashMap<>(3);
        //声明当前队列绑定的死信交换机
        params.put("x-dead-letter-exchange",DEAD_EXCHANGE);
        //声明死信的路由key
        params.put("x-dead-letter-routing-key","DD");
        //声明队列的ttl
        params.put("x-message-ttl",40000);
        return QueueBuilder.durable(QUEUE_B).withArguments(params).build();
    }



    //声明队列B 绑定交换机X
    @Bean
    public Binding getBQueueBindXExchange(@Qualifier("xExchange") DirectExchange exchange,
                                          @Qualifier("bQueue") Queue queue){
        return BindingBuilder.bind(queue).to(exchange).with("XB");
    }

    //声明死信队列
    @Bean("dQueue")
    public Queue getDQueue(){
        return new Queue(DEAD_QUEUE);
    }

    //声明死信队列d 绑定死信交换机
    @Bean
    public Binding getDQueueBindDExchange(@Qualifier("dExchange") DirectExchange exchange,
                                          @Qualifier("dQueue") Queue queue){
        return BindingBuilder.bind(queue).to(exchange).with("DD");
    }



    @Bean("cQueue")
    public Queue getCQueue(){
        Map<String,Object> params = new HashMap<>(3);
        //声明当前队列绑定的死信交换机
        params.put("x-dead-letter-exchange",DEAD_EXCHANGE);
        //声明死信的路由key
        params.put("x-dead-letter-routing-key","DD");
        return QueueBuilder.durable(QUEUE_C).withArguments(params).build();
    }


    @Bean
    public Binding getCQueueBindXExchange(@Qualifier("cQueue")Queue queue,
                                          @Qualifier("xExchange") DirectExchange exchange ){
        return BindingBuilder.bind(queue).to(exchange).with("XC");
    }



}

