package com.hinz.lambda;

import com.hinz.bean.Employee;

import java.util.function.Consumer;
import java.util.function.Function;

/**
 * @author ：quanhz
 * @date ：Created in 2021/1/18 17:28
 * @Description : 双冒号
 */
public class DoubleColonDemo {
    public static void main(String[] args) {

        Consumer<String> c1 = (String s) -> {System.out.println(s);} ;
        c1.accept("hello ::");

        //使用双冒号
        Consumer<String> c2 = System.out::println;
        c2.accept("hi ::");


        Function<Integer, Employee> f1 = (n) -> new Employee();
        System.out.println("f1 = " + f1.apply(1));

        //使用双冒号
        Function<Integer, Employee> f2 = Employee::new;
        System.out.println("f2 = " + f2.apply(2));


        Function<Integer,Integer[]> f3 = n -> new Integer[n];
        System.out.println("f3 = " + f3.apply(3));

        //使用双冒号
        Function<Integer,Integer[]> f4 = Integer[]::new;
        System.out.println("f5 = " + f4.apply(4));

    }

}
