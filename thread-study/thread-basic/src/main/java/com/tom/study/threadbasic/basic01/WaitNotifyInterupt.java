package com.tom.study.threadbasic.basic01;

/**
 * @author tom
 * @date 2019/08/17
 * @desc wait() 被阻塞后，如果其他线程中断了该线程，该线程会抛出异常并返回
 */
public class WaitNotifyInterupt {

    static Object obj = new Object();

    public static void main(String[] args) throws InterruptedException {
        Thread thread = new Thread(() -> {
            try {
                System.out.println("---start----");
                synchronized (obj) {
                    obj.wait();
                }
                System.out.println("---end-----");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        thread.start();
        Thread.sleep(1000);

        thread.interrupt();
    }
}
