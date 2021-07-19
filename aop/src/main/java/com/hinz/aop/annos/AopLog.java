package com.hinz.aop.annos;


import com.hinz.aop.aspect.AopLogAspect;

import java.lang.annotation.*;

/**
 * 记录方法的出入参信息,为减小日志体积,建议只记录会修改数据库的接口,如保存或更新接口,这些接口的出入参信息对于排查问题非常重要
 *
 * @author yudong
 * @date 2021/5/18
 * @see AopLogAspect
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD})
public @interface AopLog {
}
