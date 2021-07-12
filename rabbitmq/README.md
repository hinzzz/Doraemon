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

##### 3、消息重新入队

​	如果消费者在处理消息的过程中，由于某些原因失去连接或者宕机，导致消息为发送ack确认，RabbitMQ将了解到消息未完全处理，并将其重新排队。如果此时其他消费者可以处理，它将很快将消息发给另一个消费者。这样，即时某个消费者偶尔死亡，也不会影响消息丢失。

##### 4、手动应答

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
                 *    true:应答所.有消息 包括传过来的消息 会对5,6,7,8消息进行应答
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

##### 

#### 三、持久化

> 消息应答解决了任务处理的过程中不丢失的情况，但是如何保障当RabbitMQ宕机之后，消费者发送的过来的消息不丢失。默认情况下RabbitMQ退出或者由于某种情况宕机时，它忽略队列和消息，除非告诉他不这样做。**确保消息和队列不丢失，需要将队列和消息都标志为持久化**



##### 1、队列如何实现持久化

​		之前我们创建的队列都是非持久话的，如果RabbitMQ重启，队列就会被删掉，我们需要将队列声明为持久化

```java
boolean durable = true;
channel.queueDeclare(QUEUE_NAME,durable,false,false,null);
```

注意，如果之前已经声明过该队列，需要把该队列先删除，否则会报下面的错

```java
Caused by: com.rabbitmq.client.ShutdownSignalException: channel error; protocol method: #method<channel.close>(reply-code=406, reply-text=PRECONDITION_FAILED - inequivalent arg 'durable' for queue 'hello' in vhost '/': received 'true' but current is 'false', class-id=50, method-id=10)
```

在控制台我们可以看到该队列多了个持久化的标志，这时候重启RabbitMQ之后，**该队列依然存在，但是队列里的消息已经被删除了**

![](http://hinzzz.oss-cn-shenzhen.aliyuncs.com/durable.png?Expires=32500886400&OSSAccessKeyId=LTAI4G9rkBZLb3G51wiGr2sS&Signature=ffIm0fKRQ582lahVy9KVE48F1JA%3D)



##### 2、消息持久化

要想让消息实现持久化，推送消息的时候好需要声明一个属性**MessageProperties.PERSISTENT_TEXT_PLAIN**

```java
channel.basicPublish("",QUEUE_NAME, MessageProperties.PERSISTENT_TEXT_PLAIN,msg.getBytes());
```

重启之后消息依然存在



将消息标志位持久化并不能保证完全不丢失消息，尽管告诉RabbitMQ将消息保存到磁盘，但是这里依然存在当消息刚准被存储在磁盘的时候，但是还没有存储完，消息还在一个持久化的间隔点，此时并没有真正写入磁盘。持久性保证并不强，但是对于简单的队列而言，已经绰绰有余了，



#### 四、不公平分发

> 从上面我们知道，RabbitMQ默认的消息分发策略是轮训分发，这种策略并不是很好。比如说，存在两个消费者A（处理速度快）,B（处理速度慢）,当他们在处理任务的时候，如果我们还是使用轮训分发的策略，A就会很快处理完任务，并处于空闲时间，而B则一直在干活，这种分发策略其实就不太好，如果队列还在不断的添加消息，就会导致B产生消息堆积，而A消费完之后处于空闲状态。我们想要的效果实际是 **能者多劳**



##### 1、预取值

```java
int prefetchCount = 1;
channel.basicQos(prefetchCount);
```

意思是消费者当前任务没有处理完或者没有应答，RabbitMQ先别分配新的消息给我，我目前只能处理一个任务，等我处理完先。然后RabbitMQ就

本身消息的发送就是异步发送的，所以在任何时候， channel 上肯定不止只有一个消息另外来自消费
者的手动确认本质上也是异步的。因此这里就存在一个未确认的消息缓冲区，因此希望开发人员能限制此
缓冲区的大小，以避免缓冲区里面无限制的未确认消息问题。这个时候就可以通过使用 basic.qos 方法设
置“预取计数”值来完成的。 该值定义通道上允许的未确认消息的最大数量。一旦数量达到配置的数量，
RabbitMQ 将停止在通道上传递更多消息，除非至少有一个未处理的消息被确认，例如，**假设在通道上有**
**未确认的消息 5、 6、 7， 8，并且通道的预取计数设置为 4，此时 RabbitMQ 将不会在该通道上再传递任何**
**消息，除非至少有一个未应答的消息被 ack。比方说 tag=6 这个消息刚刚被确认 ACK， RabbitMQ 将会感知**
**这个情况到并再发送一条消息。**消息应答和 QoS 预取值对用户吞吐量有重大影响。通常，**增加预取将提高**
**向消费者传递消息的速度**。 虽然自动应答传输消息速率是最佳的，但是，在这种情况下已传递但尚未处理
的消息的数量也会增加，从而增加了消费者的 RAM 消耗(随机存取存储器)应该小心使用具有无限预处理
的自动确认模式或手动确认模式，**消费者消费了大量的消息如果没有确认的话，会导致消费者连接节点的**
**内存消耗变大，所以找到合适的预取值是一个反复试验的过程，不同的负载该值取值也不同 100 到 300 范**
**围内的值通常可提供最佳的吞吐量，并且不会给消费者带来太大的风险**。**预取值为 1 是最保守的。当然这**
**将使吞吐量变得很低，特别是消费者连接延迟很严重的情况下，特别是在消费者连接等待时间较长的环境**
**中**。对于大多数应用来说，稍微高一点的值将是最佳的。  





![](http://hinzzz.oss-cn-shenzhen.aliyuncs.com/prefetch.png?Expires=32500886400&OSSAccessKeyId=LTAI4G9rkBZLb3G51wiGr2sS&Signature=1nXf3JauH9lGsZnkfnPyfq%2BiMo8%3D)

![]( http://hinzzz.oss-cn-shenzhen.aliyuncs.com/prefetch2.png?Expires=32500886400&OSSAccessKeyId=LTAI4G9rkBZLb3G51wiGr2sS&Signature=vh6RPipkqrv9dQOru6J9n98LUZM%3D)





#### 五、发布确认

##### 1、发布确认原理

> ​	生产者将信道设置成confirm模式，一旦信道进入confirm模式，所有在该信道发布的消息将会被指派一个唯一ID（从1开始）,一旦消息被投递到所匹配的消息之后，broker就会发送一个确认消息给生产者（包括消息的唯一ID）,这就使得生产者已经知道消息成功投递到目的队列了。如果消息是持久化的，那么确认消息会在将消息写入磁盘之后发送给生产者，此外也可以设置basic.ack的mutiple，表示到这个序号之前的的所有消息都已经应答									     	confirm模式最大的好处是，它是异步的，一旦发布一条消息，生产者应用程序就可以在等待返回确认的时候继续发送下一条消息，当消息最终确认之后，生产应用就可以通过回调来处理该确认消息。如果RabbitMQ因为内部原因导致消息丢失，就会发送一条nack消息，生产者同样可以在回调中处理该消息。

##### 2、发布确认策略

###### 开启发布确认的方法

