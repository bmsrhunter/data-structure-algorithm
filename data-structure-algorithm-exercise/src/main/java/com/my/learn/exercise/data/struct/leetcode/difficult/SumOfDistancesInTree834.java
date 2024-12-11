package com.my.learn.exercise.data.struct.leetcode.difficult;
/*
 * 创建人：baimiao
 * 创建时间：2024/5/31 13:37
 *834. 树中距离之和
困难
相关标签
相关企业
给定一个无向、连通的树。树中有 n 个标记为 0...n-1 的节点以及 n-1 条边 。

给定整数 n 和数组 edges ， edges[i] = [ai, bi]表示树中的节点 ai 和 bi 之间有一条边。

返回长度为 n 的数组 answer ，其中 answer[i] 是树中第 i 个节点与所有其他节点之间的距离之和。
*
* 输入: n = 6, edges = [[0,1],[0,2],[2,3],[2,4],[2,5]]
输出: [8,12,6,10,10,10]
解释: 树如图所示。
我们可以计算出 dist(0,1) + dist(0,2) + dist(0,3) + dist(0,4) + dist(0,5)
也就是 1 + 1 + 2 + 2 + 2 = 8。 因此，answer[0] = 8，以此类推。
*
* 1 <= n <= 3 * 104
edges.length == n - 1
edges[i].length == 2
0 <= ai, bi < n
ai != bi
给定的输入保证为有效的树
*                               0
*                      2                 5
*               6          4         9   3  7
*           1      8         10
*
* n-2-2k
*
树的任意一个节点都可以当做根，算出根到各个点的距离，然后它的子节点是 dist[parent]+(n-2)-2*count(此节点的子节点数量)

 */

import com.wifiin.common.JSON;

import java.util.*;

public class SumOfDistancesInTree834 {
    public static void main(String[] args) {
        SumOfDistancesInTree834 tree = new SumOfDistancesInTree834();
        int n = 11;
        int[][] edges = {{0, 2}, {0, 5}, {2, 6}, {2, 4}, {6, 1}, {6, 8}, {4, 10}, {5, 9}, {5, 3}, {5, 7}};
//        int n = 6;
//        int[][] edges = {{0, 1}, {0, 2}, {2, 3}, {2, 4}, {2, 5}};
//        int n = 4;
//        int[][] edges = {{0, 1}, {1, 2}, {1, 3}};
        int[] result = tree.sumOfDistancesInTree(n, edges);
        System.out.println(JSON.common().toJSON(result));
    }


    public int[] sumOfDistancesInTree(int n, int[][] edges) {
        if (n == 1) {
            return new int[]{0};
        } else if (n == 2) {
            return new int[]{1, 1};
        } else {
            int totalEdge = edges.length;
            int left;
            int right;
            Map<Integer, Set<Integer>> map = new HashMap<>();
            for (int i = 0; i < totalEdge; i++) {
                int[] edge = edges[i];
                left = edge[0];
                right = edge[1];
                Set<Integer> set = map.get(left);
                if (set == null) {
                    set = new HashSet<>();
                    set.add(right);
                    map.put(left, set);
                } else {
                    set.add(right);
                }
                Set<Integer> s = map.get(right);
                if (s == null) {
                    s = new HashSet<>();
                    s.add(left);
                    map.put(right, s);
                } else {
                    s.add(left);
                }
            }
            Map<Integer, Integer> relation = new HashMap<>();
            Set<Integer> leaves = new HashSet<>();
            List<Integer> nodes = bfs(map, relation, leaves);
            nodes.remove(0);
            Map<Integer, Integer> childCount = countSon(map, nodes, leaves);
            int[] result = new int[n];
            result[0] = calculateRoot(map);
            int sum;
            int size = nodes.size();
            for (int i = 0; i < size; i++) {
                int val = nodes.get(i);
                Integer parent = relation.get(val);
                int count = childCount.get(val);
                sum = result[parent] + (n - 2) - 2 * count;
                result[val] = sum;
            }
            for (Integer i : leaves) {
                Set<Integer> parent = map.get(i);
                for (Integer p : parent) {
                    sum = result[p] + (n - 2);
                    result[i] = sum;
                }
            }
            return result;
        }
    }


    private List<Integer> bfs(Map<Integer, Set<Integer>> map, Map<Integer, Integer> relation, Set<Integer> leaves) {
        List<Integer> nodes = new ArrayList<>();
        Set<Integer> passed = new HashSet<>();
        List<Integer> queue = new ArrayList<>();
        Set<Integer> children;
        queue.add(0);
        passed.add(0);
        nodes.add(0);
        int count = 0;
        while (count < queue.size()) {
            int val = queue.get(count);
            children = map.get(val);
            int size = children.size();
            if (size > 1 || val == 0) {
                for (Integer i : children) {
                    if (!passed.contains(i)) {
                        queue.add(i);
                        passed.add(i);
                        nodes.add(i);
                        relation.put(i, val);
                    }
                }
            } else {
                leaves.add(val);
            }
            count++;
        }
        nodes.removeAll(leaves);
        return nodes;
    }


    private Map<Integer, Integer> countSon(Map<Integer, Set<Integer>> map, List<Integer> nodes, Set<Integer> leaves) {
        Map<Integer, Integer> childCount = new HashMap<>();
        int size = nodes.size();
        for (int i = size - 1; i >= 0; i--) {
            int val = nodes.get(i);
            int counter = 0;
            Set<Integer> relations = map.get(val);
            for (Integer r : relations) {
                if (leaves.contains(r)) {
                    counter++;
                } else {
                    Integer n = childCount.get(r);
                    if (n != null) {
                        counter += n;
                        counter++;
                    }
                }
            }
            childCount.put(val, counter);
        }
        return childCount;
    }


    private int calculateRoot(Map<Integer, Set<Integer>> map) {
        int root = 0;
        int sum = 0;
        Set<Integer> passed = new HashSet<>();
        Map<Integer, Integer> levelMap = new HashMap<>();
        Set<Integer> children;
        List<Integer> queue = new ArrayList<>();
        queue.add(root);
        passed.add(root);
        int currentLevel;
        levelMap.put(root, 0);
        int count = 0;
        while (count < queue.size()) {
            int val = queue.get(count);
            children = map.get(val);
            currentLevel = levelMap.get(val) + 1;
            for (Integer i : children) {
                if (!passed.contains(i)) {
                    sum += currentLevel;
                    int size = map.get(i).size();
                    if (size > 1) {
                        levelMap.put(i, currentLevel);
                        queue.add(i);
                        passed.add(i);
                    }
                }
            }
            count++;
        }
        return sum;
    }


}
