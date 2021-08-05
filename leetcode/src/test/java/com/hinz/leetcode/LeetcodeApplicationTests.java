package com.hinz.leetcode;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;

//@SpringBootTest
class LeetcodeApplicationTests {

    @Test
    void contextLoads() {
    }


    @Test
    void fun(){
        /*int[] a = {2,5,5,11};
        int b = 10;
        Arrays.stream(LeetcodeApplicationTests.twoSum(a, b)).forEach(System.out::println);*/

        int[] a = {2,5,5,11};
        System.out.println(LeetcodeApplicationTests.removeDuplicates(a));
    }


    public static int removeDuplicates(int[] nums) {
        int index = 0;
        for(int i = 1; i < nums.length; ++i){
            if(nums[i] != nums[index]){
                nums[++index] = nums[i];
            }
        }
        return index + 1;
    }

    public static int[] twoSum(int[] nums, int target) {
        int[] result =new int[2];
        if(nums != null){
            for (int i = 0; i < nums.length; i++) {
                for (int i1 = 1; i1 < nums.length; i1++) {
                    if(nums[i]+nums[i1]==target && i!=i1){
                        result[0] = i;
                        result[1] = i1;
                        System.out.println("nums[i] = " + i);
                        System.out.println("nums[i1] = " + i1);
                        return result;
                    }
                }
            }
        }
        return result;
    }
}
