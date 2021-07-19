package com.hinz.aop.aspect;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;

import java.util.Arrays;

/**
 * Controller出异常的地方均记录下请求参数和异常信息
 *
 * @author yudong
 * @date 2021/5/18
 */
@Slf4j
@Aspect
public class LogAfterThrowingAspect {

    @AfterThrowing(value = "bean(*Controller)", throwing = "throwable")
    public void logAfterThrowing(JoinPoint joinPoint, Throwable throwable) {
        String classMethod = joinPoint.getTarget().getClass().getSimpleName() + "#" + joinPoint.getSignature().getName();
        Object[] args = joinPoint.getArgs();
        log.error("req:{}\nargs:{}\nerr:{}", classMethod, Arrays.toString(args), throwable.getMessage());
    }

}
