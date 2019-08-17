package com.tom.study.threadbasic.basic01;

/**
 * @author tom
 * @date 2019/08/17
 * @desc
 */
public class DeadLock {

    private static volatile Object objA = new Object();

    private static volatile Object objB = new Object();

    public static void main(String[] args) {
        Thread threadA = new Thread(() -> {
            synchronized (objA) {
                try {
                    Thread.sleep(1000);
                    System.out.println("threada try to access objb");
                    synchronized (objB) {
                        System.out.println("thread a -> obja -> objb");
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

            }
        });

        Thread threadB = new Thread(() -> {
            synchronized (objB) {
                try {
                    Thread.sleep(1000);
                    System.out.println("threadb try to access obja");
                    synchronized (objA) {
                        System.out.println("thread b -> objb -> obja");
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
        threadA.start();
        threadB.start();
    }
}
