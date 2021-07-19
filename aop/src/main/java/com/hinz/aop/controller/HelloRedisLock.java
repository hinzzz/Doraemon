package com.hinz.aop.controller;

import com.hinz.aop.annos.AopLock;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloRedisLock {


    @AopLock("#mobile")
    @RequestMapping("/hello")
    public Object hello(String mobile){
        return "hi "+mobile;
    }
}
