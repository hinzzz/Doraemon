package com.hinz.redisson.config;

import lombok.extern.slf4j.Slf4j;
import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.redisson.config.ReadMode;
import org.redisson.config.SubscriptionMode;
import org.redisson.connection.balancer.RoundRobinLoadBalancer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import redis.clients.jedis.HostAndPort;
import redis.clients.jedis.JedisCluster;

import java.util.HashSet;
import java.util.Set;

/**
 * @author hinzzz
 * @date 2021/8/9 11:11
 * @desc
 */
@Configuration
@Slf4j
public class GlobalConfig {

    @Bean(destroyMethod = "shutdown")
    public RedissonClient redissonClient() {
        Config config = new Config();
        //config.setLockWatchdogTimeout(5*1000);
        config.useClusterServers()
                .setScanInterval(2000) // 集群状态扫描间隔时间，单位是毫秒 默认值： 5000
                .setCheckSlotsCoverage(false)
                .addNodeAddress("redis://www.xieguangda.top:6379")//节点地址
                .addNodeAddress("redis://www.xieguangda.top:6380")//节点地址
                .addNodeAddress("redis://www.xieguangda.top:6381")//节点地址
                .setReadMode(ReadMode.SLAVE)//读取操作的负载均衡模式 默认：只在从服务节点里读取
                // 注：在从服务节点里读取的数据说明已经至少有两个节点保存了该数据，确保了数据的高可用性。
                .setSubscriptionMode(SubscriptionMode.MASTER)//订阅操作的负载均衡模式 默认：只在从服务节点里订阅
                .setLoadBalancer(new RoundRobinLoadBalancer())//负载均衡算法类的选择 默认： org.redisson.connection.balancer.RoundRobinLoadBalancer
                //org.redisson.connection.balancer.WeightedRoundRobinBalancer - 权重轮询调度算法
                //org.redisson.connection.balancer.RoundRobinLoadBalancer - 轮询调度算法
                //org.redisson.connection.balancer.RandomLoadBalancer - 随机调度算法
                .setRetryAttempts(3)//命令失败重试次数 默认：3
                .setRetryInterval(1500)//命令重试发送时间间隔，单位：毫秒 默认：1500
                .setMasterConnectionMinimumIdleSize(10)
                .setMasterConnectionPoolSize(64)
                .setSlaveConnectionMinimumIdleSize(10)
                .setSlaveConnectionPoolSize(64);
        RedissonClient redisson = Redisson.create(config);

        return Redisson.create(config);
    }


    @Bean
    public JedisCluster jedisCluster() {
        Set<HostAndPort> nodes = new HashSet<>();
        nodes.add(new HostAndPort("www.xieguangda.top", 6379));
        nodes.add(new HostAndPort("www.xieguangda.top", 6380));
        nodes.add(new HostAndPort("www.xieguangda.top", 6381));

        return new JedisCluster(nodes);
    }

}
