package com.hinz.aop.aspect;

import com.hinz.aop.annos.AopCache;
import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.core.DefaultParameterNameDiscoverer;
import org.springframework.core.ParameterNameDiscoverer;
import org.springframework.core.annotation.Order;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.expression.EvaluationContext;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;

import java.lang.reflect.Method;
import java.util.Objects;
import java.util.concurrent.TimeUnit;


/**
 * 缓存切面
 *
 * @author yudong
 * @date 2021/5/18
 */
@Aspect
@Order(4)
@ConditionalOnBean(RedisTemplate.class)
public class AopCacheAspect {
    public static final ExpressionParser PARSER = new SpelExpressionParser();
    public static final ParameterNameDiscoverer PARAMETER_NAME_DISCOVERER = new DefaultParameterNameDiscoverer();

    @Value("${spring.application.name}")
    private String serviceName;
    @Autowired
    private RedisTemplate redisTemplate;

    private static final String NULL_HOLDER = "-";

    @Around("@annotation(com.hinz.aop.annos.AopCache)")
    public Object cache(ProceedingJoinPoint joinPoint) throws Throwable {
        Object[] args = joinPoint.getArgs();
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Method method = signature.getMethod();
        EvaluationContext context = initEvaluationContext(joinPoint);
        AopCache aopCache = method.getAnnotation(AopCache.class);
        String keySpElValue = PARSER.parseExpression(aopCache.keySpEL()).getValue(context, String.class);
        String cacheKey;
        if (aopCache.useFixedPrefix()) {
            cacheKey = aopCache.fixedPrefix() + ":" + keySpElValue;
        } else {
            cacheKey = serviceName + ":" + keySpElValue;
        }
        String hashKeySpEl = aopCache.hashKeySpEL();
        String hashKey = null;
        Object res;
        boolean cacheAsHash = StringUtils.isNotEmpty(hashKeySpEl);
        if (cacheAsHash) {
            hashKey = PARSER.parseExpression(hashKeySpEl).getValue(context, String.class);
            Objects.requireNonNull(hashKey);
            res = redisTemplate.opsForHash().get(cacheKey, hashKey);
        } else {
            res = redisTemplate.opsForValue().get(cacheKey);
        }
        if (res != null) {
            if (NULL_HOLDER.equals(res)) {
                return null;
            } else {
                return res;
            }
        }
        res = joinPoint.proceed(args);
        if (cacheAsHash) {
            if (res != null) {
                redisTemplate.opsForHash().put(cacheKey, hashKey, res);
            } else {
                redisTemplate.opsForHash().put(cacheKey, hashKey, NULL_HOLDER);
            }
        } else {
            if (res != null) {
                redisTemplate.opsForValue().set(cacheKey, res);
            } else {
                redisTemplate.opsForValue().set(cacheKey, NULL_HOLDER);
            }
        }
        redisTemplate.expire(cacheKey, aopCache.seconds(), TimeUnit.SECONDS);
        return res;
    }

    public static EvaluationContext initEvaluationContext(ProceedingJoinPoint joinPoint) {
        Object[] args = joinPoint.getArgs();
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Method method = signature.getMethod();
        EvaluationContext context = new StandardEvaluationContext();
        String[] parameterNames = PARAMETER_NAME_DISCOVERER.getParameterNames(method);
        Objects.requireNonNull(parameterNames);
        for (int i = 0; i < parameterNames.length; i++) {
            String paramName = parameterNames[i];
            context.setVariable(paramName, args[i]);
        }
        return context;
    }
}
