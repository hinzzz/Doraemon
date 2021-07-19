package com.hinz.aop.aspect;

import com.hinz.aop.annos.AopLock;
import com.hinz.aop.exception.ServiceException;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.core.annotation.Order;
import org.springframework.expression.EvaluationContext;

import java.lang.reflect.Method;
import java.util.concurrent.TimeUnit;

import static com.hinz.aop.aspect.AopCacheAspect.PARSER;
import static com.hinz.aop.aspect.AopCacheAspect.initEvaluationContext;


/**
 * 为方法加锁,处理完成再释放
 *
 * @author yudong
 * @date 2021/5/21
 */
@Slf4j
@Aspect
@Order(3)
@ConditionalOnBean(RedissonClient.class)
public class AopLockAspect {
    @Autowired
    private RedissonClient redissonClient;
    @Value("${spring.application.name}")
    private String lockKeyPrefix;

    @Around("@annotation(com.hinz.aop.annos.AopLock)")
    public Object lock(ProceedingJoinPoint joinPoint) throws Throwable {
        Object[] args = joinPoint.getArgs();
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Method method = signature.getMethod();
        EvaluationContext context = initEvaluationContext(joinPoint);

        AopLock aopLock = method.getAnnotation(AopLock.class);
        String spEl = aopLock.value();
        String expressionValue = lockKeyPrefix + ":" + PARSER.parseExpression(spEl).getValue(context);
        RLock lock = redissonClient.getLock(expressionValue);
        try {
            boolean getterLock = lock.tryLock(aopLock.waitTime(), aopLock.leaseTime(), TimeUnit.SECONDS);
            if (!getterLock) {
                throw new ServiceException(aopLock.errorCode(), aopLock.errorMsg());
            }
            return joinPoint.proceed(args);
        } finally {
            try {
                lock.unlock();
            } catch (Exception e) {
                log.warn("unlock error:" + e.getMessage() + "," + e.getClass().getName());
            }
        }
    }

}
