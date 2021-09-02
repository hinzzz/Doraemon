package com.hinz.streamapi;

import com.hinz.bean.Employee;
import org.junit.Test;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @author ：quanhz
 * @date ：Created in 2021/1/19 10:43
 * @Description : No Description
 */
public class StreamApiTest {

    @Test
    public void fun1(){
        
        List<Employee> emps = Arrays.asList(
                new Employee(1,"hinzzz",18,100,null),
                new Employee(1,"hinzzz",18,100,null),
                new Employee(1,"zs",19,200,null),
                new Employee(1,"l4",20,500,null));

        emps.stream().map(item->{
            if(item.getName().equals("zs")){
                item.setName("hahahhahaha");
            }
            return item;
        }).collect(Collectors.toList());

        System.out.println("emps = " + emps);
    }

    @Test
    public void collect(){
        List<Employee> emps = Arrays.asList(
                new Employee(1,"hinzzz",18,100,null),
                new Employee(1,"hinzzz",18,100,null),
                new Employee(1,"zs",19,200,null),
                new Employee(1,"l4",20,500,null));
        //把流中元素收集到List中
        List<Employee> collect = emps.stream().collect(Collectors.toList());
        System.out.println("collect = " + collect);

        //把流中元素收集到Set中
        Set<Employee> collect1 = emps.stream().collect(Collectors.toSet());
        System.out.println("collect1 = " + collect1);

        //把流中元素收集到指定集合中
        ArrayList<Employee> collect2 = emps.stream().collect(Collectors.toCollection(ArrayList::new));
        System.out.println("collect2 = " + collect2);

        //计算流中元素的个数
        Long collect3 = emps.stream().collect(Collectors.counting());
        System.out.println("collect3 = " + collect3);

        //计算流中数据的求和
        Integer collect4 = emps.stream().collect(Collectors.summingInt(Employee::getAge));
        System.out.println("collect4 = " + collect4);

        //计算流中数据的平均值
        Double collect5 = emps.stream().collect(Collectors.averagingDouble(Employee::getSalary));
        System.out.println("collect5 = " + collect5);

        //收集流中数据的统计值：总个数，最大、小值，平均值
        IntSummaryStatistics collect6 = emps.stream().collect(Collectors.summarizingInt(Employee::getAge));
        System.out.println("collect6 = " + collect6);

        //连接流中每个字符串
        String collect7 = emps.stream().map(Employee::getName).collect(Collectors.joining());
        System.out.println("collect7 = " + collect7);

        //根据比较器选择最大值
        Optional<Employee> collect8 = emps.stream().collect(Collectors.maxBy(Comparator.comparingInt(Employee::getAge)));
        System.out.println("collect8 = " + collect8.get());

        //根据比较器选择最小值
        Optional<Employee> collect9 = emps.stream().collect(Collectors.minBy(Comparator.comparingInt(Employee::getAge)));
        System.out.println("collect9 = " + collect9.get());

        //以某个值为开始累加，逐个执行BinaryOperator 得到最终结果
        Integer collect10 = emps.stream().collect(Collectors.reducing(0, Employee::getAge, Integer::max));
        System.out.println("collect10 = " + collect10);

        //包含另一个收集器，对其结果进行转换
        Integer collect11 = emps.stream().collect(Collectors.collectingAndThen(Collectors.toList(), List::size));
        System.out.println("collect11 = " + collect11);

        //根据某个属性值对流进行分组，属性为k，值为value
        Map<String, List<Employee>> collect12 = emps.stream().collect(Collectors.groupingBy(Employee::getName));
        System.out.println("collect12 = " + collect12);

        //根据true、false进行分组
        Map<Boolean, List<Employee>> collect13 = emps.stream().collect(Collectors.partitioningBy((emp) -> {
            return emp.getAge() == 20;
        }));
        System.out.println("collect13 = " + collect13);


    }
}
