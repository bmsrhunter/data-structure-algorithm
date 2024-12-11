package com.my.learn.exercise.data.struct.sort;
/*
 * 创建人：baimiao
 * 创建时间：2024/8/23 11:28
 *
 */

import java.util.Arrays;

public class MergeSort {

    public static void main(String[] args) {

        MergeSort sort = new MergeSort();
        int[] arr = {2, 5, -1, -4, 7, 0, -9, 4, 6, 1, 8, -5};
//        int[] arr = {2147483647, -2147483648, -1, 0};
//        int[] arr = {3, 2};
        sort.mergeSort(arr, 0, arr.length);
        System.out.println(Arrays.toString(arr));
    }


    private void mergeSort(int[] arr, int left, int right) {
        int middle = (left + right) / 2;
        if (right - left > 2) {
            mergeSort(arr, left, middle);
            mergeSort(arr, middle, right);
        }

        int gap = right - left;
        if (gap > 1) {
            int[] result = new int[gap];
            int k = 0;
            int j = middle;
            for (int i = left; i < middle; i++) {
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
    }
}
