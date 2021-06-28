package com.hinz.spring.beanProcesser;

import com.hinz.spring.annotation.ServiceLog;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;

public class MyBeanProcessor implements BeanPostProcessor {

    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        return null;
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        Class<?> clz = bean.getClass();
        ServiceLog serviceLog = clz.getAnnotation(ServiceLog.class);
        if( serviceLog != null){

        }

        return bean;
    }
}
