package com.tom.study.disruptorstudy.quickstart;

import com.lmax.disruptor.EventFactory;

/**
 * event 工厂类,用于创建event类实例对象
 */
public class OrderEventFactory implements EventFactory<OrderEvent> {
    @Override
    public OrderEvent newInstance() {
        return new OrderEvent();
    }
}
