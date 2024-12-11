package com.my.learn.exercise.data.struct.leetcode.difficult;
/*
 * 创建人：baimiao
 * 创建时间：2024/3/21 10:34
 给你一个未排序的整数数组 nums ，请你找出其中没有出现的最小的正整数。

请你实现时间复杂度为 O(n) 并且只使用常数级别额外空间的解决方案。


示例 1：

输入：nums = [1,2,0]
输出：3
解释：范围 [1,2] 中的数字都在数组中。
示例 2：

输入：nums = [3,4,-1,-2,1]==>[3,1,-1,-2,4]==>[1,3,-1-2,4]
输出：2
解释：1 在数组中，但 2 没有。
示例 3：

输入：nums = [7,8,9,11,12]
输出：1
解释：最小的正数 1 没有出现。


提示：

1 <= nums.length <= 105
-231 <= nums[i] <= 231 - 1
 */

public class FirstMissingPositive {
    public static void main(String[] args) {
        FirstMissingPositive positive = new FirstMissingPositive();
//        int[] nums = {2, 5, 7, 0, 8, 4, 3, 45, -65, 9, 1};
        int[] nums = {8,7,5,4,3,6,1};
        int result = positive.findTheNumber(nums);
        System.out.println(result);
    }

    private int findTheNumber(int[] nums) {
        int length = nums.length;
        if (length == 1) {
            return nums[0] == 1 ? 2 : 1;
        }

        int cursorVal, posVal, temp;
        //[8,7,3,1]==>[1,7,3,3]==>[1,4,5,3]
        for (int i = 0; i < length; i++) {
            cursorVal = nums[i];
            while (cursorVal > 0 && cursorVal <= length) {
                if (cursorVal == i + 1) {
                    break;
                }
                if (cursorVal == nums[cursorVal - 1]) {
                    break;
                }
                temp = nums[cursorVal - 1];
                nums[cursorVal - 1] = cursorVal;
                if (temp == i + 1) {
                    nums[i] = temp;
                    break;
                }
                cursorVal = temp;
            }
        }

        for (int i = 0; i < length; i++) {
            if (nums[i] != i + 1) {
                return i + 1;
            }
        }
        return length + 1;
    }
}
