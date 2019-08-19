package com.tom.study.threadbasic.juc03;

import java.util.ArrayList;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * @author tom
 * @date 2019/08/19
 * @desc
 */
public class WriteReadReentrantLockList {

    private ArrayList<String> array = new ArrayList<>();

    private static final ReentrantReadWriteLock rootLock = new ReentrantReadWriteLock();

    public Lock getWriteLock() {
        return rootLock.writeLock();
    }

    public Lock getReadLock() {
        return rootLock.readLock();
    }

    public void add(String e) {
        Lock writer = getWriteLock();
        writer.lock();
        try {
            array.add(e);
        } finally {
            writer.unlock();
        }
    }

    public String get(int index) {
        Lock read = getReadLock();
        read.lock();
        try {
            return array.get(index);
        } finally {
            read.unlock();
        }
    }
}
