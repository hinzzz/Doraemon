package com.hinz.lambda;

import org.junit.Test;

import java.util.Comparator;
import java.util.TreeSet;

/**
 * @author ：quanhz
 * @date ：Created in 2021/1/14 11:28
 * @Description : No Description
 */
public class LambdaTest {

    @Test
    public void testInnerClass(){
        /**
         * 传统方式构建内部类
         * */
        Comparator<Integer> cpt = new Comparator<Integer>() {
            public int compare(Integer o1, Integer o2) {
                return Integer.compare(o1,o2);
            }
        };
        TreeSet<Integer> set = new TreeSet<Integer>(cpt);

        /**
         * 使用lambda
         */
        Comparator<Integer> cpt1 = (x,y)->{return Integer.compare(x,y);};
        TreeSet<Integer> set1 = new TreeSet<Integer>(cpt1);

    }
}
