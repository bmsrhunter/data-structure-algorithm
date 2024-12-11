package com.my.learn.exercise.data.struct.leetcode.difficult;
/*
 * 创建人：baimiao
 * 创建时间：2024/11/27 8:49
 * url : https://leetcode.cn/problems/longest-increasing-path-in-a-matrix/
 *
 * 给定一个 m x n 整数矩阵 matrix ，找出其中 最长递增路径 的长度。

对于每个单元格，你可以往上，下，左，右四个方向移动。 你 不能 在 对角线 方向上移动或移动到 边界外（即不允许环绕）
*
* 输入：matrix = [[9,9,4],[6,6,8],[2,1,1]]
输出：4
解释：最长递增路径为 [1, 2, 6, 9]。
*
*
* 输入：matrix = [[3,4,5],[3,2,6],[2,2,1]]
输出：4
解释：最长递增路径是 [3, 4, 5, 6]。注意不允许在对角线方向上移动。
示例 3：

输入：matrix = [[1]]
输出：1
*
* 提示：

m == matrix.length
n == matrix[i].length
1 <= m, n <= 200
0 <= matrix[i][j] <= 231 - 1

 * 1 2 3
 * 6 5 4
 * 7 8 9
 *
 *
 *  找入度为0的节点存到starter中 然后依次广度遍历，并更新每个经过的节点的最大深度，如果当前的高度大于当前节点最大深度，更新当前的最大深度，并将节点的next加入到深度遍历中
 */

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

public class LongestIncreasingPathH329 {

    public static void main(String[] args) {
        LongestIncreasingPathH329 path = new LongestIncreasingPathH329();
        int[][] matrix = {{9, 9, 4}, {6, 6, 8}, {2, 1, 1}};
//        int[][] matrix = {{1, 1, 1}, {1, 1, 1}, {1, 1, 1}};
//        int[][] matrix = {{17, 4, 6, 11, 4, 0, 17, 12, 19, 12, 12, 0}, {0, 6, 4, 4, 5, 9, 15, 1, 11, 13, 18, 0}, {4, 2, 13, 1, 2, 7, 15, 5, 14, 6, 8, 6}};
//        int[][] matrix = {{0, 1, 2, 3, 4, 5, 6, 7, 8, 9}, {19, 18, 17, 16, 15, 14, 13, 12, 11, 10}, {20, 21, 22, 23, 24, 25, 26, 27, 28, 29}, {39, 38, 37, 36, 35, 34, 33, 32, 31, 30}, {40, 41, 42, 43, 44, 45, 46, 47, 48, 49}, {59, 58, 57, 56, 55, 54, 53, 52, 51, 50}, {60, 61, 62, 63, 64, 65, 66, 67, 68, 69}, {79, 78, 77, 76, 75, 74, 73, 72, 71, 70}, {80, 81, 82, 83, 84, 85, 86, 87, 88, 89}, {99, 98, 97, 96, 95, 94, 93, 92, 91, 90}, {100, 101, 102, 103, 104, 105, 106, 107, 108, 109}, {119, 118, 117, 116, 115, 114, 113, 112, 111, 110}, {120, 121, 122, 123, 124, 125, 126, 127, 128, 129}, {139, 138, 137, 136, 135, 134, 133, 132, 131, 130}, {0, 0, 0, 0, 0, 0, 0, 0, 0, 0}};
        int longest = path.longestIncreasingPath(matrix);
        System.out.println(longest);
    }

    private Map<Integer, Node> map = new HashMap<>();

    private List<Integer> starter = new ArrayList<>();
    private int row;
    private int column;


    public int longestIncreasingPath(int[][] matrix) {
        buildMap(matrix);
        int size = starter.size();
        if (size == 0) {
            return 1;
        }
        int result = 0;
        for (int i = 0; i < size; i++) {
            int tmp = maxLengthOfBfs(starter.get(i));
            result = result > tmp ? result : tmp;
        }
        return result;
    }

    private int maxLengthOfBfs(int position) {
        Node node = map.get(position);
        AtomicInteger counter = new AtomicInteger(1);
        compareAndReplace(node.getNext(), counter);
        return counter.get();
    }

    private void compareAndReplace(List<Integer> queue, AtomicInteger counter) {
        if (queue != null && !queue.isEmpty()) {
            int size = queue.size();
            List<Integer> list = new ArrayList<>();
            int level = counter.addAndGet(1);
            for (int i = 0; i < size; i++) {
                int k = queue.get(i);
                Node n = map.get(k);
                if (n.getDepth() < level) {
                    n.setDepth(level);
                    if (n.getNext() != null) {
                        list.addAll(n.getNext());
                    }
                }
            }
            queue = list;
            compareAndReplace(queue, counter);
        }
    }


    private void buildMap(int[][] matrix) {
        row = matrix.length;
        column = matrix[0].length;
        for (int i = 0; i < row; i++) {
            for (int j = 0; j < column; j++) {
                Node n = new Node();
                //up ,down ,left,right
                int out = 0;
                int in = 0;
                if (i > 0) {
                    if (matrix[i][j] < matrix[i - 1][j]) {
                        out++;
                        n.addNext((i - 1) * column + j);
                    } else if (matrix[i][j] > matrix[i - 1][j]) {
                        in++;
                    }
                }
                if (i < row - 1) {
                    if (matrix[i][j] < matrix[i + 1][j]) {
                        out++;
                        n.addNext((i + 1) * column + j);
                    } else if (matrix[i][j] > matrix[i + 1][j]) {
                        in++;
                    }
                }
                if (j > 0) {
                    if (matrix[i][j] < matrix[i][j - 1]) {
                        out++;
                        n.addNext(i * column + j - 1);
                    } else if (matrix[i][j] > matrix[i][j - 1]) {
                        in++;
                    }
                }
                if (j < column - 1) {
                    if (matrix[i][j] < matrix[i][j + 1]) {
                        out++;
                        n.addNext(i * column + j + 1);
                    } else if (matrix[i][j] > matrix[i][j + 1]) {
                        in++;
                    }
                }
                int pos = i * column + j;
                if (out == 0 && in == 0) {// black hole   上下左右都相等
                    continue;
                }
                n.setPos(pos);
                map.put(pos, n);
                if (in == 0) {//入度为0的节点
                    starter.add(pos);
                }
            }
        }
    }

    public static class Node {
        private int pos;// i*column+j

        private int depth = 1;//经过此点的最大深度
        private List<Integer> next;//list positions

        public int getPos() {
            return pos;
        }

        public void setPos(int pos) {
            this.pos = pos;
        }

        public List<Integer> getNext() {
            return next;
        }

        public void setNext(List<Integer> next) {
            this.next = next;
        }

        public int getDepth() {
            return depth;
        }

        public void setDepth(int depth) {
            this.depth = depth;
        }

        public void addNext(Integer n) {
            if (next == null) {
                next = new ArrayList<>();
            }
            next.add(n);
        }
    }
}
