package com.hinz.aop.annos;


import com.hinz.aop.aspect.AopCacheAspect;

import java.lang.annotation.*;

/**
 * @author yudong
 * @date 2021/5/18
 * @see AopCacheAspect
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD})
public @interface AopCache {

    /**
     * 是否使用默认的固定前缀,否则使用服务名作为前缀
     */
    boolean useFixedPrefix() default false;

    /**
     * 固定前缀,当useFixedPrefix为true时,使用该值作为前缀
     */
    String fixedPrefix() default "mh-common-aop";

    /**
     * SpEL表达式,缓存的key,表达式结果必须为String类型
     */
    String keySpEL();

    /**
     * SpEL表达式,缓存的hashKey,若不为空,则以hash结构来存储,其中表达式结果必须为String类型
     */
    String hashKeySpEL() default "";

    /**
     * 缓存时长(秒)
     */
    int seconds() default 600;


}
