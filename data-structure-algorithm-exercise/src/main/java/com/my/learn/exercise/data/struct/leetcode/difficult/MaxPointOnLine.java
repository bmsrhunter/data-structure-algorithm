package com.my.learn.exercise.data.struct.leetcode.difficult;
/*
 * 创建人：baimiao
 * 创建时间：2024/3/20 9:47

 149. 直线上最多的点数
困难
相关标签
相关企业
给你一个数组 points ，其中 points[i] = [xi, yi] 表示 X-Y 平面上的一个点。求最多有多少个点在同一条直线上。

*
*
* 输入：points = [[1,1],[3,2],[5,3],[4,1],[2,3],[1,4]]
输出：4
*
提示：

1 <= points.length <= 300
points[i].length == 2
-104 <= xi, yi <= 104
points 中的所有点 互不相同




 */

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class MaxPointOnLine {

    public static void main(String[] args) {
        MaxPointOnLine m = new MaxPointOnLine();
//        int[][] points = {{1, 1}, {3, 2}, {5, 3}, {4, 1}, {2, 3}, {1, 4}};
//        int[][] points = {{2, 3}, {3, 3}, {-5, 3}};
        int[][] points = {{-184, -551}, {-105, -467}, {-90, -394}, {-60, -248}, {115, 359}, {138, 429}, {60, 336}, {150, 774}, {207, 639}, {-150, -686}, {-135, -613}, {92, 289}, {23, 79}, {135, 701}, {0, 9}, {-230, -691}, {-115, -341}, {-161, -481}, {230, 709}, {-30, -102}};
//        int[][] points = {{-6, -1}, {3, 1}, {12, 3}};
//        int[][] points = {{7, 3}, {19, 19}, {-16, 3}, {13, 17}, {-18, 1}, {-18, -17}, {13, -3}, {3, 7}, {-11, 12}, {7, 19}, {19, -12}, {20, -18}, {-16, -15}, {-10, -15}, {-16, -18}, {-14, -1}, {18, 10}, {-13, 8}, {7, -5}, {-4, -9}, {-11, 2}, {-9, -9}, {-5, -16}, {10, 14}, {-3, 4}, {1, -20}, {2, 16}, {0, 14}, {-14, 5}, {15, -11}, {3, 11}, {11, -10}, {-1, -7}, {16, 7}, {1, -11}, {-8, -3}, {1, -6}, {19, 7}, {3, 6}, {-1, -2}, {7, -3}, {-6, -8}, {7, 1}, {-15, 12}, {-17, 9}, {19, -9}, {1, 0}, {9, -10}, {6, 20}, {-12, -4}, {-16, -17}, {14, 3}, {0, -1}, {-18, 9}, {-15, 15}, {-3, -15}, {-5, 20}, {15, -14}, {9, -17}, {10, -14}, {-7, -11}, {14, 9}, {1, -1}, {15, 12}, {-5, -1}, {-17, -5}, {15, -2}, {-12, 11}, {19, -18}, {8, 7}, {-5, -3}, {-17, -1}, {-18, 13}, {15, -3}, {4, 18}, {-14, -15}, {15, 8}, {-18, -12}, {-15, 19}, {-9, 16}, {-9, 14}, {-12, -14}, {-2, -20}, {-3, -13}, {10, -7}, {-2, -10}, {9, 10}, {-1, 7}, {-17, -6}, {-15, 20}, {5, -17}, {6, -6}, {-11, -8}};
        int result = m.calculate(points);
        System.out.println(result);
    }

    public static void main2(String[] args) {
        System.out.println(Double.doubleToRawLongBits(9.000000000000057));
        System.out.println(Double.doubleToRawLongBits(9.0));
    }


    private int calculate(int[][] points) {
        int outLength = points.length;
        if (outLength <= 2) {
            return points.length;
        }
        Map<String, Integer> container = new HashMap<>();
        for (int i = 0; i < outLength; i++) {
            for (int j = i + 1; j < outLength; j++) {
                drawLine(points[i], points[j], container);
            }
        }

        Set<String> keys = container.keySet();
        int maxCount = 0;
        for (String k : keys) {
            int val = container.get(k);
            if (val > maxCount) {
                maxCount = val;
            }
        }
        return (int) ((1 + Math.sqrt(1 + 8 * maxCount)) / 2);
    }

    private void drawLine(int[] x, int[] y, Map<String, Integer> container) {
        String key;
        int deltaY = y[1] - x[1];
        int deltaX = y[0] - x[0];
        if (deltaX == 0) {
            key = "inf" + "-" + x[0];
        } else {
            double k;
            if (deltaY == 0) {
                k = 0;
                key = k + "-" + x[1];
            } else {
                k = (double) deltaY / deltaX;
                double b = x[1] - k * x[0];
                String sb = String.valueOf(Double.doubleToRawLongBits(b));//浮点数精度可能导致出现类似9.0 和9.0000000000000000000034 ,更准确应该用分数表示
                if (sb.length() > 17) {
                    sb = sb.substring(0, 17);
                }
                key = k + "-" + sb;
            }
        }
        Integer value = container.get(key);
        if (value == null) {
            container.put(key, 1);
        } else {
            container.put(key, value + 1);
        }
    }


}
