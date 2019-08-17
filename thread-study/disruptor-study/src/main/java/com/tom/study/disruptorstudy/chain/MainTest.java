package com.tom.study.disruptorstudy.chain;

import com.lmax.disruptor.BusySpinWaitStrategy;
import com.lmax.disruptor.dsl.Disruptor;
import com.lmax.disruptor.dsl.ProducerType;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MainTest {
    public static void main(String[] args) {

        ExecutorService es1 = Executors.newFixedThreadPool(1);
        ExecutorService es2 = Executors.newFixedThreadPool(5);
        Disruptor<Trade> disruptor = new Disruptor<>(
                () -> new Trade(),
                1024*1024,
                es1,
                ProducerType.SINGLE,
                new BusySpinWaitStrategy()
        );
    }
}
