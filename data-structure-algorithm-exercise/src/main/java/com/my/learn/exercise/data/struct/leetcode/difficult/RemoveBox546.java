package com.my.learn.exercise.data.struct.leetcode.difficult;
/*
 * 创建人：baimiao
 * 创建时间：2024/9/4 9:48


 546. 移除盒子
困难
相关标签
相关企业
给出一些不同颜色的盒子 boxes ，盒子的颜色由不同的正数表示。

你将经过若干轮操作去去掉盒子，直到所有的盒子都去掉为止。每一轮你可以移除具有相同颜色的连续 k 个盒子（k >= 1），这样一轮之后你将得到 k * k 个积分。

返回 你能获得的最大积分和 。



示例 1：

输入：boxes = [1,3,2,2,2,3,4,3,1]
输出：23
解释：
[1, 3, 2, 2, 2, 3, 4, 3, 1]
----> [1, 3, 3, 4, 3, 1] (3*3=9 分)
----> [1, 3, 3, 3, 1] (1*1=1 分)
----> [1, 1] (3*3=9 分)
----> [] (2*2=4 分)
示例 2：

输入：boxes = [1,1,1]
输出：9
示例 3：

输入：boxes = [1]
输出：1


提示：

1 <= boxes.length <= 100
1 <= boxes[i] <= 100
 */

public class RemoveBox546 {


    public static void main(String[] args) {
        RemoveBox546 box = new RemoveBox546();
        int[] boxes = new int[]{};
        int result = box.removeBoxes(boxes);
        System.out.println(result);
    }

    public int removeBoxes(int[] boxes) {
        return 0;
    }
}
