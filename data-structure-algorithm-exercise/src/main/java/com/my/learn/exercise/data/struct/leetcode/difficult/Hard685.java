package com.my.learn.exercise.data.struct.leetcode.difficult;
/*
 * 创建人：baimiao
 * 创建时间：2024/5/18 17:58
 685. 冗余连接 II
困难
相关标签
相关企业
在本问题中，有根树指满足以下条件的 有向 图。该树只有一个根节点，所有其他节点都是该根节点的后继。该树除了根节点之外的每一个节点都有且只有一个父节点，而根节点没有父节点。

输入一个有向图，该图由一个有着 n 个节点（节点值不重复，从 1 到 n）的树及一条附加的有向边构成。附加的边包含在 1 到 n 中的两个不同顶点间，这条附加的边不属于树中已存在的边。

结果图是一个以边组成的二维数组 edges 。 每个元素是一对 [ui, vi]，用以表示 有向 图中连接顶点 ui 和顶点 vi 的边，其中 ui 是 vi 的一个父节点。

返回一条能删除的边，使得剩下的图是有 n 个节点的有根树。若有多个答案，返回最后出现在给定二维数组的答案。
* 提示：

n == edges.length
3 <= n <= 1000
edges[i].length == 2
1 <= ui, vi <= n
*
*
* 题解：树有n个节点 ，n-1条边，边是有向的，有出度和入度，当节点数==入度时 说明存在环，有可能是多边形环或者三角形环，正常树入度==1，出现入==2，向上追溯直到追溯到入度等于2的节点
 入度都等于1，说明没有跟，最后添加的那个使入度==节点数的那个边就是应该移除的
 * */

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class Hard685 {
    public static void main(String[] args) {
        Hard685 obj = new Hard685();
//        int[][] edges = {{1, 2}, {1, 3}, {2, 3}};
        int[][] edges = {{2, 1}, {1, 4}, {4, 2}, {5, 3}, {3, 1}};
//        int[][] edges = {{1, 2}, {3, 4}, {6, 1},  {1, 5}, {2, 3},{4, 1}};

        int[] result = obj.findRedundantDirectedConnection(edges);
        System.out.println(result[0] + "-" + result[1]);
    }

    public int[] findRedundantDirectedConnection(int[][] edges) {
        int totalEdges = edges.length;
        Map<Integer, int[]> map = new HashMap<>();
        Set<Integer> set = new HashSet<>();
        int counter = 0;
        int maxSize = 0;
        int[] last = null;
        int key = -1;
        int[] edge;
        for (int i = 0; i < totalEdges; i++) {
            edge = edges[i];
            counter++;
            set.add(edge[0]);
            set.add(edge[1]);
            int[] vals = map.get(edge[1]);
            if (vals == null) {
                map.put(edge[1], new int[]{edge[0]});
                maxSize = maxSize > 1 ? maxSize : 1;
            } else {
                map.put(edge[1], new int[]{vals[0], edge[0]});
                key = edge[1];
                maxSize = 2;
            }

            if (set.size() == counter) {
                if (maxSize == 2) {
                    break;
                } else {
                    if (last == null) {
                        last = edge;
                    }
                }
            }
        }
        if (maxSize == 1) {
            return last;
        } else {
            int[] val = map.get(key);
            int left = val[0];
            int anchor = left;
            int right = val[1];
            int[] value;
            while (true) {
                value = map.get(anchor);
                if (value != null) {
                    if (value[0] == key) {
                        return new int[]{left, key};
                    } else {
                        anchor = value[0];
                    }
                } else {
                    break;
                }
            }
            return new int[]{right, key};
        }
    }
}
