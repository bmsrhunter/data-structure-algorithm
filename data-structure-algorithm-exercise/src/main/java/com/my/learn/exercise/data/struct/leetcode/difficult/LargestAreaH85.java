package com.my.learn.exercise.data.struct.leetcode.difficult;
/*
 * 创建人：baimiao
 * 创建时间：2024/6/20 9:10
 85. 最大矩形
困难
相关标签
相关企业
给定一个仅包含 0 和 1 、大小为 rows x cols 的二维二进制矩阵，找出只包含 1 的最大矩形，并返回其面积。
输入：matrix = [["1","0","1","0","0"],["1","0","1","1","1"],["1","1","1","1","1"],["1","0","0","1","0"]]
输出：6
解释：最大矩形如上图所示。
示例 2：

输入：matrix = [["0"]]
输出：0
示例 3：

输入：matrix = [["1"]]
输出：1


提示：

rows == matrix.length
cols == matrix[0].length
1 <= row, cols <= 200
matrix[i][j] 为 '0' 或 '1'
 */

import java.util.Stack;

public class LargestAreaH85 {
    public static void main(String[] args) {
        LargestAreaH85 area = new LargestAreaH85();
        char[][] matrix = {{'0', '0', '0', '1', '0', '0', '1', '1', '1', '1', '1', '1'}, {'0', '1', '1', '1', '1', '1', '1', '1', '1', '1', '1', '0'}, {'0', '1', '1', '1', '0', '1', '0', '1', '1', '0', '1', '1'}, {'0', '1', '1', '1', '1', '1', '1', '0', '1', '1', '0', '1'}, {'0', '1', '0', '1', '0', '1', '1', '1', '1', '1', '0', '1'}, {'0', '1', '1', '1', '1', '1', '1', '0', '1', '1', '1', '1'}, {'0', '1', '1', '1', '1', '1', '0', '1', '1', '1', '0', '1'}, {'0', '1', '1', '1', '0', '1', '1', '1', '1', '1', '1', '1'}, {'0', '1', '1', '1', '1', '1', '1', '0', '0', '1', '1', '1'}, {'0', '1', '1', '1', '1', '1', '1', '1', '0', '1', '1', '1'}, {'0', '1', '0', '0', '0', '1', '1', '0', '1', '1', '0', '1'}, {'0', '0', '0', '1', '1', '1', '1', '0', '1', '1', '1', '1'}};
//        char[][] matrix = {
//                {'1', '0', '1', '1', '0', '1'},
//                {'1', '1', '1', '1', '1', '1'},
//                {'0', '1', '1', '0', '1', '1'},
//                {'1', '1', '1', '0', '1', '0'},
//                {'0', '1', '1', '1', '1', '1'},
//                {'1', '1', '0', '1', '1', '1'}
//        };
        int result = area.maximalRectangle(matrix);
        System.out.println(result);
    }

    private int row;
    private int column;
    private int step = 1;
    private Stack<Node> stack = new Stack<>();

    public int maximalRectangle(char[][] matrix) {
        int max = 0;
        row = matrix.length;
        column = matrix[0].length;
        for (int i = 0; i < row; i = i + step) {
            if (max >= (row - i) * column) {
                break;
            }
            int vert;
            for (int j = 0; j < column; j++) {
                vert = vertical(matrix, i, j);
                step = vert < step ? vert : step;
                if (stack.isEmpty()) {
                    if (vert > 0) {
                        stack.push(new Node(vert));
                    }
                } else {
                    Node node = stack.peek();
                    if (vert > node.getBottom()) {
                        stack.push(new Node(vert));
                    } else if (vert == node.getBottom()) {
                        node.addHeight();
                    } else {
                        int v = calculate(vert);
                        max = max > v ? max : v;
                    }
                }
            }
            int v = calculate(0);
            max = max > v ? max : v;
            if (step == 0) {
                step = 1;
            }
        }
        return max;
    }

    //结算，返回最大值
    private int calculate(int vert) {
        int localMax = 0;
        int height = 0;
        while (!stack.isEmpty() && stack.peek().getBottom() > vert) {
            Node node = stack.pop();
            height += node.getHeight();
            int area = node.getBottom() * height;
            localMax = localMax > area ? localMax : area;
        }
        if (vert != 0) {
            if (stack.isEmpty()) {
                stack.push(new Node(vert, height + 1));
            } else {
                Node top = stack.peek();
                if (top.getBottom() == vert) {
                    top.setHeight(top.getHeight() + height + 1);
                } else {
                    stack.push(new Node(vert, height + 1));
                }
            }
        }
        return localMax;
    }

    private int vertical(char[][] matrix, int x, int y) {
        int len = 0;
        for (int i = x; i < row; i++) {
            if (matrix[i][y] == '1') {
                len++;
            } else {
                break;
            }
        }
        return len;
    }


    private static class Node {
        private int bottom;
        private int height;

        public Node(int bottom) {
            this.bottom = bottom;
            this.height = 1;
        }

        public Node(int bottom, int height) {
            this.bottom = bottom;
            this.height = height;
        }

        public void setBottom(int bottom) {
            this.bottom = bottom;
        }

        public void setHeight(int height) {
            this.height = height;
        }

        public void addHeight() {
            this.height = height + 1;
        }

        public int getBottom() {
            return bottom;
        }

        public int getHeight() {
            return height;
        }
    }
}
