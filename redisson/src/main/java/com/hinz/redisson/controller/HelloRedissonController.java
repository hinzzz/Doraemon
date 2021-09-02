package com.hinz.redisson.controller;

import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.TimeUnit;

/**
 * @author hinzzz
 * @date 2021/8/10 14:33
 * @desc
 */
@RestController
@Slf4j
public class HelloRedissonController {

    @Autowired
    private RedissonClient redissonClient;

    @RequestMapping("createOrder/{orderNo}")
    public String createOrder(@PathVariable String orderNo) {
        RLock lock = redissonClient.getLock(orderNo);
        try {
            boolean tryLock = lock.tryLock(6, 10000, TimeUnit.SECONDS);
            if (!tryLock) {
                log.info("请勿重复提交！");
                return "请勿重复提交！";
            }
            log.info("订单号：{},线程id:{}", orderNo, Thread.currentThread().getId());
            TimeUnit.SECONDS.sleep(20);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if(lock.isLocked()){
                    lock.unlock();
                }
            } catch (Exception e) {
                log.warn("unlock error:" + e.getMessage() + "," + e.getClass().getName());
            }
        }
        return "ok";
    }



}
