package com.hinz.leetcode;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

/**
 * @author hinzzz
 * @date 2021/9/8 16:43
 * @desc
 */
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
        List<String> names = new ArrayList<String>() {{
            add("11");
            add("22");
            add("33");
        }};

        List subList = names.subList(0, 2);
        System.out.println(subList);
        subList.add("44");
        System.out.println("subList = " + subList);
        System.out.println("names = " + names);
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
}

