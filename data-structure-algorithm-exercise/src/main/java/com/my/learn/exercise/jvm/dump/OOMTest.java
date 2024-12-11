package com.my.learn.exercise.jvm.dump;
/*
 * 创建人：baimiao
 * 创建时间：2024/10/22 10:27
 *
 */

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class OOMTest {
    public static void main(String[] args) {
        try {
            List<String> list = new ArrayList<>();
            Thread thread = new Thread(() -> {
                while (true) {
                    list.add("abc");
                    try {
                        TimeUnit.MILLISECONDS.sleep(500);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            });
            thread.setName("my-test-thread");
            thread.start();
            System.out.println("====main end====");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
