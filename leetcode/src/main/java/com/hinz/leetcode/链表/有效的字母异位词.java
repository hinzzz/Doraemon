package com.hinz.leetcode.链表;

import org.junit.Test;

/**
 * 给定两个字符串 s 和 t ，编写一个函数来判断 t 是否是 s 的字母异位词。
 *
 * 示例 1: 输入: s = "anagram", t = "nagaram" 输出: true
 *
 * 示例 2: 输入: s = "rat", t = "car" 输出: false
 *
 * 说明: 你可以假设字符串只包含小写字母。
 *
 * @author hinzzz
 * @date 2021/11/25 17:05
 * @desc
 */
public class 有效的字母异位词 {

    @Test
    public void fun() {
        System.out.println(isAnagram("aaab", "baaa"));
    }

    public boolean isAnagram(String a, String b) {
        int[] result = new int[26];
        char[] aChars = a.toCharArray();
        for (char aChar : aChars) {
            result[aChar - 'a'] += 1;
        }

        char[] bChars = b.toCharArray();
        for (char bChar : bChars) {
            result[bChar - 'a'] -= 1;
        }

        for (int i : result) {
            if (i != 0) {
                return false;
            }
        }
        return true;
    }
}
