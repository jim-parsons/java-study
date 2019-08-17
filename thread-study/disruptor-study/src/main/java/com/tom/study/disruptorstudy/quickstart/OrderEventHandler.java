package com.tom.study.disruptorstudy.quickstart;


import com.lmax.disruptor.EventHandler;

/**
 * 消费类,即监听实践类,应用于处理数据(Event类)
 */
public class OrderEventHandler implements EventHandler<OrderEvent> {

    @Override
    public void onEvent(OrderEvent orderEvent, long l, boolean b) throws Exception {
        System.out.println("消费者: " + orderEvent.getValue());
    }
}
