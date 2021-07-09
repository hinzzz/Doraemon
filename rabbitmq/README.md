#### 一、工作队列

在实际生产中，当消息队列中有过多的消息等待消费时，我们使用一个消费者来处理消息显然是不够的，我们可以增加消费者，来共享消息队列中的消息，进行任务处理

消费一条消息往往比产生一条消息慢很多，为了防止消息积压，一般需要开启多个工作线程同时消费消息。在 RabbitMQ 中，我们可以创建多个 Consumer 消费同一队列

##### 1、轮询分发消息

###### 生产者

```java
public class Task01 {
    private final static String QUEUE_NAME="work";
    public static void main(String[] args) {
        Channel channel = RabbitMQUtils.getChannel();
        try {
            channel.queueDeclare(QUEUE_NAME,false,false,false,null);
            System.out.println("请输入消息：");
            Scanner sc = new Scanner(System.in);
            while (sc.hasNext()){
                String msg = sc.next();
                channel.basicPublish("",QUEUE_NAME,null,msg.getBytes());
                System.out.println("发送消息完成："+new String(msg.getBytes()));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
```

###### 消费者1

```java
public class Woker01 {
    private final static String QUEUE_NAME="work";
    public static void main(String[] args) {
        Channel channel = RabbitMQUtils.getChannel();
        DeliverCallback deliverCallback =  (consumerTag, message) ->{
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("consumerTag = " + consumerTag);
            System.out.println("工作线程01：" + new String (message.getBody())+" "+ LocalDateTime.now().toString());
        };

        CancelCallback cancelCallback = consumerTag ->{
            System.out.println("consumerTag = " + consumerTag);
        };
        try {
            System.out.println("工作线程01 等待消费");
            channel.basicConsume(QUEUE_NAME,true,deliverCallback,cancelCallback);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
```

###### 消费者2

```java
public class Woker02 {
    private final static String QUEUE_NAME="work";
    public static void main(String[] args) {
        Channel channel = RabbitMQUtils.getChannel();
        DeliverCallback deliverCallback =  (consumerTag, message) ->{
             try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("consumerTag = " + consumerTag);
            System.out.println("工作线程02：" + new String (message.getBody())+" "+ LocalDateTime.now().toString());
        };
        CancelCallback cancelCallback = consumerTag ->{
            System.out.println("consumerTag = " + consumerTag);
        };
        try {
            System.out.println("工作线程02 等待消费");
            channel.basicConsume(QUEUE_NAME,true,deliverCallback,cancelCallback);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
```

###### 运行结果

消费者1和2消费消息的速度不一样，按道理来说处理速度快的消费者应该消费更多的消息，但是他们各自轮流消费了所有的消息，why?

RabbitMQ 默认将消息顺序发送给下一个消费者，不管每个消费者的消费效率，这样，每个消费者会得到相同数量的消息。即，**轮询分发模式**。

![](http://hinzzz.oss-cn-shenzhen.aliyuncs.com/work_producer.png?Expires=32500886400&OSSAccessKeyId=LTAI4G9rkBZLb3G51wiGr2sS&Signature=5MAh2pqoXVz7S2s%2BXDqKFV4QDao%3D)

![](http://hinzzz.oss-cn-shenzhen.aliyuncs.com/work_consumer1.png?Expires=32500886400&OSSAccessKeyId=LTAI4G9rkBZLb3G51wiGr2sS&Signature=apFRKpqLeETl0Of6Oyvmg1XNOlg%3D)

![](http://hinzzz.oss-cn-shenzhen.aliyuncs.com/work-consumer2.png?Expires=32500886400&OSSAccessKeyId=LTAI4G9rkBZLb3G51wiGr2sS&Signature=A3Nxcn6KmtVasQUIFkw2Jaq%2B39o%3D)





#### 二、消息应答

> 消费者在处理一个任务的过程中，处理失败或者服务器宕机，会发生什么情况？
>
> RabbitMQ一旦向消费者传递了一条消息，变立即将改消息标志位已删除。在这种情况下，突然有某个消费者挂掉了，我们将丢失正在处理的消息。以及后续发送给该消费者的消息，它都无法收到。

##### 1、概念

​	为了保证消息在传递的过程中不丢失，RabbitMQ引入了**消息应答机制**，消息应答就是：消费者在接收到消息并完成消息处理之后，告诉RabbitMQ它已经处理了，可以将该条消息删掉。

##### 2、自动应答

​	消息发送后立即被认为已传递成功，这种模式需要在**高吞吐量和数据传输安全方面做权衡**，因为这种模式如果**消息在接收到之前**，消费者那边出现连接关闭，那么消息就会丢失了。当然另一方面这种消费模式消费者那边可以传递过载的消息，没有对传递消息的数量进行限制，	最终会导致消息堆积过多，内存耗尽。所以这种模式只适合消费者能够高效处理消息的情况下使用

##### 3、手动应答

```java
public class AckConsumer {
    public static final String TASK_QUEUE = "task";
    public static void main(String[] args) {
        Channel channel = RabbitMQUtils.getChannel();
        try {
            boolean autoAck = false;
            DeliverCallback deliverCallback = (consumerTag,  message) -> {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println("消费消息："+new String(message.getBody()));
                /**
                 * 1、消息标记
                 * 2、false:只应答接收到的那个传递的消息 有5,6,7,8个消息 当前消息是8 只会对8进行应答
                 *    true:应答所有消息 包括传过来的消息 会对5,6,7,8消息进行应答
                 *
                 */
                channel.basicAck(message.getEnvelope().getDeliveryTag(),false);
            };
            channel.basicConsume(TASK_QUEUE,autoAck,deliverCallback,a -> {});
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
```

##### 4、消息重新入队

​	如果消费者在处理消息的过程中，由于某些原因失去连接或者宕机，导致消息为发送ack确认，RabbitMQ将了解到消息未完全处理，并将其重新排队。如果此时其他消费者可以处理，它将很快将消息发给另一个消费者。这样，即时某个消费者偶尔死亡，也不会影响消息丢失。



