package com.my.learn.exercise.data.struct.leetcode.difficult;
/*
 * 创建人：baimiao
 * 创建时间：2024/5/27 13:49
 85. 最大矩形
困难
相关标签
相关企业
给定一个仅包含 0 和 1 、大小为 rows x cols 的二维二进制矩阵，找出只包含 1 的最大矩形，并返回其面积。

输入：matrix = [['1','0','1','0','0'],['1','0','1','1','1'],['1','1','1','1','1'],['1','0','0','1','0']]
输出：6
解释：最大矩形如上图所示。
示例 2：

输入：matrix = [['0']]
输出：0
示例 3：

输入：matrix = [['1']]
输出：1


提示：

rows == matrix.length
cols == matrix[0].length
1 <= row, cols <= 200
matrix[i][j] 为 '0' 或 '1'
 *
 *
 */

public class MaxRectangle85 {


    public static void main(String[] args) {
        MaxRectangle85 obj = new MaxRectangle85();
        char[][] matrix = {{'1', '0', '1', '0', '0'}, {'1', '0', '1', '1', '1'}, {'1', '1', '1', '1', '1'}, {'1', '0', '0', '1', '0'}};
        int result = obj.maximalRectangle(matrix);
        System.out.println(result);
    }


    public int maximalRectangle(char[][] matrix) {
        int row = matrix.length;
        int colum = matrix[0].length;
        if (row == 1 && colum == 1) {
            return matrix[0][0] == '1' ? 1 : 0;
        }
        //todo
        int maxArea = 0;
        int horizon = 0;
        int longitude = 0;
        int start = -1;
        int end = -1;
        for (int i = 0; i < row; i++) {
            for (int j = 0; j < colum; j++) {
                if (matrix[i][j] == 1) {
                    horizon++;
                    start = j;
                } else {
                    end = j - 1;


                }

            }

        }

        return 0;
    }

    private int localArea(char[][] matrix){
        return 0;
    }
}
