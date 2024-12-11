package com.my.learn.exercise.data.struct.leetcode.difficult;
/*
 * 创建人：baimiao
 * 创建时间：2024/7/12 13:37
 *10. 正则表达式匹配
困难
相关标签
相关企业
给你一个字符串 s 和一个字符规律 p，请你来实现一个支持 '.' 和 '*' 的正则表达式匹配。

'.' 匹配任意单个字符
'*' 匹配零个或多个前面的那一个元素
所谓匹配，是要涵盖 整个 字符串 s的，而不是部分字符串。


示例 1：

输入：s = "aa", p = "a"
输出：false
解释："a" 无法匹配 "aa" 整个字符串。
示例 2:

输入：s = "aa", p = "a*"
输出：true
解释：因为 '*' 代表可以匹配零个或多个前面的那一个元素, 在这里前面的元素就是 'a'。因此，字符串 "aa" 可被视为 'a' 重复了一次。
示例 3：

输入：s = "ab", p = ".*"
输出：true
解释：".*" 表示可匹配零个或多个（'*'）任意字符（'.'）。


提示：

1 <= s.length <= 20
1 <= p.length <= 20
s 只包含从 a-z 的小写字母。
p 只包含从 a-z 的小写字母，以及字符 . 和 *。
保证每次出现字符 * 时，前面都匹配到有效的字符
 */

import java.util.ArrayList;
import java.util.List;

public class RegMatchH10 {
    public static void main(String[] args) {
        RegMatchH10 reg = new RegMatchH10();
//        String s = "mississippi";
//        String p = "mis*is*ip*.";
        String s = "bbbba";
        String p = ".*a*a";
        boolean result = reg.isMatch(s, p);
        System.out.println(result);
    }

    //  a bccab a bcccb aba c
    //  a.b.c.b b* c* .* b* c==compress==>a.b.c.b .* c
    //  .* b.c.b a*  c
    //  b* b.c.b .*  c
    //  1 0 -1 1 0 -1  0 0 0 -1 1 1 1 -1 0
    //segment,compress

    private int length = 0;

    public boolean isMatch(String s, String p) {
        length = s.length();
        Node node = parse(p);
        return match(s.toCharArray(), node, 0);
    }


    //有限自动机
    //最左做小匹配，遇到不成功的就回退，回退过程中有不符合条件的就失败
    // 1. 优先匹配level 1 ,成功后匹配前面的level 0 or -1,否则失败（最小匹配原则）
    // 2. 上一步匹配不成功则向前重新匹配，前面的level 1 也相应的向后匹配，以此类推
    private boolean match(char[] s, Node node, int start) {
        int level = node.getLevel();
        if (level == 1) {
            int m = matchPrecise(s, node, start);
            if (m == length) {
                return false;
            }
            node.setPos(m);
            int size = node.getList().size();
            int end = m + size;
            Node pre = node.getPre();
            start = pre == null ? 0 : pre.getPos();
            boolean q = matchVague(s, pre, start, m);
            if (q) {
                Node next = node.getNext();
                if (next == null) {
                    return true;
                }
                next.setPos(end);
                return match(s, next, end);
            } else {
                //backtrack
                if (pre != null) {
                    Node pp = pre.getPre();
                    if (pp != null) {
                        return match(s, pp, pp.getPos() + 1);
                    }
                }
                return false;
            }
        } else if (level == 0) {
            Node next = node.getNext();
            if (next == null) {
                boolean m = matchVague(s, node, start, length);
                if (m) {
                    return true;
                } else {
                    Node pre = node.getPre();
                    if (pre == null) {
                        return false;
                    }
                    return match(s, pre, pre.getPos() + 1);
                }
            }
            return match(s, next, start);
        } else {
            Node next = node.getNext();
            if (next == null) {
                return true;
            }
            return match(s, next, start);
        }
    }


    //模糊匹配
    private boolean matchVague(char[] s, Node node, int start, int end) {
        if (node == null) {
            return end == start;
        }
        if(node.getLevel()==-1){
            return true;
        }
        List<Character> chars = node.getList();
        int size = chars.size();
        int tmp = 0;
        int anchor = start;
        int gap = end - start;
        for (int i = 0; i < size; i++) {
            for (int j = anchor; j < end; j++) {
                if (s[j] != chars.get(i)) {
                    break;
                } else {
                    anchor++;
                    tmp++;
                }
            }
        }
        return gap == tmp;
    }


    private int matchPrecise(char[] s, Node node, int start) {
        List<Character> chars = node.getList();
        int size = chars.size();
        Node next = node.getNext();
        int i;
        if (next == null) {
            i = length - size;
            if (i < 0) {
                return length;
            }
        } else {
            i = start;
        }
        int j = 0, pos = i;
        while (i < length && j < size) {
            if (chars.get(j) == '.' || s[i] == chars.get(j)) {
                i++;
                j++;
                if (j == size) {
                    break;
                }
            } else {
                pos++;
                i = pos;
                j = 0;
            }
        }
        if (j == size) {
            return pos;
        } else {
            return length;
        }
    }


    private Node parse(String pattern) {
        Node head = null;
        Node curr = null;
        for (Character c : pattern.toCharArray()) {
            if (head == null) {
                head = new Node();
                head.setCh(c);
                head.add(c);
                head.setLevel(1);
                curr = head;
            } else {
                if (c == '*') {
                    if (curr.getLevel() > 0) {// a .
                        if (curr.getCh() == '.') {
                            curr.setLevel(-1);
                        } else {
                            curr.setLevel(0);
                        }
                        curr = compress(curr);
                    }
                } else {
                    Node n = new Node();
                    n.setLevel(1);
                    n.setCh(c);
                    n.add(c);
                    n.setPre(curr);
                    curr.setNext(n);
                    curr = n;
                }
            }
        }

        curr = head;
        while (curr != null) {
            int level = curr.getLevel();
            if (level == 1) {
                Node next = curr.getNext();
                if (next != null) {
                    if (next.getLevel() == 1) {
                        curr.setCh(next.getCh());
                        curr.add(next.getCh());
                        if (next.getNext() != null) {
                            next.getNext().setPre(curr);
                        }
                        curr.setNext(next.getNext());
                    } else {
                        curr = next;
                    }
                } else {
                    break;
                }
            } else {
                curr = curr.getNext();
            }
        }
        return head;
    }

    private Node compress(Node node) {
        int level = node.getLevel();
        Node pre = node.getPre();
        if (pre != null) {
            if (level < 0) {
                if (pre.getLevel() == 1) {
                    return node;
                } else {
                    pre.setLevel(-1);
                    pre.setCh('.');
                    pre.clear();
                }
            } else {
                char val = node.getCh();
                if (pre.getLevel() == -1) {
                    pre.setNext(null);
                } else if (pre.getLevel() == 0) {
                    if (pre.getCh() == val) {
                        pre.setNext(null);
                    } else {
                        pre.add(val);
                        pre.setCh(val);
                        pre.setNext(null);
                    }
                } else {
                    return node;
                }
            }
            return pre;
        } else {
            return node;
        }
    }

    //.* -1 a* 0 a 1 combine a*b*c*==>(abc)*

    public static class Node {
        private char ch;
        private List<Character> list;

        private int pos;
        private int level;
        private Node pre;
        private Node next;


        public List<Character> getList() {
            return list;
        }

        public void setList(List<Character> list) {
            this.list = list;
        }

        public void add(Character c) {
            if (list == null) {
                list = new ArrayList<>();
            }
            list.add(c);
        }

        public void remove() {
            list.remove(list.size() - 1);
        }

        public char getCh() {
            return ch;
        }

        public void setCh(char ch) {
            this.ch = ch;
        }

        public int getLength() {
            return list.size();
        }

        public int getLevel() {
            return level;
        }

        public void setLevel(int level) {
            this.level = level;
        }

        public Node getPre() {
            return pre;
        }

        public void setPre(Node pre) {
            this.pre = pre;
        }

        public Node getNext() {
            return next;
        }

        public void setNext(Node next) {
            this.next = next;
        }

        public void clear() {
            list.clear();
        }

        public int getPos() {
            return pos;
        }

        public void setPos(int pos) {
            this.pos = pos;
        }
    }


}
