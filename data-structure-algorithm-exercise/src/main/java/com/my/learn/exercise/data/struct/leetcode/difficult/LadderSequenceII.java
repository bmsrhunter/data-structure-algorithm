package com.my.learn.exercise.data.struct.leetcode.difficult;
/*
 * 创建人：baimiao
 * 创建时间：2024/5/5 13:19
126. 单词接龙 II
困难
相关标签
相关企业
按字典 wordList 完成从单词 beginWord 到单词 endWord 转化，一个表示此过程的 转换序列 是形式上像 beginWord -> s1 -> s2 -> ... -> sk 这样的单词序列，并满足：

每对相邻的单词之间仅有单个字母不同。
转换过程中的每个单词 si（1 <= i <= k）必须是字典 wordList 中的单词。注意，beginWord 不必是字典 wordList 中的单词。
sk == endWord
给你两个单词 beginWord 和 endWord ，以及一个字典 wordList 。请你找出并返回所有从 beginWord 到 endWord 的 最短转换序列 ，如果不存在这样的转换序列，返回一个空列表。每个序列都应该以单词列表 [beginWord, s1, s2, ..., sk] 的形式返回。



示例 1：

输入：beginWord = "hit", endWord = "cog", wordList = ["hot","dot","dog","lot","log","cog"]
输出：[["hit","hot","dot","dog","cog"],["hit","hot","lot","log","cog"]]
解释：存在 2 种最短的转换序列：
"hit" -> "hot" -> "dot" -> "dog" -> "cog"
* hit->hig->cig->cog
"hit" -> "hot" -> "lot" -> "log" -> "cog"
示例 2：

输入：beginWord = "hit", endWord = "cog", wordList = ["hot","dot","dog","lot","log"]
输出：[]
解释：endWord "cog" 不在字典 wordList 中，所以不存在符合要求的转换序列。


提示：

1 <= beginWord.length <= 5
endWord.length == beginWord.length
1 <= wordList.length <= 500
wordList[i].length == beginWord.length
beginWord、endWord 和 wordList[i] 由小写英文字母组成
beginWord != endWord
wordList 中的所有单词 互不相同
*
*       baaa         bacd
*              baad
* aaaa  aaad         btad   btcd

*       ataa   atca  btca
*

        aaar   aaer  aver   uver   uvhr
 */


import com.google.common.collect.Lists;
import com.wifiin.common.JSON;

import java.util.*;

public class LadderSequenceII {
    //[["red","ted","tad","tax"],["red","ted","tex","tax"],["red","rex","tex","tax"]]
    public static void main5(String[] args) {
        LadderSequenceII sequence = new LadderSequenceII();
        String beginWord = "hit";
        String endWord = "cog";
        List<String> wordList = Lists.newArrayList("hot", "dot", "dog", "lot", "log", "cog");
        List<List<String>> result = sequence.ladderLength(beginWord, endWord, wordList);
        System.out.println(JSON.common().toJSON(result));
    }

    public static void main2(String[] args) {
        LadderSequenceII sequence = new LadderSequenceII();
        String beginWord = "game";
        String endWord = "thee";
        List<String> wordList = Lists.newArrayList("frye", "heat", "tree", "thee", "game", "free", "hell", "fame", "faye");
        List<List<String>> result = sequence.ladderLength(beginWord, endWord, wordList);
        System.out.println(JSON.common().toJSON(result));
    }

    public static void main(String[] args) {
        LadderSequenceII sequence = new LadderSequenceII();
        String beginWord = "aaaaa";
        String endWord = "ggggg";
        List<String> wordList = Lists.newArrayList("aaaaa", "caaaa", "cbaaa", "daaaa", "dbaaa", "eaaaa", "ebaaa", "faaaa", "fbaaa", "gaaaa", "gbaaa", "haaaa", "hbaaa", "iaaaa", "ibaaa", "jaaaa", "jbaaa", "kaaaa", "kbaaa", "laaaa", "lbaaa", "maaaa", "mbaaa", "naaaa", "nbaaa", "oaaaa", "obaaa", "paaaa", "pbaaa", "bbaaa", "bbcaa", "bbcba", "bbdaa", "bbdba", "bbeaa", "bbeba", "bbfaa", "bbfba", "bbgaa", "bbgba", "bbhaa", "bbhba", "bbiaa", "bbiba", "bbjaa", "bbjba", "bbkaa", "bbkba", "bblaa", "bblba", "bbmaa", "bbmba", "bbnaa", "bbnba", "bboaa", "bboba", "bbpaa", "bbpba", "bbbba", "abbba", "acbba", "dbbba", "dcbba", "ebbba", "ecbba", "fbbba", "fcbba", "gbbba", "gcbba", "hbbba", "hcbba", "ibbba", "icbba", "jbbba", "jcbba", "kbbba", "kcbba", "lbbba", "lcbba", "mbbba", "mcbba", "nbbba", "ncbba", "obbba", "ocbba", "pbbba", "pcbba", "ccbba", "ccaba", "ccaca", "ccdba", "ccdca", "cceba", "cceca", "ccfba", "ccfca", "ccgba", "ccgca", "cchba", "cchca", "cciba", "ccica", "ccjba", "ccjca", "cckba", "cckca", "cclba", "cclca", "ccmba", "ccmca", "ccnba", "ccnca", "ccoba", "ccoca", "ccpba", "ccpca", "cccca", "accca", "adcca", "bccca", "bdcca", "eccca", "edcca", "fccca", "fdcca", "gccca", "gdcca", "hccca", "hdcca", "iccca", "idcca", "jccca", "jdcca", "kccca", "kdcca", "lccca", "ldcca", "mccca", "mdcca", "nccca", "ndcca", "occca", "odcca", "pccca", "pdcca", "ddcca", "ddaca", "ddada", "ddbca", "ddbda", "ddeca", "ddeda", "ddfca", "ddfda", "ddgca", "ddgda", "ddhca", "ddhda", "ddica", "ddida", "ddjca", "ddjda", "ddkca", "ddkda", "ddlca", "ddlda", "ddmca", "ddmda", "ddnca", "ddnda", "ddoca", "ddoda", "ddpca", "ddpda", "dddda", "addda", "aedda", "bddda", "bedda", "cddda", "cedda", "fddda", "fedda", "gddda", "gedda", "hddda", "hedda", "iddda", "iedda", "jddda", "jedda", "kddda", "kedda", "lddda", "ledda", "mddda", "medda", "nddda", "nedda", "oddda", "oedda", "pddda", "pedda", "eedda", "eeada", "eeaea", "eebda", "eebea", "eecda", "eecea", "eefda", "eefea", "eegda", "eegea", "eehda", "eehea", "eeida", "eeiea", "eejda", "eejea", "eekda", "eekea", "eelda", "eelea", "eemda", "eemea", "eenda", "eenea", "eeoda", "eeoea", "eepda", "eepea", "eeeea", "ggggg", "agggg", "ahggg", "bgggg", "bhggg", "cgggg", "chggg", "dgggg", "dhggg", "egggg", "ehggg", "fgggg", "fhggg", "igggg", "ihggg", "jgggg", "jhggg", "kgggg", "khggg", "lgggg", "lhggg", "mgggg", "mhggg", "ngggg", "nhggg", "ogggg", "ohggg", "pgggg", "phggg", "hhggg", "hhagg", "hhahg", "hhbgg", "hhbhg", "hhcgg", "hhchg", "hhdgg", "hhdhg", "hhegg", "hhehg", "hhfgg", "hhfhg", "hhigg", "hhihg", "hhjgg", "hhjhg", "hhkgg", "hhkhg", "hhlgg", "hhlhg", "hhmgg", "hhmhg", "hhngg", "hhnhg", "hhogg", "hhohg", "hhpgg", "hhphg", "hhhhg", "ahhhg", "aihhg", "bhhhg", "bihhg", "chhhg", "cihhg", "dhhhg", "dihhg", "ehhhg", "eihhg", "fhhhg", "fihhg", "ghhhg", "gihhg", "jhhhg", "jihhg", "khhhg", "kihhg", "lhhhg", "lihhg", "mhhhg", "mihhg", "nhhhg", "nihhg", "ohhhg", "oihhg", "phhhg", "pihhg", "iihhg", "iiahg", "iiaig", "iibhg", "iibig", "iichg", "iicig", "iidhg", "iidig", "iiehg", "iieig", "iifhg", "iifig", "iighg", "iigig", "iijhg", "iijig", "iikhg", "iikig", "iilhg", "iilig", "iimhg", "iimig", "iinhg", "iinig", "iiohg", "iioig", "iiphg", "iipig", "iiiig", "aiiig", "ajiig", "biiig", "bjiig", "ciiig", "cjiig", "diiig", "djiig", "eiiig", "ejiig", "fiiig", "fjiig", "giiig", "gjiig", "hiiig", "hjiig", "kiiig", "kjiig", "liiig", "ljiig", "miiig", "mjiig", "niiig", "njiig", "oiiig", "ojiig", "piiig", "pjiig", "jjiig", "jjaig", "jjajg", "jjbig", "jjbjg", "jjcig", "jjcjg", "jjdig", "jjdjg", "jjeig", "jjejg", "jjfig", "jjfjg", "jjgig", "jjgjg", "jjhig", "jjhjg", "jjkig", "jjkjg", "jjlig", "jjljg", "jjmig", "jjmjg", "jjnig", "jjnjg", "jjoig", "jjojg", "jjpig", "jjpjg", "jjjjg", "ajjjg", "akjjg", "bjjjg", "bkjjg", "cjjjg", "ckjjg", "djjjg", "dkjjg", "ejjjg", "ekjjg", "fjjjg", "fkjjg", "gjjjg", "gkjjg", "hjjjg", "hkjjg", "ijjjg", "ikjjg", "ljjjg", "lkjjg", "mjjjg", "mkjjg", "njjjg", "nkjjg", "ojjjg", "okjjg", "pjjjg", "pkjjg", "kkjjg", "kkajg", "kkakg", "kkbjg", "kkbkg", "kkcjg", "kkckg", "kkdjg", "kkdkg", "kkejg", "kkekg", "kkfjg", "kkfkg", "kkgjg", "kkgkg", "kkhjg", "kkhkg", "kkijg", "kkikg", "kkljg", "kklkg", "kkmjg", "kkmkg", "kknjg", "kknkg", "kkojg", "kkokg", "kkpjg", "kkpkg", "kkkkg", "ggggx", "gggxx", "ggxxx", "gxxxx", "xxxxx", "xxxxy", "xxxyy", "xxyyy", "xyyyy", "yyyyy", "yyyyw", "yyyww", "yywww", "ywwww", "wwwww", "wwvww", "wvvww", "vvvww", "vvvwz", "avvwz", "aavwz", "aaawz", "aaaaz");
        List<List<String>> result = sequence.ladderLength(beginWord, endWord, wordList);
        System.out.println(JSON.common().toJSON(result));
    }

    public static void main3(String[] args) {
        LadderSequenceII sequence = new LadderSequenceII();
        String beginWord = "aaaa";
        String endWord = "btcd";
        List<String> wordList = Lists.newArrayList("baaa", "bacd", "baad", "aaad", "btad", "btcd", "ataa", "atca", "btca", "aaar", "aaer", "aver", "uver", "uvhr");
        List<List<String>> result = sequence.ladderLength(beginWord, endWord, wordList);
        System.out.println(JSON.common().toJSON(result));
    }


    private List<List<String>> ladderLength(String beginWord, String endWord, List<String> wordList) {
        List<List<String>> out = new ArrayList<>();
        if (!wordList.contains(endWord)) {
            return out;
        }
        int length = beginWord.length();
        if (length == 1) {
            List<String> in = new ArrayList<>();
            in.add(beginWord);
            in.add(endWord);
            out.add(in);
            return out;
        }

        Map<String, Set<String>> map = new HashMap<>();
        Map<String, Node> nodeMap = new HashMap<>();
        Node result = null;
        List<Node> queue = new ArrayList<>();
        Set<String> temp = new HashSet<>();
        Node start = new Node(beginWord, null);
        queue.add(start);
        Set<String> set = new HashSet<>(wordList);
        set.remove(beginWord);
        int height = Integer.MAX_VALUE;
        int level = 0;
        while (!queue.isEmpty()) {
            Node current = queue.remove(0);
            int depth = current.getDepth();
            String val = current.getValue();
            if (depth < height - 1) {
                if (depth > level) {
                    set.removeAll(temp);
                    temp.clear();
                    level = depth;
                }
                for (String w : set) {
                    if (next(val, w)) {
                        if (w.equals(endWord)) {
                            result = new Node(w, current);
                            height = result.getDepth();

                            Set<String> item = map.get(w);
                            if (item == null) {
                                item = new HashSet<>();
                                item.add(val);
                                map.put(w, item);

                                nodeMap.put(w, result);

                            } else {
//                                if (!item.contains(val)) {
                                Node n = nodeMap.get(w);
                                n.addPre(nodeMap.get(val));
//                                }
                            }
                            break;
                        } else {
                            Set<String> item = map.get(w);
                            if (item == null) {
                                item = new HashSet<>();
                                item.add(val);
                                map.put(w, item);

                                Node mid = new Node(w, current);
                                nodeMap.put(w, mid);
                                queue.add(mid);
                                temp.add(w);
                            } else {
//                                if (!item.contains(val)) {
                                Node n = nodeMap.get(w);
                                n.addPre(nodeMap.get(val));
                                temp.add(w);
//                                }
                            }
                        }
                    }
                }
            } else if (depth == height - 1) {
                if (next(val, endWord)) {
                    Set<String> item = map.get(endWord);
//                    if (!item.contains(val)) {
                    Node n = nodeMap.get(endWord);
                    n.addPre(nodeMap.get(val));
//                    }
                }
            } else {
                break;
            }
        }
        if (result != null) {
            List<String> res = new ArrayList<>();
            dfs(result, res);
            List<String> anchor = new ArrayList<>();
            List<String> l = null;
            for (int i = 0; i < res.size(); i++) {
                String val = res.get(i);
                if (val.equals(beginWord)) {
                    l.add(beginWord);
                    if (l.size() == height + 1) {
                        List<String> revers = new ArrayList<>();
                        anchor.clear();
                        anchor.addAll(l);
                        revers.addAll(l);
                        Collections.reverse(revers);
                        out.add(revers);
                        l.clear();
                    } else {
                        int diff = anchor.size() - l.size();
                        l.addAll(0, anchor.subList(0, diff));
                        List<String> revers = new ArrayList<>();
                        revers.addAll(l);
                        Collections.reverse(revers);
                        out.add(revers);
                        l.clear();
                    }
                } else {
                    if (l == null) {
                        l = new ArrayList<>();
                    } else if (l.isEmpty()) {
                        l.add(endWord);
                    }
                    l.add(val);
                }
            }
        }
        return out;
    }


    private void dfs(Node n, List<String> result) {
        if (n != null) {
            result.add(n.getValue());
            if (n.getPres() != null) {
                for (Node nd : n.getPres()) {
                    List<String> l = new ArrayList<>();
                    dfs(nd, l);
                    result.addAll(l);
                }
            }
        }
    }


    private boolean next(String bgw, String candidate) {
        int len = bgw.length();
        int counter = 0;
        for (int i = 0; i < len; i++) {
            char b = bgw.charAt(i);
            char c = candidate.charAt(i);
            if (b != c) {
                if (++counter > 1) {
                    return false;
                }
            }
        }
        return counter > 0;//exclude equals begin
    }


    public static class Node {
        private int depth;

        private boolean red;
        private String value;
        private List<Node> pres = new ArrayList<>();

        public Node(String value, Node pre) {
            this.value = value;
            this.pres.add(pre);
            if (pre == null) {
                depth = 0;
            } else {
                depth = pre.getDepth() + 1;
            }
        }

        public void addPre(Node pre) {
            pres.add(pre);
        }

        public String getValue() {
            return value;
        }

        public List<Node> getPres() {
            return pres;
        }

        public int getDepth() {
            return depth;
        }

        public boolean getRed() {
            return red;
        }

        public void setRed(boolean red) {
            this.red = red;
        }
    }
}
