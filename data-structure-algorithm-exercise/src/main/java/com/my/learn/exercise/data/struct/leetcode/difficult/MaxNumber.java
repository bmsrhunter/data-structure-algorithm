package com.my.learn.exercise.data.struct.leetcode.difficult;
/*
 * 创建人：baimiao
 * 创建时间：2024/5/22 15:24
 321. 拼接最大数
困难
相关标签
相关企业
给你两个整数数组 nums1 和 nums2，它们的长度分别为 m 和 n。数组 nums1 和 nums2 分别代表两个数各位上的数字。同时你也会得到一个整数 k。

请你利用这两个数组中的数字中创建一个长度为 k <= m + n 的最大数，在这个必须保留来自同一数组的数字的相对顺序。

返回代表答案的长度为 k 的数组。



示例 1：

输入：nums1 = [3,4,6,5], nums2 = [9,1,2,5,8,3], k = 5
输出：[9,8,6,5,3]
示例 2：

输入：nums1 = [6,7], nums2 = [6,0,4], k = 5
输出：[6,7,6,0,4]
示例 3：

输入：nums1 = [3,9], nums2 = [8,9], k = 3
输出：[9,8,9]


提示：

m == nums1.length
n == nums2.length
1 <= m, n <= 500
0 <= nums1[i], nums2[i] <= 9
1 <= k <= m + n
 */

import com.wifiin.common.JSON;

public class MaxNumber {


    public static void main(String[] args) {
        MaxNumber max = new MaxNumber();
        int[] num1 = {};
        int[] num2 = {};
        int k = 0;
        int[] result = max.calcute(num1, num2, k);
        System.out.println(JSON.common().toJSON(result));
    }

    public int[] calcute(int[] nums1, int[] nums2, int k) {
    return null;
    }
}
