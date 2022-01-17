package com.hinz.leetcode.算法;

import java.util.Arrays;

/**
 * @author hinzzz
 * @date 2021/9/29 16:26
 * @desc
 */
public class 两数相加 {
    public ListNode addTwoNumbers(ListNode l1, ListNode l2) {

        int[] a1 = {};
        int[] a2 = {};
        while (l1.next != null){
            a1[a1.length] = l1.val;
        }
        while (l2.next != null){
            a1[a2.length] = l2.val;
        }

        return null;
    }
}


class ListNode {
    int val;
    ListNode next;

    ListNode() {
    }

    ListNode(int val) {
        this.val = val;
    }

    ListNode(int val, ListNode next) {
        this.val = val;
        this.next = next;
    }
}
