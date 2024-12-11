package com.my.learn.exercise.data.struct.leetcode.difficult;
/*
 * 创建人：baimiao
 * 创建时间：2024/7/1 14:35
 778. 水位上升的泳池中游泳
困难
相关标签
相关企业
提示
在一个 n x n 的整数矩阵 grid 中，每一个方格的值 grid[i][j] 表示位置 (i, j) 的平台高度。

当开始下雨时，在时间为 t 时，水池中的水位为 t 。你可以从一个平台游向四周相邻的任意一个平台，但是前提是此时水位必须同时淹没这两个平台。假定你可以瞬间移动无限距离，也就是默认在方格内部游动是不耗时的。当然，在你游泳的时候你必须待在坐标方格里面。

你从坐标方格的左上平台 (0，0) 出发。返回 你到达坐标方格的右下平台 (n-1, n-1) 所需的最少时间 。
 示例 1:



输入: grid = [[0,2],[1,3]]
输出: 3
解释:
时间为0时，你位于坐标方格的位置为 (0, 0)。
此时你不能游向任意方向，因为四个相邻方向平台的高度都大于当前时间为 0 时的水位。
等时间到达 3 时，你才可以游向平台 (1, 1). 因为此时的水位是 3，坐标方格中的平台没有比水位 3 更高的，所以你可以游向坐标方格中的任意位置
示例 2:



输入: grid = [[0,1,2,3,4],[24,23,22,21,5],[12,13,14,15,16],[11,17,18,19,20],[10,9,8,7,6]]
输出: 16
解释: 最终的路线用加粗进行了标记。
我们必须等到时间为 16，此时才能保证平台 (0, 0) 和 (4, 4) 是连通的


提示:

n == grid.length
n == grid[i].length
1 <= n <= 50
0 <= grid[i][j] < n^2
grid[i][j] 中每个值 均无重复
 *
 * */

import java.util.HashSet;
import java.util.Set;

public class SwimInWaterH778 {

    public static void main(String[] args) {
        SwimInWaterH778 swim = new SwimInWaterH778();
        int[][] grid = {{0, 1, 2, 3, 4}, {24, 23, 22, 21, 5}, {12, 13, 14, 15, 16}, {11, 17, 18, 19, 20}, {10, 9, 8, 7, 6}};
        int result = swim.swimInWater(grid);
        System.out.println(result);
    }

    private int n;

    private int time = 0;

    public int swimInWater(int[][] grid) {
        n = grid.length;
        Set<Integer> visited = new HashSet<>();
        Set<Integer> unVisited = new HashSet<>();
        if (n > 1) {
            visited.add(0);
            visited.add(n * n - 1);
            search(grid, 0, 0, visited, unVisited);
        }
        return time;
    }

    //构建点与边的关系，先从（0,0）开始，把（0,0）可以访问的节点放到unVisited中，从中选择一个最小的，重复上述过程，直到（n-1,n-1）的上面和左边的节点被访问到为止，崽从中找出最大的值
    private void search(int[][] grid, int x, int y, Set<Integer> visited, Set<Integer> unVisited) {
        if (x + 1 < n && !visited.contains((x + 1) * n + y)) {
            unVisited.add((x + 1) * n + y);
        }
        if (x - 1 >= 0 && !visited.contains((x - 1) * n + y)) {
            unVisited.add((x - 1) * n + y);
        }
        if (y - 1 >= 0 && !visited.contains(x * n + y - 1)) {
            unVisited.add(x * n + y - 1);
        }
        if (y + 1 < n && !visited.contains(x * n + y + 1)) {
            unVisited.add(x * n + y + 1);
        }

        int pos = findMin(grid, unVisited);
        visited.add(pos);
        unVisited.remove(pos);
        if (pos == n * n - n - 1 || pos == n * n - 2) {
            time = findMax(grid, visited);
        } else {
            search(grid, pos / n, pos % n, visited, unVisited);
        }
    }


    private int findMin(int[][] grid, Set<Integer> unVisited) {
        int p = Integer.MAX_VALUE;
        int m = -1;
        for (Integer s : unVisited) {
            int x = s / n;
            int y = s % n;
            if (p > grid[x][y]) {
                p = grid[x][y];
                m = s;
            }
        }
        return m;
    }

    private int findMax(int[][] grid, Set<Integer> visited) {
        int m = -1;
        for (Integer s : visited) {
            int x = s / n;
            int y = s % n;
            if (m < grid[x][y]) {
                m = grid[x][y];
            }
        }
        return m;
    }
}
