package com.tom.study.disruptorstudy.ability;

import java.util.concurrent.ArrayBlockingQueue;

public class ArrayBlockingQueue4Test {

    public static void main(String[] args) {
        ArrayBlockingQueue<Data> queue = new ArrayBlockingQueue<>(100000000);
        long startTime = System.currentTimeMillis();

        new Thread(() -> {
            long i = 0;
            while (i < Constants.EVENT_NUM_OHM) {
                Data data = new Data(i, "c" + i);
                try {
                    queue.put(data);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();


        new Thread(() -> {
            int k = 0;
            while (k < Constants.EVENT_NUM_OHM) {
                try {
                    queue.take();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            long endTime = System.currentTimeMillis();
            System.out.println("ArrayBlockingQueue costTime = " + (endTime - startTime) + "ms");
        }).start();
    }


}
