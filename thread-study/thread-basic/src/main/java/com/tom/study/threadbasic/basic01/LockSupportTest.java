package com.tom.study.threadbasic.basic01;

import java.util.concurrent.locks.LockSupport;

/**
 * @author tom
 * @date 2019/08/19
 * @desc
 */
public class LockSupportTest {

    static Thread thread = new Thread(() -> {
        System.out.println("child thread start");
        // 当一个线程调用park方法，如果没有与LockSupport关联的许可证，则被挂起，否则直接返回
        // 如果此时被其他线程中断，返回时不会抛出InterruptedException
        LockSupport.park();
        System.out.println("child thread end");
    });


    public static void main(String[] args) throws InterruptedException {
        thread.start();
        Thread.sleep(1000);

        System.out.println("main thread start");
        // 让传入的thread有对应的关联许可证，则对应的park会返回
        //
        LockSupport.unpark(thread);
    }
}
