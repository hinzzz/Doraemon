package com.hinz.lambda;

import com.hinz.functionalinterface.MyFun;

import java.util.function.BinaryOperator;
import java.util.function.Consumer;

/**
 * @author ：quanhz
 * @date ：Created in 2021/1/14 11:27
 * @Description : lambda表达式
 */
public class LambdaDemo {

    public static void main(String[] args) {

        /*//格式一、无参无返回值，Lambda体只需一条语句
        Runnable run1 = () -> {System.out.println("hinz");};
        //格式二、无返回值，Lambda体{}可以省略
        Runnable run2 = () -> System.out.println("hinz");
        //格式三、单个参数,无返回值
        Consumer<String> c1 = (String str) -> { System.out.println(str); };
        //格式四、单个参数时参数的小括号可以省略,参数类型也可以省略
        Consumer<String> c2 = str -> System.out.println(str);
        //格式五、多个参数，并且有返回值
        BinaryOperator<Integer> b = (x,y) -> { return x+y ;};*/

        Integer num = LambdaDemo.toAdd(x -> x+100, 1);
        System.out.println("num = " + num);

    }

    public static Integer toAdd(MyFun myFun,Integer num){
        return myFun.getValue(num);
    }

}

