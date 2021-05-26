package com.hinz.function;

import com.hinz.bean.Employee;
import org.junit.Test;

import java.io.PrintStream;
import java.util.Comparator;
import java.util.function.BiPredicate;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;

/*一、方法引用：若 Lambda 体中的内容有方法已经实现了，我们可以使用“方法引用”(可以理解为方法
*    引用是Lambda 表达式的另一种表现形式)
* 主要有三种语法格式：
*
* 注意：在使用 1 和 2 的时候有一个前提条件，就是函数式接口里面抽象方法的参数列表和返回值
*      要和Lambda体中方法的参数列表和返回值一致。
* 1.对象::实例方法名
* 2.类::静态方法名
*
* 注意：在使用 3 的时候也有一个前提条件，就是Lambda 表达式的参数列表中的第一个参数是实例方法* 的调用者，第二个参数是实例方法的参数时就可以
*     
* 3.类::实例方法名
*/
public class TestMethodRef {
    //对象::实例方法
    @Test
    public void test1(){
        PrintStream ps=System.out;
        Consumer<String> consumer=(x)->ps.println(x);
        consumer.accept("Hello World");
        PrintStream ps1=System.out;
        Consumer<String> consumer1=ps1::println;
        consumer1.accept("Hello World");
    }

    @Test
    public void test2(){
        Employee employee=new Employee("张三",18);
        Supplier<String> supplier=()->employee.getName();
        System.out.println(supplier.get());
        //方法引用
        Supplier<String> supplier1=employee::getName;
        System.out.println(supplier1.get());
    }

    //类::静态方法
    @Test
    public void test3(){
        Comparator<Integer> comparator=(x, y)->Integer.compare(x,y);
        //方法引用
        Comparator<Integer> comparator1=Integer::compare;
        System.out.println(comparator1.compare(1,2));
    }

    //类::实例方法
    @Test
    public void test4(){
        BiPredicate<String,String> predicate=(x, y)->x.equals(y);
        BiPredicate<String,String> predicate1=String::equals;
        System.out.println(predicate1.test("Hello","Hello"));
    }
 /*
 *二、构造器引用
 * 格式：
 *    ClassName::new
 * 注意：
 *  具体调用哪个构造方法看函数式接口中抽象方法的参数列表，抽象方法的参数列表要和构造方法的           
 *  参数列表一致
 */

    //构造器引用，具体调用那个构造方法看函数式接口中抽象方法的参数列表
    @Test
    public void test5(){
        Supplier<Employee> supplier=()->new Employee();
        Supplier<Employee> supplier1=Employee::new;
        System.out.println(supplier1.get() instanceof  Employee);
    }

    @Test
    public void test6(){
        Function<String,Employee> function=(x)->new Employee(x);
        Function<String,Employee> function1=Employee::new;
        System.out.println(function1.apply("hinzzz"));
        System.out.println(function1.apply("zzz").getName()==null);
    }
    
/* 
*三、数组引用
* Type[]::new
*/
    //数组引用
    @Test
    public void test7(){
        Function<Integer,String[]> function=(x)->new String[x];
        Function<Integer,String[]> function1=String[]::new;
        String[] str=function1.apply(10);
        System.out.println(str.length);
    }
}
