package com.hinz.aop.function;


/**
 * @author yudong
 * @date 2021/6/10
 */
@FunctionalInterface
public interface TripleConsumer<T, U, V> {

    void accept(T t, U u, V v);

}
