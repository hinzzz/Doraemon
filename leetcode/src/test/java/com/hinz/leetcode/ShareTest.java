package com.hinz.leetcode;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

import java.util.*;

/**
 * @author hinzzz
 * @date 2021/9/8 16:43
 * @desc
 */
@Slf4j
public class ShareTest {

    /**
     * 1、为什么要求谨慎使用 ArrayList 中的 subList 方法？
     */
    @Test
    public void subListCast(){
        List<String> names = new ArrayList<String>() {{
            add("11");
            add("22");
            add("33");
            add("44");
        }};

        List subList = names.subList(0, 2);
        System.out.println(subList);

        ArrayList subList1 = (ArrayList) names.subList(0, 2);
        System.out.println(subList);
    }


    /**
     * 2、对结果集进行操作
     */
    @Test
    public void subListEdit(){
        List<Integer> list = new ArrayList<Integer>() {{
            add(1);
            add(2);
            add(3);
        }};

        List subList = list.subList(0, 2);
        subList.add(4);
        System.out.println("subList = " + subList);//1,2,3
        System.out.println("list = " + list);//1,2,3,4
    }

    /**
     * 3、对原list进行操作
     */
    @Test
    public void sourceListEdit(){
        List<String> sourceList = new ArrayList<String>() {{
            add("11");
            add("22");
            add("33");
        }};

        List subList = sourceList.subList(0, 2);
        System.out.println(subList);
        sourceList.add("44");
        System.out.println("subList = " + subList);
        System.out.println("names = " + sourceList);
    }


    @Test
    public void Comparator (){
        List<Student> list = new ArrayList<Student>(){{
            add(Student.builder().age(14).name("z3").build());
            add(Student.builder().age(11).name("l4").build());
            add(Student.builder().age(10).name("w5").build());
            add(Student.builder().age(13).name("z7").build());
            add(Student.builder().age(11).name("s8").build());
            add(Student.builder().age(11).name("s8").build());
            add(Student.builder().age(11).name("s8").build());
            add(Student.builder().age(13).name("s8").build());
            add(Student.builder().age(11).name("s8").build());
            add(Student.builder().age(14).name("s8").build());
            add(Student.builder().age(11).name("s8").build());

        }};

        Collections.sort(list,(a,b)->{
           return a.getAge() > b.getAge() ? 1 : -1;
        });
        System.out.println("list = " + list);

        Student[] students = list.toArray(new Student[list.size()]);
        Arrays.sort(students,(a,b)->{
            return a.getAge() > b.getAge() ? 1 : -1;
        });
        Arrays.stream(students).forEach(System.out::println);
    }
}

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
class Student {
    private Integer age;
    private String name;
}

