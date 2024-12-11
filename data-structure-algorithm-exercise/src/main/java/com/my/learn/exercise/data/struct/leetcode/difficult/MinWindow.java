package com.my.learn.exercise.data.struct.leetcode.difficult;
/*
 * 创建人：baimiao
 * 创建时间：2024/3/25 9:47
 76. 最小覆盖子串
尝试过
困难
相关标签
相关企业
提示
给你一个字符串 s 、一个字符串 t 。返回 s 中涵盖 t 所有字符的最小子串。如果 s 中不存在涵盖 t 所有字符的子串，则返回空字符串 "" 。



注意：

对于 t 中重复字符，我们寻找的子字符串中该字符数量必须不少于 t 中该字符数量。
如果 s 中存在这样的子串，我们保证它是唯一的答案。


示例 1：

输入：s = "ADOBECODEBANC", t = "ABC"
输出："BANC"
解释：最小覆盖子串 "BANC" 包含来自字符串 t 的 'A'、'B' 和 'C'。
示例 2：

输入：s = "a", t = "a"
输出："a"
解释：整个字符串 s 是最小覆盖子串。
示例 3:

输入: s = "a", t = "aa"
输出: ""
解释: t 中两个字符 'a' 均应包含在 s 的子串中，
因此没有符合条件的子字符串，返回空字符串。


提示：

m == s.length
n == t.length
1 <= m, n <= 10^5
s 和 t 由英文字母组成


进阶：你能设计一个在 o(m+n) 时间内解决此问题的算法吗？
 */

import java.util.HashMap;
import java.util.Map;

public class MinWindow {

    public static void main(String[] args) {
        MinWindow mw = new MinWindow();
        String s = "abc";
        String t = "abd";
//        String s = "barbatcduabbacd";
//        String t = "abdc";
        String result = mw.find3(s, t);
        System.out.println(result);
    }

    private String find3(String s, String t) {
        int tLen = t.length();
        int sLen = s.length();
        if (sLen < tLen) {
            return "";
        }

        int left = 0, right = 0;
        Map<Character, Integer> charCount = new HashMap<>();
        Map<Character, Integer> overflowMap = new HashMap<>();
        char[] tArray = t.toCharArray();
        char[] sArray = s.toCharArray();
        char temp;
        Integer count;
        for (int i = 0; i < tLen; i++) {
            temp = tArray[i];
            count = charCount.get(temp);
            if (count == null) {
                charCount.put(temp, 1);
            } else {
                charCount.put(temp, count + 1);
            }
        }
        Integer countOver;
        int total = 0;//总合格的char数
        int minLength = sLen;
        int bLeft = 0, bRight = 0;

        while (right < sLen) {
            temp = sArray[right];
            count = charCount.get(temp);
            if (count == null) {
                if (overflowMap.isEmpty()) {
                    left++;
                }
            } else {
                countOver = overflowMap.get(temp);
                countOver = countOver == null ? 0 : countOver;
                int sum = countOver + 1;
                overflowMap.put(temp, sum);
                if (sum <= count) {
                    total++;
                }
            }
            if (total == tLen) {//缩
                for (int i = left; i <= right; i++) {
                    temp = sArray[i];
                    count = charCount.get(temp);
                    if (count == null) {
                        left++;
                    } else {
                        countOver = overflowMap.get(temp);
                        if (countOver > count) {
                            overflowMap.put(temp, countOver - 1);
                            left++;
                        } else {
                            break;
                        }
                    }
                }

                int length = right - left + 1;
                if (minLength >= length) {
                    minLength = length;
                    bLeft = left;
                    bRight = right;
                    if (minLength == tLen) {
                        break;
                    }
                }

                countOver = overflowMap.get(sArray[left]);
                overflowMap.put(sArray[left], countOver - 1);
                total--;
                left++;
            }

            right++;
        }
        if (overflowMap.size() < charCount.size()) {
            return "";
        }
        if (bRight - bLeft + 1 < tLen) {
            return "";
        }
        return s.substring(bLeft, bRight + 1);
    }
}
