package com.hinz.redisson.controller;

import lombok.extern.slf4j.Slf4j;
import org.redisson.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.*;

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

    @Autowired
    private StringRedisTemplate stringRedisTemplate;


    @RequestMapping("createOrder/{orderNo}")
    public String createOrder(@PathVariable String orderNo) {
        RLock lock = redissonClient.getLock(orderNo);
        try {
            boolean tryLock = lock.tryLock(6, 11, TimeUnit.SECONDS);
            if (!tryLock) {
                log.info("请勿重复提交！");
                return "请勿重复提交！";
            }
            log.info("订单号：{},线程id:{}", orderNo, Thread.currentThread().getId());
            TimeUnit.SECONDS.sleep(200);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                lock.unlock();
            } catch (Exception e) {
                log.warn("unlock error:" + e.getMessage() + "," + e.getClass().getName());
            }
        }
        return "ok";
    }


    @RequestMapping("/nomalLock/{orderNo}")
    @ResponseBody
    public String nomalLock(@PathVariable String orderNo) {
        RLock lock = redissonClient.getLock(orderNo);
        //lock.lock(10,TimeUnit.SECONDS);
        lock.lock();
        try {
            TimeUnit.SECONDS.sleep(50);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
        return "ok";
    }


    @RequestMapping("/write/{orderNo}")
    @ResponseBody
    public String write(@PathVariable String orderNo) {
        RReadWriteLock rReadWriteLock = redissonClient.getReadWriteLock("rw-lock");
        RLock rLock = rReadWriteLock.writeLock();
        rLock.lock();
        try {
            stringRedisTemplate.opsForValue().set("write", orderNo);
            TimeUnit.SECONDS.sleep(0);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }finally {
            rLock.unlock();
        }
        return orderNo;
    }


    @RequestMapping("/read/{orderNo}")
    @ResponseBody
    public String read(@PathVariable String orderNo) {
        RReadWriteLock rReadWriteLock = redissonClient.getReadWriteLock("rw-lock");
        RLock rLock = rReadWriteLock.readLock();
        rLock.lock();
        try {
            TimeUnit.SECONDS.sleep(10);
            return stringRedisTemplate.opsForValue().get("write");
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            rLock.unlock();
        }
        return "xx";
    }

    /*@RequestMapping("/park")
    @ResponseBody
    public String park() {
        RSemaphore semaphore = redissonClient.getSemaphore("car");
        try {
            semaphore.acquire();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return "park success";
    }

    @RequestMapping("/leave")
    @ResponseBody
    public String leave() {
        RSemaphore semaphore = redissonClient.getSemaphore("car");
        semaphore.release();
        return "leave success";
    }*/

    /**
     * 车库停车
     * 三个车位
     * @return
     */
    @RequestMapping("/park")
    @ResponseBody
    public String park() {
        RSemaphore semaphore = redissonClient.getSemaphore("car");
        boolean b = semaphore.tryAcquire();
        return "park success > "+b;
    }

    @RequestMapping("/leave")
    @ResponseBody
    public String leave() {
        RSemaphore semaphore = redissonClient.getSemaphore("car");
        semaphore.release();
        return "leave success";
    }

    /**
     * 学校放假锁门 5个班级的人全部走完 才能锁门
     * @return
     */
    @GetMapping("/lockDoor")
    public String lockDoor() throws Exception{
        RCountDownLatch door = redissonClient.getCountDownLatch("door");
        door.trySetCount(5);
        door.await();//等待五个班走完才能关门
        return "放假了  关门了。。。。。。";
    }

    @GetMapping("/gogogo/{classId}")
    public String gogogo(@PathVariable String classId){
        RCountDownLatch door = redissonClient.getCountDownLatch("door");
        door.countDown();//计数减一
        return classId+" 班的人全走了。。。。";
    }


    @GetMapping("/fairLock/{orderNo}")
    public String fairLock(@PathVariable String orderNo){
        RLock fairLock = redissonClient.getFairLock("fairLock");
        System.out.println("公平锁 订单号：" + orderNo + "等待处理。。。。。");
        fairLock.lock(12,TimeUnit.SECONDS);
        try {
            TimeUnit.SECONDS.sleep(10);
            System.out.println("公平锁 订单号：" + orderNo + "处理成功");
            return "公平锁 订单号："+orderNo+"处理成功";
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            fairLock.unlock();
        }
        return "订单号："+orderNo+"处理失败";
    }

    @GetMapping("/noFairLock/{orderNo}")
    public String noFairLock(@PathVariable String orderNo){
        RLock lock = redissonClient.getLock("noFairLock");
        System.out.println("普通锁 订单号：" + orderNo + "等待处理。。。。。");
        lock.lock(12,TimeUnit.SECONDS);
        try {
            TimeUnit.SECONDS.sleep(10);
            System.out.println("普通锁 订单号：" + orderNo + "处理成功");
            return "普通锁 订单号："+orderNo+"处理成功";
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            lock.unlock();
        }
        return "订单号："+orderNo+"处理失败";
    }


    @GetMapping("/testThread")
    public String testThread(){
        System.out.println(Thread.currentThread().getId());
        this.test();
        return "ok";
    }

    private void test() {
        System.out.println(Thread.currentThread().getId());
    }


}
