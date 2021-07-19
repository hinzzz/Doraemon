package com.hinz.aop.config;

import com.hinz.aop.aspect.AopCacheAspect;
import com.hinz.aop.aspect.AopLockAspect;
import com.hinz.aop.aspect.AopLogAspect;
import com.hinz.aop.aspect.LogAfterThrowingAspect;
import org.springframework.context.annotation.*;

/**
 * @author yudong
 * @date 2021/5/21
 */
@Configuration
@EnableAspectJAutoProxy
public class AspectConfig {

    @Bean
    public LogAfterThrowingAspect logAfterThrowingAspect() {
        return new LogAfterThrowingAspect();
    }

    @Bean
    public AopCacheAspect aopCacheAspect() {
        return new AopCacheAspect();
    }

    @Bean
    public AopLogAspect aopLogAspect() {
        return new AopLogAspect();
    }

    @Bean
    public AopLockAspect aopLockAspect() {
        return new AopLockAspect();
    }
}
