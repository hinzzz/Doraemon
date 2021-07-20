package com.hinz.aop.controller;

import com.hinz.aop.annos.AopLock;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloRedisLockController {


    @AopLock("#mobile")
    @RequestMapping("/hello/{mobile}")
    public Object hello(@PathVariable String mobile){
        try {
            Thread.sleep(20000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return "hi "+mobile;
    }
}
