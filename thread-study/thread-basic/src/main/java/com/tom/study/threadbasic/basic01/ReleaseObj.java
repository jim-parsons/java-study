package com.tom.study.threadbasic.basic01;

/**
 * @author tom
 * @date 2019/08/17
 * @desc wait() 只会释放当前对象的锁
 */
public class ReleaseObj {

    private static volatile Object OBJ_A = new Object();

    private static volatile Object OBT_B = new Object();


    public static void main(String[] args) {
        Thread threadA = new Thread(() -> {
            try {
                synchronized (OBJ_A) {
                    System.out.println("threadA get OBJ_A lock");
                    synchronized (OBT_B) {
                        System.out.println("threadA get OBJ_B lock");

                        System.out.println("threadA release OBJ_A lock");
                        OBJ_A.wait();
                    }
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });

        Thread threadB = new Thread(() -> {
            try {
                Thread.sleep(1000);
                synchronized (OBJ_A) {
                    System.out.println("threadB get OBJ_A lock");

                    System.out.println("threadB try to get OBJ_B lock");
                    synchronized (OBT_B) {
                        System.out.println("threadB get OBJ_B lock");

                        System.out.println("threadB release OBJ_A lock");
                        OBJ_A.wait();
                    }
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });

        threadA.start();
        threadB.start();

    }
}
