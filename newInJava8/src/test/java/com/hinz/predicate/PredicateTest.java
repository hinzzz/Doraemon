package com.hinz.predicate;

import org.junit.Test;

import java.util.function.Predicate;

/**
 * @author ：quanhz
 * @date ：Created in 2021/1/14 16:01
 * @Description : No Description
 */
public class PredicateTest {

    @Test
    public void fun1(){
        /**
         * 判断数字是否大于7
         */
        Predicate<Integer> predicate = x -> x > 7;
        System.out.println("predicate = " + predicate);

    }
}
