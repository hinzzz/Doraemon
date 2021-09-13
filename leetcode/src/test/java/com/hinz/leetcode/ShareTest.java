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

    @Test
    public void subList(){
        List<Integer> list = new ArrayList<>();
        list.add(1);
        list.add(2);
        list.add(3);
        //ArrayList<Integer> cast = (ArrayList)list;
        //ArrayList<Integer> subList = (ArrayList)list.subList(0, 2);
        List<Integer> subList = list.subList(0, 2);
        //cast.add(4);
        subList.add(4);
    }
}

