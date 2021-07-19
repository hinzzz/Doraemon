package com.hinz.aop.aspect;


import lombok.extern.slf4j.Slf4j;
import net.sf.json.JSONObject;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.core.annotation.Order;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Field;
import java.lang.reflect.Method;


/**
 * @author yudong
 * @date 2021/5/25
 */
@Slf4j
@Aspect
@Order(1)
public class AopCheckAngelTokenAspect {

   /* private final SsoService ssoService;

    public AopCheckAngelTokenAspect(SsoService ssoService) {
        this.ssoService = ssoService;
    }

    *//**
     * 校验天使token
     *//*
   // @Around("@annotation(cn.com.bluemoon.mh.common.aop.annos.AopCheckAngelToken)")
    public Object checkToken(ProceedingJoinPoint joinPoint) throws Throwable {
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Method method = signature.getMethod();
        AopCheckAngelToken aopCheckAngelToken = method.getAnnotation(AopCheckAngelToken.class);

        Object[] args = joinPoint.getArgs();
        Object req = args[0];
        Field tokenField = ReflectionUtils.findField(req.getClass(), "token");
        if (tokenField == null) {
            throw new IllegalArgumentException(req.getClass().getName() + " 缺少token字段");
        }
        tokenField.setAccessible(true);
        String token = ((String) tokenField.get(req));
        String string = "";
        JSONObject resJsonObj = null;
        try {
            JSONObject reqJsonObj = new JSONObject();
            reqJsonObj.put("token", token);
            reqJsonObj.put("time", aopCheckAngelToken.time());
            reqJsonObj.put("appType", aopCheckAngelToken.appType());
            string = ssoService.checkTokenForAllApp(reqJsonObj);
            resJsonObj = JSONObject.fromObject(string);
            String angelCode = resJsonObj.getString("userName");
            if (args.length > 1 && args[1] instanceof UserInfo) {
                reqJsonObj = new JSONObject();
                reqJsonObj.put("account", angelCode);
                resJsonObj = JSONObject.fromObject(ssoService.getUserInfo(reqJsonObj));
                UserInfo userInfo = (UserInfo) JSONObject.toBean(resJsonObj.getJSONObject("user"), UserInfo.class);
                args[1] = userInfo;
            }
        } catch (Exception e) {
            log.error("check token error,token:" + token + "," + string);
            if (resJsonObj != null) {
                throw new ServiceException(resJsonObj.getInt("responseCode"), resJsonObj.getString("responseMsg"));
            } else {
                throw e;
            }
        }
        return joinPoint.proceed(args);
    }*/

}
