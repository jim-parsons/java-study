package com.tom.study.threadbasic.juc02;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class TestABCAlternate {

    public static void main(String[] args) {
        AlternateDemo ad = new AlternateDemo();
        new Thread(() -> {
            for (int i = 0; i < 20; i++) {
                ad.loopA(i);
            }
        }, "A").start();
        new Thread(() -> {
            for (int i = 0; i < 20; i++) {
                ad.loopB(i);
            }
        }, "B").start();
        new Thread(() -> {
            for (int i = 0; i < 20; i++) {
                ad.loopC(i);
                System.out.println("-----------------------------------");
            }
        }, "C").start();
    }
}

class AlternateDemo{
    private int number = 1; //当前线程执行的标记

    private Lock lock = new ReentrantLock();

    private Condition condition1 = lock.newCondition();
    private Condition condition2 = lock.newCondition();
    private Condition condition3 = lock.newCondition();


    public void loopA(int total) {
        lock.lock();

        try {

            // 1 判断
            if (number != 1) {
                try {
                    condition1.await();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            // 2 打印
            System.out.println(Thread.currentThread().getName() + "\t" + "\t" + total);

            // 3 唤醒
            number = 2;
            condition2.signal();
        } finally {
            lock.unlock();
        }
    }

    public void loopB(int total)  {
        lock.lock();

        try {

            // 1 判断
            if (number != 2) {
                try {
                    condition2.await();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            // 2 打印
            System.out.println(Thread.currentThread().getName() + "\t" + "\t" + total);

            // 3 唤醒
            number = 3;
            condition3.signal();
        } finally {
            lock.unlock();
        }
    }

    public void loopC(int total) {
        lock.lock();

        try {

            // 1 判断
            if (number != 3) {
                try {
                    condition3.await();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            // 2 打印
            System.out.println(Thread.currentThread().getName() + "\t" + "\t" + total);

            // 3 唤醒
            number = 1;
            condition1.signal();
        } finally {
            lock.unlock();
        }
    }
}
