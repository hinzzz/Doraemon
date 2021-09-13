package com.hinz.leetcode;

import org.junit.jupiter.api.Test;

import java.util.*;

//@SpringBootTest
class LeetcodeApplicationTests {

    @Test
    void contextLoads() {
    }


    @Test
    void fun() {
        /*int[] a = {2,5,5,11};
        int b = 10;
        Arrays.stream(LeetcodeApplicationTests.twoSum(a, b)).forEach(System.out::println);*/

        int[] a = {2, 5, 5, 11};
        System.out.println(LeetcodeApplicationTests.removeDuplicates(a));
    }


    public static int removeDuplicates(int[] nums) {
        int index = 0;
        for (int i = 1; i < nums.length; ++i) {
            if (nums[i] != nums[index]) {
                nums[++index] = nums[i];
            }
        }
        return index + 1;
    }

    public static int[] twoSum(int[] nums, int target) {
        int[] result = new int[2];
        if (nums != null) {
            for (int i = 0; i < nums.length; i++) {
                for (int i1 = 1; i1 < nums.length; i1++) {
                    if (nums[i] + nums[i1] == target && i != i1) {
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

    @Test
    public void testlengthOfLongestSubstring(){
        System.out.println(count("abcabcbb"));
    }

    public int count(String s) {
//        if(s==null || s.equals("")) return 0;
//        if(s.length()==1){
//            return 1;
//        }
//        char[] chars = s.toCharArray();
//        int count = 0;
//        int max = 0;
//        Set<Character> set = new HashSet<>();
//        for (int i = 0; i < chars.length; i++) {
//            if (set.add(chars[i])) {
//                count++;
//            } else {
//                max = max > count ? max : count;
//                count = 0;
//                set = new HashSet<>();
//            }
//            for (int j = i + 1; j < chars.length; j++) {
//                if (set.add(chars[j])) {
//                    count++;
//                } else {
//                    max = max > count ? max : count;
//                    count = 0;
//                    set = new HashSet<>();
//                    break;
//                }
//            }
//        }
//        return max;

//        if (inputStr == null)
//            return 0;
//        char[] arr = inputStr.toCharArray();
//        int max = 0;
//        Map<String, Integer> charMap = new LinkedHashMap<>(arr.length);
//        for (int index = 0; index < arr.length; index++) {
//            String charkey = String.valueOf(arr[index]);
//            if (charMap.containsKey(charkey)) {
//                max = charMap.size() > max ? charMap.size() : max;
//                index -=2;
//                charMap.clear();
//
//            } else {
//                charMap.put(charkey, index);
//            }
//        }
//        return charMap.size() > max ? charMap.size() : max;


        //abcabcbb
        int len = s.length();
        Map<Character,Integer> map = new HashMap<>();
        int tmp = 0;
        int max = 0;
        for (int i = 0; i < len; i++) {
            char c = s.charAt(i);
            if(map.containsKey(c)){
                //
                tmp = Math.max(map.get(c)+1,tmp);
            }
            map.put(c,i);
            max = Math.max(max,i - tmp+1);
        }
        return max;
    }
}
