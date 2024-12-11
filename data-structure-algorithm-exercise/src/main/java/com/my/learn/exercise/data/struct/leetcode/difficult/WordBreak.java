package com.my.learn.exercise.data.struct.leetcode.difficult;
/*
 * 创建人：baimiao
 * 创建时间：2024/5/15 11:20


140. 单词拆分 II
困难
相关标签
相关企业
给定一个字符串 s 和一个字符串字典 wordDict ，在字符串 s 中增加空格来构建一个句子，使得句子中所有的单词都在词典中。以任意顺序 返回所有这些可能的句子。

注意：词典中的同一个单词可能在分段中被重复使用多次。



示例 1：
abcd       abc
abc  def   abcd

输入:s = "catsanddog", wordDict = ["cat","cats","and","sand","dog"]
输出:["cats and dog","cat sand dog"]
示例 2：

输入:s = "pineapplepenapple", wordDict = ["apple","pen","applepen","pine","pineapple"]
输出:["pine apple pen apple","pineapple pen apple","pine applepen apple"]
解释: 注意你可以重复使用字典中的单词。
示例 3：

输入:s = "catsandog", wordDict = ["cats","dog","sand","and","cat"]
输出:[]


提示：

1 <= s.length <= 20
1 <= wordDict.length <= 1000
1 <= wordDict[i].length <= 10
s 和 wordDict[i] 仅有小写英文字母组成
wordDict 中所有字符串都 不同
 */

import com.google.common.collect.Lists;
import com.wifiin.common.JSON;

import java.util.*;

public class WordBreak {
    public static void main(String[] args) {
        WordBreak work = new WordBreak();
//        String s = "catsanddog";
//        List<String> wordDict = Lists.newArrayList("cat", "cats", "and", "sand", "dog");
        String s = "aaaaaaaaaaaaaaaaaaa";
        List<String> wordDict = Lists.newArrayList("a","aa","aaa","aaaa","aaaaa","aaaaaa","aaaaaaa","aaaaaaaa","aaaaaaaaa","aaaaaaaaaa","aaaaaaaaaaa","aaaaaaaaaaaa","aaaaaaaaaaaa","aaaaaaaaaaaaaa","aaaaaaaaaaaaaaaa");
        List<String> list = work.wordBreak(s, wordDict);
        System.out.println(JSON.common().toJSON(list));
    }

    private List<String> wordBreak(String s, List<String> wordDict) {
        Set<String> dictSet = new HashSet<>();
        dictSet.addAll(wordDict);

        Set<Integer> dictLen = new HashSet<>();
        for (String wd : wordDict) {
            dictLen.add(wd.length());
        }

        List<Node> result = new ArrayList<>();
        List<Node> queue = new ArrayList<>();
        Node start = new Node(0, "", null);
        queue.add(start);

        int length = s.length();
        int end;
        while (!queue.isEmpty()) {
            Node current = queue.remove(0);
            int val = current.getPosition();
            for (Integer len : dictLen) {
                end = val + len;
                if (end <= length) {
                    String sub = s.substring(val, end);
                    if (dictSet.contains(sub)) {
                        Node node;
                        if (end == length) {
                            node = new Node(end, sub, current);
                            result.add(node);
                        } else if (end < length) {
                            node = new Node(end, sub, current);
                            queue.add(node);
                        }
                    }
                }
            }
        }
        List<String> out = new ArrayList<>();
        if (!result.isEmpty()) {
            List<String> reverse = new ArrayList<>();
            StringBuilder sb = new StringBuilder();
            for (Node n : result) {
                reverse.clear();
                while (n != null) {
                    reverse.add(n.getSub());
                    n = n.getPre();
                }
                Collections.reverse(reverse);

                for (String w : reverse) {
                    sb.append(w).append(" ");
                }
                out.add(sb.toString().trim());
                sb.setLength(0);
            }
        }


        return out;
    }


    private static class Node {
        private Node pre;
        private int position;

        private String sub;

        public Node(int position, String sub, Node pre) {
            this.position = position;
            this.sub = sub;
            this.pre = pre;
        }

        public Node getPre() {
            return pre;
        }

        public int getPosition() {
            return position;
        }

        public String getSub() {
            return sub;
        }
    }
}
