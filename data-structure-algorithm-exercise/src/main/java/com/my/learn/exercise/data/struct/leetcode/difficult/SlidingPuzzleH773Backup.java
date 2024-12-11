package com.my.learn.exercise.data.struct.leetcode.difficult;
/*
 * 创建人：baimiao
 * 创建时间：2024/7/3 13:15
 773. 滑动谜题
困难
相关标签
相关企业
提示
在一个 2 x 3 的板上（board）有 5 块砖瓦，用数字 1~5 来表示, 以及一块空缺用 0 来表示。一次 移动 定义为选择 0 与一个相邻的数字（上下左右）进行交换.

最终当板 board 的结果是 [[1,2,3],[4,5,0]] 谜板被解开。

给出一个谜板的初始状态 board ，返回最少可以通过多少次移动解开谜板，如果不能解开谜板，则返回 -1 。

输入：board = [[1,2,3],[4,0,5]]
输出：1
解释：交换 0 和 5 ，1 步完成
*
*
输入：board = [[1,2,3],[5,4,0]]
输出：-1
解释：没有办法完成谜板

输入：board = [[4,1,2],[5,0,3]]
输出：5
解释：
最少完成谜板的最少移动次数是 5 ，
一种移动路径:
尚未移动:  [[4,1,2],[5,0,3]]
移动 1 次: [[4,1,2],[0,5,3]]
移动 2 次: [[0,1,2],[4,5,3]]
移动 3 次: [[1,0,2],[4,5,3]]
移动 4 次: [[1,2,0],[4,5,3]]
移动 5 次: [[1,2,3],[4,5,0]]
*
board.length == 2
board[i].length == 3
0 <= board[i][j] <= 5
board[i][j] 中每个值都 不同

3 2 4
1 5 0

 * */

public class SlidingPuzzleH773Backup {


    public static void main(String[] args) {
        SlidingPuzzleH773Backup puzzle = new SlidingPuzzleH773Backup();
        int[][] board = {{3, 2, 4}, {1, 5, 0}};
        int result = puzzle.slidingPuzzle(board);
        System.out.println(result);
    }

    /**
     * 0.逆序为奇数没有解答
     * 1.移动过程中减少逆序
     * 2.远的优先,用曼哈顿距离|x1-x2|+|y1-y2|
     */

    private int[] order = new int[6];
    private int zeroPos = -1;
    private int lastPos = -1;

    public int slidingPuzzle(int[][] board) {
        for (int i = 0; i < 2; i++) {
            for (int j = 0; j < 3; j++) {
                order[i * 3 + j] = board[i][j];
                if (board[i][j] == 0) {
                    zeroPos = i * 3 + j;
                }
            }
        }
        int roc = 0;
        for (int i = 0; i < 6; i++) {
            for (int j = i + 1; j < 6; j++) {
                if (order[i] != 0 && order[j] != 0 && order[i] > order[j]) {
                    roc++;
                }
            }
        }

        if (roc % 2 > 0) {
            return -1;
        }
        int step = 0;
        while (!perfect()) {
            move();
            step++;
        }
        return step;
    }

    private void move() {
        int[] neighbor = findNeighbor(zeroPos);
        int min = 0;
        int pos = -1;
        int length = neighbor.length;
        for (int i = 0; i < length; i++) {
            if (lastPos != neighbor[i]) {
                int diff = reverseOrderDiff(neighbor[i], zeroPos);
                if (min > diff) {
                    min = diff;
                    pos = neighbor[i];
                }
            }
        }

        if (pos < 0) {
            int x;
            int y;
            int ox;
            int oy;
            int zx = zeroPos / 3;
            int zy = zeroPos % 3;
            int max = -2;
            for (int i = 0; i < length; i++) {
                if (neighbor[i] != lastPos) {
                    int val = order[neighbor[i]];
                    x = neighbor[i] / 3;
                    y = neighbor[i] % 3;
                    ox = (val - 1) / 3;
                    oy = (val - 1) % 3;
                    x = x - ox > 0 ? x - ox : ox - x;
                    y = y - oy > 0 ? y - oy : oy - y;
                    int dis1 = x + y;
                    x = zx - ox > 0 ? zx - ox : ox - zx;
                    y = zy - oy > 0 ? zy - oy : oy - zy;
                    int dis2 = x + y;
                    if (max < (dis1 - dis2)) {
                        max = (dis1 - dis2);
                        pos = neighbor[i];
                    }
                }
            }
        }

        lastPos = zeroPos;
        zeroPos = pos;
        order[lastPos] = order[pos];
        order[pos] = 0;
    }


    //manhattan distance ，距离自己原本的位置more/less
    private int reverseOrderDiff(int from, int to) {
        if (from / 3 == to / 3) { //left or right
            return 0;
        }
        int counter = 0;
        int val = order[from];
        if (from > to) {
            for (int i = to + 1; i < from; i++) {
                if (val < order[i]) {
                    counter++;
                }
            }
        } else {
            for (int i = from + 1; i < to; i++) {
                if (val > order[i]) {
                    counter++;
                }
            }
        }
        if (counter == 0) {
            return 2;
        } else if (counter == 1) {
            return 0;
        } else {
            return -2;
        }
    }

    private int[] findNeighbor(int pos) {
        switch (pos) {
            case 0:
                return new int[]{1, 3};
            case 1:
                return new int[]{0, 2, 4};
            case 2:
                return new int[]{1, 5};
            case 3:
                return new int[]{0, 4};
            case 4:
                return new int[]{1, 3, 5};
            case 5:
                return new int[]{2, 4};
        }
        return null;
    }

    private boolean perfect() {
        if (order[5] == 0) {
            int roc = 0;
            for (int i = 0; i < 6; i++) {
                for (int j = i + 1; j < 6; j++) {
                    if (order[i] != 0 && order[j] != 0 && order[i] > order[j]) {
                        roc++;
                    }
                }
            }
            return roc == 0;
        }
        return false;
    }
}
