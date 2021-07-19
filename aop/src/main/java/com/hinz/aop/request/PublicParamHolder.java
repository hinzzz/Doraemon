package com.hinz.aop.request;

import org.springframework.core.NamedThreadLocal;

/**
 * 持有并暴露请求的url公参
 *
 * @author yudong
 * @date 2021/6/2
 */
public final class PublicParamHolder {

    private static final ThreadLocal<PublicParamAttributes> THREAD_LOCAL = new NamedThreadLocal<>("Request Public Params");

    public static void setRequestAttributes(PublicParamAttributes attributes) {
        THREAD_LOCAL.set(attributes);
    }

    public static PublicParamAttributes getAttributes() {
        return THREAD_LOCAL.get();
    }

    public static void resetRequestAttributes() {
        THREAD_LOCAL.remove();
    }


}
