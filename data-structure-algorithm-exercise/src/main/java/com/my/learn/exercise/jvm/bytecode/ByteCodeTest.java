package com.my.learn.exercise.jvm.bytecode;
/*
 * 创建人：baimiao
 * 创建时间：2024/10/21 14:39
 *
 */

public class ByteCodeTest implements ByteCodeTestInterface {

    private final int number = 0;
    private final static boolean flag = false;

    private int age;
    public ByteCodeTest() {
    }

    public ByteCodeTest(int age) {
        this.age = age;
    }

    public static void main(String[] args) {
        Thread thread = new Thread(() -> {
            for (int i = 0; i < 10; i++) {
                System.out.println(i);
            }
        });
        thread.start();
        System.out.println("main method end");
    }

    @Override
    public void show() {
        System.out.println("bytecode test");
    }
}
