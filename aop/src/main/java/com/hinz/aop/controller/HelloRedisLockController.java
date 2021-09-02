package com.hinz.aop.controller;

import com.hinz.aop.annos.AopLock;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloRedisLockController {


    @AopLock(value = "#mobile",leaseTime = 10000)
    @RequestMapping("/hello/{mobile}")
    public Object hello(@PathVariable String mobile){
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return "hi "+mobile;
    }
}
