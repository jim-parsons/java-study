package com.tom.study.disruptorstudy.ability;

import com.lmax.disruptor.RingBuffer;
import com.lmax.disruptor.YieldingWaitStrategy;
import com.lmax.disruptor.dsl.Disruptor;
import com.lmax.disruptor.dsl.ProducerType;


public class DisruptorSingle4Test {

    public static void main(String[] args) {
        int ringBufferSize = 65536;

        Disruptor<Data> disruptor = new Disruptor<>(
                () -> new Data(),
                ringBufferSize,
//                Executors.newSingleThreadExecutor(),
                r -> {return new Thread(r);},
                ProducerType.SINGLE,
                new YieldingWaitStrategy()

        );

        DataConsumer consumer = new DataConsumer();
        disruptor.handleEventsWith(consumer);
        disruptor.start();

        new Thread(() -> {
            RingBuffer<Data> ringBuffer = disruptor.getRingBuffer();
            for (long i = 0; i < Constants.EVENT_NUM_OHM; i++) {
                long seq = ringBuffer.next();
                Data data = ringBuffer.get(seq);
                data.setId(i);
                data.setName("c" + i);
                ringBuffer.publish(seq);
            }
        }).start();
    }
}
