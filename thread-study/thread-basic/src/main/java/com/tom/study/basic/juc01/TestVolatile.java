package com.tom.study.basic.juc01;

public class TestVolatile {

    public static void main(String[] args) {
        ThreadDemo td = new ThreadDemo();
        new Thread(td).start();
        while (true) {
            if (td.getFlag()) {
                System.out.println("------------------");
                break;
            }
        }
    }



}

class ThreadDemo implements Runnable {

//    private volatile boolean flag = false;
    private boolean flag = false;

    @Override
    public void run() {
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        flag = true;
        System.out.println("flag : " + getFlag());

    }

    public boolean getFlag() {
        return flag;
    }

}
