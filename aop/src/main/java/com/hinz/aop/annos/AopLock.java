package com.hinz.aop.annos;


import com.hinz.aop.aspect.AopLockAspect;

import java.lang.annotation.*;

/**
 * 为方法加锁,处理完成再释放
 *
 * @author yudong
 * @date 2020/8/26
 * @see AopLockAspect
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD})
public @interface AopLock {

    /**
     * SpEL表达式,用于计算lockKey.
     */
    String value();

    /**
     * 单位秒
     */
    int waitTime() default 0;

    /**
     * 单位秒
     */
    int leaseTime() default 6;

    int errorCode() default 2733;

    String errorMsg() default "操作过于频繁，请稍后再试";

}
