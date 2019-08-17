package com.tom.study.disruptorstudy.ability;


import com.lmax.disruptor.EventHandler;

public class DataConsumer implements EventHandler<Data> {

    private Long startTime;

    private int i;

    public DataConsumer() {
        this.startTime = System.currentTimeMillis();
    }

    @Override
    public void onEvent(Data data, long l, boolean b) throws Exception {
        i++;
        if (i == Constants.EVENT_NUM_OHM) {
            long endTime = System.currentTimeMillis();
            System.out.println("Disruptor costTime = " + (endTime - startTime) + "ms");
        }
    }
}
