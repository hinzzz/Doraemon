package com.hinz.problems;

import java.util.HashSet;

public class ArrVSHash {
    //算法复杂度为 O(n)
    public static void test1(){
        int[] arr = new int[100000];
        for(int i = 0;i < arr.length;i++){
            arr[i] = i + 1;
        }
        long start = System.currentTimeMillis();
        for(int j = 1; j<=100000;j++){
            int temp = j;
            for(int i = 0;i < arr.length;i++){
                if(temp == arr[i]){
                    break;
                }
            }
        }
        long end = System.currentTimeMillis();
        System.out.println("arr time： " + (end - start)); //time： 823
    }

    //算法复杂度为 O(1)
    public static void test2(){
        HashSet<Integer> set = new HashSet<>(100000);
        for(int i = 0;i < 100000;i++){
            set.add(i + 1);
        }
        long start = System.currentTimeMillis();
        for(int j = 1; j<=100000;j++) {
            int temp = j;
            boolean contains = set.contains(temp);
        }
        long end = System.currentTimeMillis();
        System.out.println("hash time： " + (end - start)); //time： 5
    }


    public static void main(String[] args) {
        ArrVSHash.test1();
        ArrVSHash.test2();
    }

}
