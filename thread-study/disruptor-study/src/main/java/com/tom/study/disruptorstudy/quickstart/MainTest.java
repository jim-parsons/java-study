package com.tom.study.disruptorstudy.quickstart;

import com.lmax.disruptor.BlockingWaitStrategy;
import com.lmax.disruptor.RingBuffer;
import com.lmax.disruptor.dsl.Disruptor;
import com.lmax.disruptor.dsl.ProducerType;

import java.nio.ByteBuffer;

public class MainTest {

    public static void main(String[] args) {
        // 参数准备
        OrderEventFactory orderEventFactory = new OrderEventFactory();
        int ringBufferSize = 1024 * 1024;

        /**
         * 1 eventFactory: 消息(event)工厂对象
         * 2 ringBufferSize: 容器的长度(2的n次方),是一个循环队列,
         * 3 executor: 线程池(建议使用自定义线程池) RejectedExecutionHandler
         * 4 ProducerType: 单生产者 还是 多生产者
         * 5 waitStrategy: 针对消费者(消费快,生产慢), 等待策略 YieldingWaitStrategy性能最高
         */

        // 1: 实例化disruptor对象
        Disruptor<OrderEvent> disruptor = new Disruptor<>(
                orderEventFactory,
                ringBufferSize,
                r -> {return new Thread(r);},
                ProducerType.SINGLE, // 生产模式,支持 单/多生产模式
                new BlockingWaitStrategy()
        );

        // 2: 添加消费者监听,(构建disruptor,与 消费者关联关系)
        disruptor.handleEventsWith(new OrderEventHandler());

        // 3: 启动disruptor
        disruptor.start();

        // 4: 获取实际存储数据的容器: RingBuffer
        RingBuffer<OrderEvent> ringBuffer = disruptor.getRingBuffer();

        // 将实际存储对象ringBuffer传入producer
        OrderEventProducer producer = new OrderEventProducer(ringBuffer);

        ByteBuffer byteBuffer = ByteBuffer.allocate(8);

        for (long i = 0; i < 100; i++) {
            byteBuffer.putLong(0, i);
            producer.putData(byteBuffer);
        }

        disruptor.shutdown();
    }
}
