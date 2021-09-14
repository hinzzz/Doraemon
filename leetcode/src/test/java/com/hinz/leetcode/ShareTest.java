package com.hinz.leetcode;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

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


    @Test
    public void logTest(){
        log.info("aaa");
        log.debug("aaa:{}","bb");
        log.warn("aaa");
    }
}

/**
 *create table smth1 (
 *  　　id int auto_increment ,
 *  　　ver int(11) default null,
 * 　　 content varchar(1000) not null,
 *  　　intro varchar(1000) not null,
 *  　　primary key(id),
 *  　　key idver(id,ver)
 * )engine = innodb default charset = utf8;
 *
 *
 *
 * create procedure smthTest1()
 * begin
 * 　　declare num int default 100001;
 * 　　while num < 1000000 do
 * 　　set num := num +1;
 * 　　insert into smth1 values (num ,num,'我是*****','我是谁');
 * 　　end while ;
 * end;
 */