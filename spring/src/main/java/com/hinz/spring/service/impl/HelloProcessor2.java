package com.hinz.spring.service.impl;

import com.hinz.spring.annotation.ServiceLog;
import com.hinz.spring.service.HelloProcessor;

public class HelloProcessor2 implements HelloProcessor {

    @ServiceLog
    @Override
    public void say() {
        System.out.println("22222222");
    }
}
