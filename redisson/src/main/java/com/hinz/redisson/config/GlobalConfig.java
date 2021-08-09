package com.hinz.redisson.config;

import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author hinzzz
 * @date 2021/8/9 11:11
 * @desc
 */
@Configuration
public class GlobalConfig {

    @Bean(destroyMethod = "shutdown")
    public RedissonClient redissonClient(){
        Config config = new Config();
        config.useClusterServers()
                .setCheckSlotsCoverage(false)
                .addNodeAddress("redis://www.xieguangda.top:6379")
                .addNodeAddress("redis://www.xieguangda.top:6380")
                .addNodeAddress("redis://www.xieguangda.top:6381");
        return Redisson.create(config);
    }
}
