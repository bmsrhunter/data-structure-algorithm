package com.my.learn.exercise.data.struct.leetcode.difficult;
/*
 * 创建人：baimiao
 * 创建时间：2024/7/25 9:55
327. 区间和的个数
困难
相关标签
相关企业
给你一个整数数组 nums 以及两个整数 lower 和 upper 。求数组中，值位于范围 [lower, upper] （包含 lower 和 upper）之内的 区间和的个数 。

区间和 S(i, j) 表示在 nums 中，位置从 i 到 j 的元素之和，包含 i 和 j (i ≤ j)。



示例 1：
输入：nums = [-2,5,-1], lower = -2, upper = 2
输出：3
解释：存在三个区间：[0,0]、[2,2] 和 [0,2] ，对应的区间和分别是：-2 、-1 、2 。
示例 2：

输入：nums = [0], lower = 0, upper = 0
输出：1


提示：

1 <= nums.length <= 10^5
-2^31 <= nums[i] <= 2^31 - 1
-10^5 <= lower <= upper <= 10^5
题目数据保证答案是一个 32 位 的整数

* right 2n+1,left 2n+2
*
 21|10|11|7|3|6|5|4|3|2|1
             |

*
*
 */

public class RangeSumH327 {

    public static void main(String[] args) {
        RangeSumH327 rangeSum = new RangeSumH327();
//        int[] nums = {-2147483647, 0, -2147483647, 2147483647};
//        int lower = -564;
//        int upper = 3864;
        int[] nums = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10};
        int lower = 10;
        int upper = 20;

        int result = rangeSum.countRangeSum(nums, lower, upper);
        System.out.println(result);
    }
    // -2 3 2
    // -2 2 3


    //1.有n个数先用前缀和，S(n)=S(n-1)+An，初始化S数组有n+1个，第一个为0，S(k)-S(0)是 S(k)自身
    //用分治法将两边数据排序，因为是有序数对，所以当C(k)-B(c)>=lower 后面的C(k+1...n)都符合
    //同样所以当C(k)-B(c)>upper 后面的C(k+1...n)都符合
    public int countRangeSum(int[] nums, int lower, int upper) {
        int length = nums.length;
        long[] array1 = new long[length + 1];
        for (int i = 0; i < length; i++) {
            array1[i + 1] = array1[i] + nums[i];
        }
        return mergeSort(array1, 0, array1.length, lower, upper);
    }


    private int mergeSort(long[] arr, int left, int right, int lower, int upper) {
        int sum = 0;
        int middle = (left + right) / 2;
        if (right - left > 2) {
            sum = mergeSort(arr, left, middle, lower, upper) + mergeSort(arr, middle, right, lower, upper);
        }

        int gap = right - left;
        if (gap > 1) {
            long[] result = new long[gap];
            int k = 0;
            int j = middle, pl = middle, pr = middle;
            for (int i = left; i < middle; i++) {
                //
                while (pl < right && arr[pl] - arr[i] < lower) {
                    pl++;
                }
                while (pr < right && arr[pr] - arr[i] <= upper) {
                    pr++;
                }
                sum += pr - pl;
                //
                //归并排序部分
                while (j < right) {
                    if (arr[i] >= arr[j]) {
                        result[k++] = arr[j++];
                    } else {
                        break;
                    }
                }
                result[k++] = arr[i];
            }
            System.arraycopy(result, 0, arr, left, k);
        }
        return sum;
    }

}
