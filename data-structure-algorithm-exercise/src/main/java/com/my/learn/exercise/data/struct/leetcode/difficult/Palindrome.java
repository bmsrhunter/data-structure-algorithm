package com.my.learn.exercise.data.struct.leetcode.difficult;
/*
 * 创建人：baimiao
 * 创建时间：2024/5/17 17:23
 564. 寻找最近的回文数
困难
相关标签
相关企业
提示
给定一个表示整数的字符串 n ，返回与它最近的回文整数（不包括自身）。如果不止一个，返回较小的那个。

“最近的”定义为两个整数差的绝对值最小。



示例 1:

输入: n = "123"
输出: "121"
示例 2:

输入: n = "1"
输出: "0"
解释: 0 和 2是最近的回文，但我们返回最小的，也就是 0。


提示:

1 <= n.length <= 18
n 只由数字组成
n 不含前导 0
n 代表在 [1, 10^18 - 1] 范围内的整数
 */

public class Palindrome {


    public static void main(String[] args) {
        String n = "";
        Palindrome palindrome = new Palindrome();
        String result = palindrome.nearestPalindromic(n);
        System.out.println(result);
    }

    public String nearestPalindromic(String n) {

        return null;
    }
}
