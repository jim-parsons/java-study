package com.tom.study.threadbasic.juc02;

import java.time.Duration;
import java.time.Instant;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveTask;
import java.util.stream.LongStream;

public class TestForkJoinPool {

    public static void main(String[] args) {
        ForkJoinPool pool = new ForkJoinPool();
        ForkJoinSumCalculate forkJoinSumCalculate = new ForkJoinSumCalculate(0L, 1000L);
        Long sum = pool.invoke(forkJoinSumCalculate);
        System.out.println(sum);
    }

    public void test1(){
        Instant start = Instant.now();

        long sum = 0L;

        for (long i = 0L; i <= 50000000000L; i++) {
            sum += i;
        }

        System.out.println(sum);

        Instant end = Instant.now();

        System.out.println("耗费时间为：" + Duration.between(start, end).toMillis());//35-3142-15704
    }

    public void test2(){
        Instant start = Instant.now();

        Long sum = LongStream.rangeClosed(0L, 50000000000L)
                .parallel()
                .reduce(0L, Long::sum);

        System.out.println(sum);

        Instant end = Instant.now();

        System.out.println("耗费时间为：" + Duration.between(start, end).toMillis());//1536-8118
    }

}


/**
 * RecursiveTask<T> 有返回值
 * RecursiveAction 没有返回值
 */
class ForkJoinSumCalculate extends RecursiveTask<Long> {

    private long start;
    private long end;

    private static final long THRESHOLD = 10000L;

    public ForkJoinSumCalculate(long start, long end) {
        this.start = start;
        this.end = end;
    }

    @Override
    protected Long compute() {

        long len = end - start;
        if (len <= THRESHOLD) {
            long sum = 0;
            for (long i = start; i < end; i++) {
                sum += i;
            }
            return sum;
        } else {
            long mid = (start + end) / 2;
            ForkJoinSumCalculate left = new ForkJoinSumCalculate(start, mid);
            left.join();
            ForkJoinSumCalculate right = new ForkJoinSumCalculate(mid + 1, end);
            right.join();
            return left.join() + right.join();
        }
    }
}