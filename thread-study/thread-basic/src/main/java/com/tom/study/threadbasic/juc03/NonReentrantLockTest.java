package com.tom.study.threadbasic.juc03;

import java.util.Queue;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;

/**
 * @author tom
 * @date 2019/08/19
 * @desc
 */
public class NonReentrantLockTest {

    final static NonReentrantLock lock = new NonReentrantLock();

    final static Condition notFull = lock.newCondition();

    final static Condition notEmpty = lock.newCondition();

    final static Queue<String> queue = new LinkedBlockingDeque<>();

    final static int size = 10;

    public static void main(String[] args) {
        Thread producer = new Thread(() -> {
//            Lock temp = lock;
            lock.lock();
            try {
                while (queue.size() == size) {
                    notEmpty.await();
                }
                queue.add("e");
                notFull.signalAll();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                lock.unlock();
            }
        });

        Thread consumer = new Thread(() -> {
//            Lock temp = lock;
            lock.lock();
            try {
                while (queue.size() == 0) {
                    notFull.await();
                }
                String e = queue.poll();
                notEmpty.signalAll();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                lock.unlock();
            }
        });

        producer.start();
        consumer.start();
    }
}
