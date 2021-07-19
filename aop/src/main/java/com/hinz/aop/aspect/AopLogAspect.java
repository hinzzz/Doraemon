package com.hinz.aop.aspect;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.core.annotation.Order;

import java.util.Arrays;


/**
 * 记录方法的入参和出参
 *
 * @author yudong
 * @date 2021/5/18
 */
@Slf4j
@Aspect
@Order(2)
public class AopLogAspect {

    @Around("@annotation(cn.com.bluemoon.mh.common.aop.annos.AopLog)")
    public Object log(ProceedingJoinPoint joinPoint) throws Throwable {
        Object[] parameters = joinPoint.getArgs();
        String classMethod = joinPoint.getSignature().getDeclaringType().getSimpleName() + "#" + joinPoint.getSignature().getName();
        Object resObj = joinPoint.proceed(parameters);
        log.info("req:{}\nargs:{}\nres:{}", classMethod, Arrays.toString(parameters), resObj);
        return resObj;
    }

}
