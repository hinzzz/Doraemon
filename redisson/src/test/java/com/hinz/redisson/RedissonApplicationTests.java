package com.hinz.redisson;

import org.junit.jupiter.api.Test;
import org.redisson.Redisson;
import org.redisson.RedissonRedLock;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.*;
import java.util.concurrent.TimeUnit;

//@SpringBootTest
class RedissonApplicationTests {

    @Autowired
    private RedissonClient redissonClient;




    @Test
    void contextLoads() throws Exception{
        String a = String.valueOf(null);
        System.out.println(String.valueOf(a));

    }


    @Test
    public void redLock(){
        Config config1 = new Config();
        config1.useSingleServer().setAddress("redis://www.xieguangda.top:6379")
                .setDatabase(0);
        RedissonClient redissonClient1 = Redisson.create(config1);

        Config config2 = new Config();
        config2.useSingleServer().setAddress("redis://www.xieguangda.top:6380")
                .setDatabase(0);
        RedissonClient redissonClient2 = Redisson.create(config2);

        Config config3 = new Config();
        config3.useSingleServer().setAddress("redis://www.xieguangda.top:6381")
                .setDatabase(0);
        RedissonClient redissonClient3 = Redisson.create(config3);

        String resourceName = "REDLOCK_KEY";

        RLock lock1 = redissonClient1.getLock(resourceName);
        RLock lock2 = redissonClient2.getLock(resourceName);
        RLock lock3 = redissonClient3.getLock(resourceName);
        // 向3个redis实例尝试加锁
        RedissonRedLock redLock = new RedissonRedLock(lock1, lock2, lock3);
        boolean isLock;
        try {
            // isLock = redLock.tryLock();
            // 500ms拿不到锁, 就认为获取锁失败。10000ms即10s是锁失效时间。
            isLock = redLock.tryLock(500, 10000, TimeUnit.MILLISECONDS);
            System.out.println("isLock = "+isLock);
            if (isLock) {
                //TODO if get lock success, do something;
            }
        } catch (Exception e) {
        } finally {
            // 无论如何, 最后都要解锁
            redLock.unlock();
        }
    }

    @Test
    public void toTwoSum(){
        int[] a = {3,2,4};
        Arrays.stream(add(a, 6)).forEach(System.out::println);
    }

    public int[] add(int[] nums, int target) {
        Map<Integer,Integer> map = new HashMap<>();
        int[] result = new int [2];
        int count = nums.length;
        for (int i = 0; i < count; i++) {
            if(map.containsKey(nums[i])){
                result[0] = map.get(nums[i]);
                result[1] = i;
                return result;
            }
            // 3 0  > 3
            // 4 1  > 2
            map.put(target - nums[i],i);
        }
        return  result;
    }
}
