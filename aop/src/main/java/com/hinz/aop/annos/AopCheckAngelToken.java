package com.hinz.aop.annos;


import com.hinz.aop.aspect.AopCheckAngelTokenAspect;

import java.lang.annotation.*;

/**
 * 校验天使token,适用条件:<br/>
 * 方法的第一个参数必须是一个bean且带有token字段<br/>
 * 如果还带有UserInfo类型参数,则必须是方法的第二个参数,会为其赋值(当前登录用户信息,由token得到)
 *
 * @author yudong
 * @date 2021/5/25
 * @see AopCheckAngelTokenAspect
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD})
public @interface AopCheckAngelToken {
    long time() default 7 * 24 * 3600 * 1000;

    String appType() default "moonAngel";
}
