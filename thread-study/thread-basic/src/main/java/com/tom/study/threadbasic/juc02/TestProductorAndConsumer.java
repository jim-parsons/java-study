package com.tom.study.threadbasic.juc02;

public class TestProductorAndConsumer {

    public static void main(String[] args) {
        Clerk clerk = new Clerk();
        Producer pro = new Producer(clerk);
        Consumer con = new Consumer(clerk);

        new Thread(pro, "生产者 A").start();
        new Thread(con, "消费者 B").start();
    }
}


class Clerk{
    private int product = 0;

    public synchronized void get(){
        if(product >= 10) {
            System.out.println("产品已满！");

            try {
                // 已满则必须等待
                // wait 是暂停当前线程,并释放锁;等待唤醒后,从当前位置继续执行
                this.wait();
            } catch (InterruptedException e) {
            }
        } else {
            System.out.println(Thread.currentThread().getName() + " : " + ++product);
            // 开始生产则通知所有
            this.notifyAll();
        }
    }

    // 将product >= 10 改为 >= 1
    // 1: 当消费者循环到了最后一次,并抢到了锁,且product的个数为0,此时会阻塞自己(this.wait()),然后释放锁 ===> product = 0
    // 2: 生产者拿到锁后,满足product>=1,同样的阻塞自己,并唤醒其他线程 ===> product = 1
    // 3: 此时消费者被唤醒,从this.wait()继续执行,结束所有循环 ===> product = 1
    // 4: 然后生产者一直阻塞,没有其他线程被唤醒
    // 可将else去掉

    // 虚假唤醒
    // 当有多个消费者和生产者线程,当多个消费者同时进入 product <= 0 此时会阻塞this.wait(),然后当生产者 notifyAll()时
    // 者两个消费者会同时唤醒,进行--product

    // wati()方法需要放在while,避免虚假唤醒

    public synchronized void sale() {
        if (product <= 0) {
            System.out.println("缺货");
            try {
                this.wait();
            } catch (InterruptedException e) {
            }
        } else {
            System.out.println(Thread.currentThread().getName() + " : " + --product);
            this.notifyAll();
        }
    }
}

class Producer implements Runnable{
    private Clerk clerk;

    public Producer(Clerk clerk) {
        this.clerk = clerk;
    }

    @Override
    public void run() {
        for (int i = 0; i < 20; i++) {
            try {
                Thread.sleep(200);
            } catch (InterruptedException e) {
            }
            clerk.get();
        }
    }
}

class Consumer implements Runnable {

    private Clerk clerk;

    public Consumer(Clerk clerk) {
        this.clerk = clerk;
    }

    @Override
    public void run() {
        for (int i = 0; i < 20; i++) {
            clerk.sale();
        }

    }
}