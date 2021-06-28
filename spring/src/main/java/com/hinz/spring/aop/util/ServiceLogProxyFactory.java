package com.hinz.spring.aop.util;


import com.alibaba.fastjson.JSON;
import com.hinz.spring.annotation.ServiceLog;
import com.hinz.spring.annotation.ServiceLogType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cglib.proxy.Enhancer;
import org.springframework.cglib.proxy.MethodInterceptor;
import org.springframework.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.Arrays;

/**
 * 2 * @Author: caizhenqin
 * 3 * @Date: 2021/5/7 15:50
 * 4
 */
public class ServiceLogProxyFactory {

    private static final Logger logger = LoggerFactory.getLogger(ServiceLogProxyFactory.class);

    public static Object createProxy(Object bean, ServiceLog annotation) {
        if(bean != null){
            Class<?> clazz = bean.getClass();
            Class<?>[] interfaces = clazz.getInterfaces();
            Object proxyBean = null;
            if(interfaces != null && interfaces.length != 0){
                // 使用JDK动态代理
                proxyBean = Proxy.newProxyInstance(clazz.getClassLoader(), interfaces, (proxy, method, args) ->
                        invoke(bean, args, method, null, false, annotation));
            }else{
                // 使用cglib动态代理
                Enhancer enhancer = new Enhancer();
                enhancer.setSuperclass(clazz);
                enhancer.setCallback((MethodInterceptor) (o, method, objects, methodProxy) ->
                        invoke(o, objects, method, methodProxy, true, annotation));
                proxyBean = enhancer.create();
            }

            return proxyBean;
        }
        return null;
    }

    private static Object invoke(Object bean, Object[] args, Method method, MethodProxy methodProxy, boolean cglib, ServiceLog annotation) throws Throwable {

        boolean matchMethod = matchMethod(annotation, method.getName());
        long mark = System.currentTimeMillis();
        if(matchMethod && (annotation.type() == ServiceLogType.Around || annotation.type() == ServiceLogType.START )){
            logger.info("mark：{}，信息：{}，方法名：{}，方法入参：{}", mark, annotation.message(), method.getName(), JSON.toJSONString(args));
        }
        Object result = null;
        long startTime = System.currentTimeMillis();
        if(cglib){
            result = methodProxy.invokeSuper(bean, args);
        }else {
            result = method.invoke(bean, args);
        }

        if(matchMethod && (annotation.type() == ServiceLogType.Around || annotation.type() == ServiceLogType.END )){
            logger.info("mark：{}，时间：{}，信息：{}，方法名：{}，方法返回值：{}", mark, System.currentTimeMillis() - startTime, annotation.message(), method.getName(), JSON.toJSONString(result));
        }
        return result;
    }


    private static boolean matchMethod(ServiceLog annotation, String methodName){
        /*if(annotation.mode() == MethodMatchMode.INCLUDE_MODE && Arrays.asList(annotation.includeMethods()).contains(methodName)){
            return true;
        }else if(annotation.mode() == MethodMatchMode.EXCLUDE_MODE && !Arrays.asList(annotation.excludeMethods()).contains(methodName)){
            return true;
        }*/
        return true;
    }
}
