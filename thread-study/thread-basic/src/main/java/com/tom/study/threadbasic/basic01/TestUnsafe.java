package com.tom.study.threadbasic.basic01;

import sun.misc.Unsafe;

import java.lang.reflect.Field;

/**
 * @author tom
 * @date 2019/08/19
 * @desc
 */
public class TestUnsafe {

    /**
     * 普通类无法直接使用Unsafe类，因为Unsafe.getUnsafe() 方法会判断加载的类加载器是不是bootstrap，不是会抛出异常
     */

    static Unsafe unsafe;

    static long stateOffset;

    private volatile long state = 0;

    static {
        try {
            Field filed = Unsafe.class.getDeclaredField("theUnsafe");
            filed.setAccessible(true);
            unsafe = (Unsafe) filed.get(null);

            stateOffset = unsafe.objectFieldOffset(TestUnsafe.class.getDeclaredField("state"));

        } catch (NoSuchFieldException | IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        TestUnsafe test = new TestUnsafe();
        boolean b = unsafe.compareAndSwapInt(test, stateOffset, 0, 1);
        System.out.println(b);
    }

}
