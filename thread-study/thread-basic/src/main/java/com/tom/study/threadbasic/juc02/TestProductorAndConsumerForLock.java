package com.tom.study.threadbasic.juc02;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class TestProductorAndConsumerForLock {
}


class ClerkA{
    private int product = 0;

    private Lock lock = new ReentrantLock();
    private Condition condition = lock.newCondition();

    public void get(){
        lock.lock();
        try {
            if(product >= 10) {
                System.out.println("产品已满！");
                try {
                    condition.await();
                } catch (InterruptedException e) {
                }
            } else {
                System.out.println(Thread.currentThread().getName() + " : " + ++product);
                // 开始生产则通知所有
                condition.signalAll();
            }
        } finally {
            lock.unlock();
        }

    }

    public synchronized void sale() {
        lock.lock();
        try {
            if (product <= 0) {
                System.out.println("缺货");
                try {
                    condition.await();
                } catch (InterruptedException e) {
                }
            } else {
                System.out.println(Thread.currentThread().getName() + " : " + --product);
                condition.signalAll();
            }
        } finally {
            lock.unlock();
        }

    }
}

//class Producer implements Runnable{
//    private Clerk clerk;
//
//    public Producer(Clerk clerk) {
//        this.clerk = clerk;
//    }
//
//    @Override
//    public void run() {
//        for (int i = 0; i < 20; i++) {
//            try {
//                Thread.sleep(200);
//            } catch (InterruptedException e) {
//            }
//            clerk.get();
//        }
//    }
//}
//
//class Consumer implements Runnable {
//
//    private Clerk clerk;
//
//    public Consumer(Clerk clerk) {
//        this.clerk = clerk;
//    }
//
//    @Override
//    public void run() {
//        for (int i = 0; i < 20; i++) {
//            clerk.sale();
//        }
//
//    }
//}