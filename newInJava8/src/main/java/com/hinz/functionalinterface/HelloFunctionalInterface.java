package com.hinz.functionalinterface;

/**
 * @author ：quanhz
 * @date ：Created in 2021/1/15 14:26
 * @Description : No Description
 */
@FunctionalInterface
public interface HelloFunctionalInterface {
    String say();
    default void say1(){

    }
}
